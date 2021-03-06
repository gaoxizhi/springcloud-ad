package net.gaox.search.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.gaox.common.dump.table.*;
import net.gaox.search.index.DataTable;
import net.gaox.search.index.IndexAware;
import net.gaox.search.index.adplan.AdPlanIndex;
import net.gaox.search.index.adplan.AdPlanObject;
import net.gaox.search.index.adunit.AdUnitIndex;
import net.gaox.search.index.adunit.AdUnitObject;
import net.gaox.search.index.creative.CreativeIndex;
import net.gaox.search.index.creative.CreativeObject;
import net.gaox.search.index.creativeunit.CreativeUnitIndex;
import net.gaox.search.index.creativeunit.CreativeUnitObject;
import net.gaox.search.index.district.UnitDistrictIndex;
import net.gaox.search.index.interest.UnitItIndex;
import net.gaox.search.index.keyword.UnitKeywordIndex;
import net.gaox.search.mysql.constant.OpType;
import net.gaox.search.utils.CommonUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p> 1. 索引之间存在着层级的划分, 也就是依赖关系的划分 </p>
 * 2. 加载全量索引其实是增量索引 "添加" 的一种特殊实现
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType type) {

        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handleBinlogEvent(DataTable.of(AdPlanIndex.class), planObject.getPlanId(), planObject, type);
    }

    /**
     * 创意索引更新
     *
     * @param creativeTable 广告创意
     * @param type          变更类型
     */
    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {

        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );
        handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeObject.getAdId(), creativeObject, type);
    }

    /**
     * 推广单元
     *
     * @param unitTable
     * @param type
     */
    public static void handleLevel3(AdUnitTable unitTable, OpType type) {

        AdPlanObject adPlanObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (null == adPlanObject) {
            log.error("handleLevel3 found AdPlanObject error: {}",
                    unitTable.getPlanId());
            return;
        }

        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                adPlanObject
        );

        handleBinlogEvent(DataTable.of(AdUnitIndex.class), unitTable.getUnitId(), unitObject, type);
    }

    /**
     * 第三层级：创意、推广单元
     *
     * @param creativeUnitTable
     * @param type
     */

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());

        if (null == unitObject || null == creativeObject) {
            log.error("AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }

        CreativeUnitObject creativeUnitObject =
                new CreativeUnitObject(creativeUnitTable.getAdId(), creativeUnitTable.getUnitId());
        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
    }

    /**
     * 第四层级：地域对象纬度
     *
     * @param unitDistrictTable
     * @param type
     */
    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitDistrictTable index error: {}",
                    unitDistrictTable.getUnitId());
            return;
        }

        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(Collections.singleton(unitDistrictTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    /**
     * 第四层级：兴趣纬度
     *
     * @param unitItTable
     * @param type
     */
    public static void handleLevel4(AdUnitItTable unitItTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("it index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitItTable index error: {}",
                    unitItTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(Collections.singleton(unitItTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitItIndex.class), unitItTable.getItTag(), value, type);
    }

    /**
     * 第四层级：关键词纬度
     *
     * @param keywordTable
     * @param type
     */
    public static void handleLevel4(AdUnitKeywordTable keywordTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitKeywordTable index error: {}",
                    keywordTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(Collections.singleton(keywordTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class), keywordTable.getKeyword(), value, type);
    }

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {

        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}