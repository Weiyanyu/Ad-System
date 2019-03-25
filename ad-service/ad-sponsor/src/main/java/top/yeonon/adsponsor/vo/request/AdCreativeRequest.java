package top.yeonon.adsponsor.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import top.yeonon.adsponsor.constant.CommonStatus;
import top.yeonon.adsponsor.entity.AdCreative;

import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/25 0025 11:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeRequest {

    private String name;

    private Integer type;

    private Integer materialType;

    private Integer height;

    private Integer width;

    private Long size;

    private Integer duration;

    private Integer auditStatus;

    private Long userId;

    private String url;

    public boolean createValidate() {
        return StringUtils.isNotEmpty(name)
                && type != null
                && materialType != null
                && userId != null
                && url != null
                && duration != null;
    }

    public AdCreative convertToCreative() {
        AdCreative adCreative = new AdCreative();

        adCreative.setWidth(width);
        adCreative.setUserId(userId);
        adCreative.setUrl(url);
        adCreative.setType(type);
        adCreative.setSize(size);
        adCreative.setName(name);
        adCreative.setMaterialType(materialType);
        adCreative.setHeight(height);
        adCreative.setDuration(duration);
        adCreative.setAuditStatus(CommonStatus.VALID.getCode());
        adCreative.setCreateTime(new Date());
        adCreative.setUpdateTime(new Date());
        return adCreative;
    }
}
