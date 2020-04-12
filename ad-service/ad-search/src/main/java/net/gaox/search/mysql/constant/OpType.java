package net.gaox.search.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * 操作类型
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    /**
     * Event转换为Operate类型
     *
     * @param eventType event类型
     * @return operate类型
     */
    public static OpType to(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;

            default:
                return OTHER;
        }
    }
}
