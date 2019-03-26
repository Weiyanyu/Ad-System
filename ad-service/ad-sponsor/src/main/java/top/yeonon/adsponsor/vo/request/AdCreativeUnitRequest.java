package top.yeonon.adsponsor.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/25 0025 12:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreativeUnitRequest {

    private List<CreativeUnitItem> creativeUnitItems;

    public boolean createValidate() {
        return creativeUnitItems.stream()
                .noneMatch(creativeUnitItem -> {
                    return creativeUnitItem.creativeId == null
                            || creativeUnitItem.unitId == null;
                });
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreativeUnitItem {
        private Long creativeId;

        private Long unitId;
    }
}
