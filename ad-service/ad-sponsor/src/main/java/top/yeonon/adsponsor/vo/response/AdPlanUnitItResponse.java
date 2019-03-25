package top.yeonon.adsponsor.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/24 0024 12:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanUnitItResponse {

    private List<Long> ids;
}
