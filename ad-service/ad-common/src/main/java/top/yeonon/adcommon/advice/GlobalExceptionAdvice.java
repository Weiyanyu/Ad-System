package top.yeonon.adcommon.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adcommon.vo.CommonResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author yeonon
 * @date 2019/3/19 0019 19:00
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(AdException.class)
    public CommonResponse handlerAdException(HttpServletRequest request,
                                             AdException e) {
        String message = String.format("request uri %s and method %s occur an error : %s",
                request.getRequestURI(),
                request.getMethod(),
                e.getMessage());

        log.info(message);
        return CommonResponse.builder()
                .code(-1)
                .message(message)
                .build();
    }
}
