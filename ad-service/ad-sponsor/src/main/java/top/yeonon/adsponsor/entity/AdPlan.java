package top.yeonon.adsponsor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.yeonon.adsponsor.constant.CommonStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 16:48
 **/
@Data
@AllArgsConstructor
@Entity
@Table(name = "ad_plan")
public class AdPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "plan_status", nullable = false)
    private Integer planStatus;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;


    public AdPlan() {
    }

    public AdPlan(Long userId, String planName, Date startDate, Date endDate) {
        this.userId = userId;
        this.planName = planName;
        //默认是合法的状态
        this.planStatus = CommonStatus.VALID.getCode();

        this.startDate = startDate;
        this.endDate = endDate;

        this.createTime = new Date();
        this.updateTime = new Date();
    }
}
