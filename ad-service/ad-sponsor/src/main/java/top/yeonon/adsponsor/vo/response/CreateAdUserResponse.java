package top.yeonon.adsponsor.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 12:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdUserResponse {

    private Long userId;
    private String username;
    private String token;
    private Date createTime;
    private Date updateTime;


}
