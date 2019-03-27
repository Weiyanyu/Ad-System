package top.yeonon.adsearch.client.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 16:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlan implements Serializable {

    private Long id;

    private Long userId;

    private String planName;

    private Integer planStatus;

    private Date startDate;

    private Date endDate;

    private Date createTime;

    private Date updateTime;



}
