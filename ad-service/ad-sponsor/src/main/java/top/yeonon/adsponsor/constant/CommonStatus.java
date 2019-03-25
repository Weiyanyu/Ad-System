package top.yeonon.adsponsor.constant;

/**
 * @Author yeonon
 * @date 2019/3/20 0020 21:07
 **/
public enum CommonStatus {
    VALID(1, "有效状态"),
    INVAILD(0, "无效状态");

    private Integer code;
    private String description;

    CommonStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
