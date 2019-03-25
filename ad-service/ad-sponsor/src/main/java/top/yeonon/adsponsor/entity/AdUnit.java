package top.yeonon.adsponsor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.yeonon.adsponsor.constant.CommonStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 16:53
 **/
@Data
@AllArgsConstructor
@Entity
@Table(name = "ad_unit")
public class AdUnit {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(name = "unit_name", nullable = false)
    private String unitName;

    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;

    /**
     * 广告位置类型（APP开屏等）
     */
    @Column(name = "position_type", nullable = false)
    private Integer positionType;

    @Column(name = "budget", nullable = false)
    private Long budget;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUnit() {
    }

    public AdUnit(Long planId, String unitName, Integer positionType, Long budget) {
        this.planId = planId;
        this.unitName = unitName;
        //默认可用
        this.unitStatus = CommonStatus.VALID.getCode();
        this.positionType = positionType;
        this.budget = budget;

        this.createTime = new Date();
        this.updateTime = new Date();
    }
}
