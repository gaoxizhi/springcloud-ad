package net.gaox.sponsor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.gaox.common.dump.table.AdCreativeTable;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name = "ad_creative")
public class Creative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 物料的类型, 比如图片可以是 bmp, jpg等等
     */
    @Basic
    @Column(name = "material_type", nullable = false)
    private Integer materialType;

    @Basic
    @Column(name = "height", nullable = false)
    private Integer height;

    @Basic
    @Column(name = "width", nullable = false)
    private Integer width;

    /**
     * 物料大小
     */
    @Basic
    @Column(name = "size", nullable = false)
    private Long size;

    /**
     * 持续时长, 只有视频不为0
     */
    @Basic
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /**
     * 审核状态
     */
    @Basic
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "url", nullable = false)
    private String url;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "updateTime", nullable = false)
    private Date updateTime;

    public AdCreativeTable toAdCreativeTable() {
        return new AdCreativeTable(
                this.getId(),
                this.getName(),
                this.getType(),
                this.getMaterialType(),
                this.getHeight(),
                this.getWidth(),
                this.getAuditStatus(),
                this.getUrl()
        );
    }
}