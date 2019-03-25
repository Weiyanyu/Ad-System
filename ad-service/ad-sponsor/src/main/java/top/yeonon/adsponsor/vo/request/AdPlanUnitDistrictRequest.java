package top.yeonon.adsponsor.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/24 0024 12:22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanUnitDistrictRequest {

    private List<UnitDistrict> unitDistricts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitDistrict {
        private Long unitId;

        private String province;

        private String city;
    }
}
