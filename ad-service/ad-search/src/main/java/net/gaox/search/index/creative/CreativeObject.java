package net.gaox.search.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 创意索引对象 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeObject {

    /**
     * 创意id
     */
    private Long adId;
    /**
     * 创意名称
     */
    private String name;
    /**
     * 创意类型
     */
    private Integer type;
    /**
     * 创意子类型
     */
    private Integer materialType;
    /**
     * 高度
     */
    private Integer height;
    /**
     * 宽度
     */
    private Integer width;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 广告创意url
     */
    private String adUrl;

    public void update(CreativeObject newObject) {

        if (null != newObject.getAdId()) {
            this.adId = newObject.getAdId();
        }
        if (null != newObject.getName()) {
            this.name = newObject.getName();
        }
        if (null != newObject.getType()) {
            this.type = newObject.getType();
        }
        if (null != newObject.getMaterialType()) {
            this.materialType = newObject.getMaterialType();
        }
        if (null != newObject.getHeight()) {
            this.height = newObject.getHeight();
        }
        if (null != newObject.getWidth()) {
            this.width = newObject.getWidth();
        }
        if (null != newObject.getAuditStatus()) {
            this.auditStatus = newObject.getAuditStatus();
        }
        if (null != newObject.getAdUrl()) {
            this.adUrl = newObject.getAdUrl();
        }
    }

}