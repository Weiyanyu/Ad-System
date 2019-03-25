package top.yeonon.adsponsor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yeonon.adsponsor.constant.CommonStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/20 0020 21:02
 **/
@Data
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;


    public AdUser() {
    }

    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getCode();
        this.createTime = new Date();
        this.updateTime = new Date();
    }


}
