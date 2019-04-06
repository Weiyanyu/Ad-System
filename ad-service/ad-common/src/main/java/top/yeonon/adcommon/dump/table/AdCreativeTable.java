package top.yeonon.adcommon.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yeonon
 * @date 2019/4/5 0005 19:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeTable {

    private Long adId;

    private String name;

    private Integer type;

    private Integer materialType;

    private Integer height;

    private Integer width;

    private Integer auditStatus;

    private String adUrl;
}
