package top.yeonon.adsponsor.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @Author yeonon
 * @date 2019/3/24 0024 11:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanUnitRequest {

    private Long planId;
    private String unitName;

    private Integer positionType;
    private Long budget;

    public boolean createValidate() {
        return planId != null
                && StringUtils.isNotEmpty(unitName)
                && positionType != null
                && budget != null;
    }

    public boolean getValidate() {
        return planId != null;
    }
}
