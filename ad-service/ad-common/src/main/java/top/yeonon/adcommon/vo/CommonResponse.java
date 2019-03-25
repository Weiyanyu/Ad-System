package top.yeonon.adcommon.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author yeonon
 * @date 2019/3/19 0019 17:01
 **/
@Data
public class CommonResponse implements Serializable {

    private Integer code;
    private String message;
    private Object data;

    private CommonResponse() {}

    private CommonResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static  CommonResponseBuilder builder() {
        return new CommonResponseBuilder();
    }

    public static class CommonResponseBuilder {

        private Integer code;
        private String message;
        private Object data;

        public CommonResponseBuilder code(Integer code) {
            this.code = code;
            return this;
        }

        public CommonResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public CommonResponseBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public CommonResponse build() {
            return new CommonResponse(code, message, data);
        }

    }

}
