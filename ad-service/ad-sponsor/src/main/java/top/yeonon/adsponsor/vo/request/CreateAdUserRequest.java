package top.yeonon.adsponsor.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 22:03
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdUserRequest {

    private String username;

    public boolean validate() {
        return StringUtils.isNotEmpty(username);
    }

}
