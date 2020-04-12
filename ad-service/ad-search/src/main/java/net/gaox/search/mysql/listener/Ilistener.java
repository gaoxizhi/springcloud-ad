package net.gaox.search.mysql.listener;

import net.gaox.search.mysql.dto.BinlogRowData;

/**
 * 由binlog实现增量索引的更新接口
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
public interface Ilistener {
    /**
     * 注册不同的监听器
     */
    void register();

    /**
     * 监听binlog事件
     *
     * @param eventData binlog对象
     */
    void onEvent(BinlogRowData eventData);
}
