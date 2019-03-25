package top.yeonon.adsponsor.constant;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 17:45
 **/
public enum CreativeMaterialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),
    PNG(3, "png"),

    MP4(4, "mp4"),
    AVI(5, "avi"),

    TEX(6, "txt");

    private Integer code;

    private String description;

    CreativeMaterialType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
