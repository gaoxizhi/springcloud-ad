package net.gaox.search.index.keyword;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.index.IndexAware;
import net.gaox.search.utils.CommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

    /**
     * 关键词到推广单元
     */
    private static Map<String, Set<Long>> keywordUnitMap;
    /**
     * 推广单元到关键词
     */
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {

        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = keywordUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        }

        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {

        log.info("UnitKeywordIndex, before add: {}", unitKeywordMap);

        Set<Long> unitIdSet = CommonUtils.getorCreate(
                key, keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(
                    unitId, unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.add(key);
        }

        log.info("UnitKeywordIndex, after add: {}", unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitKeywordIndex, before delete: {}", unitKeywordMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {

            Set<String> keywordSet = CommonUtils.getorCreate(
                    unitId, unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.remove(key);
        }

        log.info("UnitKeywordIndex, after delete: {}", unitKeywordMap);
    }

    public boolean match(Long unitId, List<String> keywords) {
        //包含单元id，并且判断单元id对应的列表非空
        if (unitKeywordMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {

            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            //是否是子集
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }

        return false;
    }
}