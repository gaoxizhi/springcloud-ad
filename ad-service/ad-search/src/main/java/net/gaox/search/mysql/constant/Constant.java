package net.gaox.search.mysql.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据信息-表字段
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
public class Constant {
    /**
     * 数据库名称
     */
    private static final String DB_NAME = "ad_system";

    /**
     * 广告数据信息内部类
     */
    public static class AdPlanTableInfo {

        public static final String TABLE_NAME = "ad_plan";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_PLAN_STATUS = "plan_status";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
    }

    /**
     * 广告创意数据信息内部类
     */
    public static class AdCreativeTableInfo {

        public static final String TABLE_NAME = "ad_creative";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MATERIAL_TYPE = "material_type";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WIDTH = "width";
        public static final String COLUMN_AUDIT_STATUS = "audit_status";
        public static final String COLUMN_URL = "url";
    }

    /**
     * 广告单元数据信息内部类
     */
    public static class AdUnitTableInfo {

        public static final String TABLE_NAME = "ad_unit";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_UNIT_STATUS = "unit_status";
        public static final String COLUMN_POSITION_TYPE = "position_type";
        public static final String COLUMN_PLAN_ID = "plan_id";
    }

    public static class AdCreativeUnitTableInfo {

        public static final String TABLE_NAME = "creative_unit";

        public static final String COLUMN_CREATIVE_ID = "creative_id";
        public static final String COLUMN_UNIT_ID = "unit_id";
    }

    public static class AdUnitDistrictTableInfo {

        public static final String TABLE_NAME = "ad_unit_district";

        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_PROVINCE = "province";
        public static final String COLUMN_CITY = "city";
    }

    public static class AdUnitItTableInfo {

        public static final String TABLE_NAME = "ad_unit_it";

        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_IT_TAG = "it_tag";
    }

    public static class AdUnitKeywordTableInfo {

        public static final String TABLE_NAME = "ad_unit_keyword";

        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_KEYWORD = "keyword";
    }

    public static Map<String, String> table2Db;

    static {
        table2Db = new HashMap<>();
        table2Db.put(AdPlanTableInfo.TABLE_NAME, DB_NAME);
        table2Db.put(AdCreativeTableInfo.TABLE_NAME, DB_NAME);
        table2Db.put(AdUnitTableInfo.TABLE_NAME, DB_NAME);
        table2Db.put(AdCreativeUnitTableInfo.TABLE_NAME, DB_NAME);
        table2Db.put(AdUnitDistrictTableInfo.TABLE_NAME, DB_NAME);
        table2Db.put(AdUnitItTableInfo.TABLE_NAME, DB_NAME);
        table2Db.put(AdUnitKeywordTableInfo.TABLE_NAME, DB_NAME);
    }
}