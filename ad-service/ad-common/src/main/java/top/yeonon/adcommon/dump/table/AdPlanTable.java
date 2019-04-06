package top.yeonon.adcommon.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/4/5 0005 19:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {

    private Long id;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
}
