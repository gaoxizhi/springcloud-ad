package net.gaox.search.index.adunit;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.index.IndexAware;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 推广单元的索引维护 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    private static Map<Long, AdUnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType) {

        Set<Long> adUnitIds = new HashSet<>();

        objectMap.forEach((k, v) -> {
            if (AdUnitObject.isAdSlotTypeOK(positionType,
                    v.getPositionType())) {
                adUnitIds.add(k);
            }
        });

        return adUnitIds;
    }

    public List<AdUnitObject> fetch(Collection<Long> adUnitIds) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptyList();
        }

        List<AdUnitObject> result = new ArrayList<>();

        adUnitIds.forEach(u -> {
            AdUnitObject object = get(u);
            if (object == null) {
                log.error("AdUnitObject not found: {}", u);
                return;
            }
            result.add(object);
        });

        return result;
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {

        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdUnitObject value) {

        log.info("before update: {}", objectMap);

        AdUnitObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {

        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
