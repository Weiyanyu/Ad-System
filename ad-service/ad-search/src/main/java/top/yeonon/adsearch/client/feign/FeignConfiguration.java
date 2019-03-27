package top.yeonon.adsearch.client.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yeonon
 * @date 2019/3/27 0027 11:23
 **/
@Configuration
public class FeignConfiguration {

    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }
}
