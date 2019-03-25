package top.yeonon.adsponsor.constant;

import lombok.Getter;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 17:43
 **/
@Getter
public enum CreativeType {

    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");

    private Integer code;
    private String description;

    CreativeType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
