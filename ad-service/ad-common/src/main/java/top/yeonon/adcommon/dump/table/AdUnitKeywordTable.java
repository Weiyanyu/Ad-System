package top.yeonon.adcommon.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yeonon
 * @date 2019/4/5 0005 19:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordTable {

    private Long unitId;

    private String keyword;
}
