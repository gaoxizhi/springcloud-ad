package net.gaox.search.index.creative;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.index.IndexAware;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    /**
     * 创意索引，正向
     */
    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public List<CreativeObject> fetch(Collection<Long> adIds) {

        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }

        List<CreativeObject> result = new ArrayList<>();

        adIds.forEach(u -> {
            CreativeObject object = get(u);
            if (null == object) {
                log.error("CreativeObject not found: {}", u);
                return;
            }

            result.add(object);
        });

        return result;
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {

        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {

        log.info("before update: {}", objectMap);

        CreativeObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {

        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}