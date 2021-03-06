package net.gaox.sponsor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.gaox.common.dump.table.AdUnitTable;
import net.gaox.sponsor.constant.CommonStatus;

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
@Table(name = "ad_unit")
public class AdUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Basic
    @Column(name = "unit_name", nullable = false)
    private String unitName;

    @Basic
    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;

    /**
     * 广告位类型(开屏, 贴片, 中贴...)
     */
    @Basic
    @Column(name = "position_type", nullable = false)
    private Integer positionType;

    @Basic
    @Column(name = "budget", nullable = false)
    private Long budget;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUnit(Long planId, String unitName, Integer positionType, Long budget) {
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALID.getStatus();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

    public AdUnitTable toAdUnitTable() {
        return new AdUnitTable(
                this.getId(),
                this.getUnitStatus(),
                this.getPositionType(),
                this.getPlanId()
        );
    }
}