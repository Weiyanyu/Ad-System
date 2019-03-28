package top.yeonon.adsearch.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/27 0027 20:02
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdPlanObject {

    private Long planId;

    private Long userId;

    private Integer planStatus;

    private Date startDate;

    private Date endDate;


    public void update(AdPlanObject newObject) {
        if (newObject == null) return;
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getUserId() != null) {
            this.userId = newObject.getUserId();
        }
        if (newObject.getPlanStatus() != null) {
            this.planStatus = newObject.getPlanStatus();
        }
        if (newObject.getStartDate() != null) {
            this.startDate = newObject.getStartDate();
        }
        if (newObject.getEndDate() != null) {
            this.endDate = newObject.getEndDate();
        }
    }
}
