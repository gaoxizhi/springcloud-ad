package net.gaox.search.index;

/**
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
public interface IndexAware<K, V> {
    /**
     * 通过索引获取
     *
     * @param key 索引
     * @return 范性
     */
    V get(K key);

    /**
     * 添加
     *
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 修改
     *
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * 删除
     *
     * @param key
     * @param value
     */
    void delete(K key, V value);
}
