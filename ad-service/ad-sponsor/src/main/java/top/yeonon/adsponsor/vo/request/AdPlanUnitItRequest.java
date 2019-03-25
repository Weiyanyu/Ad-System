package top.yeonon.adsponsor.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/24 0024 12:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanUnitItRequest {

    private List<UnitIt> unitIts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitIt {
        private Long unitId;

        private String itTag;
    }
}
