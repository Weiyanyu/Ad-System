package top.yeonon.adsponsor.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/25 0025 12:33
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreativeUnitResponse {

    private List<Long> ids;
}
