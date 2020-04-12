package net.gaox.search.sender;

import net.gaox.search.mysql.dto.MySqlRowData;

/**
 * <p> 增量数据投递接口 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:18
 */
public interface ISender {
    /**
     * 投递方法
     *
     * @param rowData binlog数据
     */
    void sender(MySqlRowData rowData);
}