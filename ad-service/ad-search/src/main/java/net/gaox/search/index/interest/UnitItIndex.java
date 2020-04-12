package net.gaox.search.index.interest;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.index.IndexAware;
import net.gaox.search.utils.CommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    /**
     * 兴趣标签，对应单元
     * <itTag, adUnitId set>
     */
    private static Map<String, Set<Long>> itUnitMap;
    /**
     * 推广单元对应推广标签，反向索引
     * <unitId, itTag set>
     */
    private static Map<Long, Set<String>> unitItMap;

    /**
     * 静态初始化
     */
    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    /**
     * 通过兴趣标签获取推广单元集合
     *
     * @param key 兴趣id
     * @return set
     */
    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {

        log.info("UnitItIndex, before add: {}", unitItMap);
        Set<Long> unitIds = CommonUtils.getorCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> its = CommonUtils.getorCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            its.add(key);
        }

        log.info("UnitItIndex, after add: {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitItIndex, before delete: {}", unitItMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> itTagSet = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            itTagSet.remove(key);
        }

        log.info("UnitItIndex, after delete: {}", unitItMap);
    }

    public boolean match(Long unitId, List<String> itTags) {

        if (unitItMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {

            Set<String> unitKeywords = unitItMap.get(unitId);

            return CollectionUtils.isSubCollection(itTags, unitKeywords);
        }

        return false;
    }
}
