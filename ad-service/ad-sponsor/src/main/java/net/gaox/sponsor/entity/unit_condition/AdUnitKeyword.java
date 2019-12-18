package net.gaox.sponsor.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

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
@Table(name = "ad_unit_keyword")
@Accessors(chain = true)
public class AdUnitKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "keyword", nullable = false)
    private String keyword;

    public AdUnitKeyword(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
    }
}