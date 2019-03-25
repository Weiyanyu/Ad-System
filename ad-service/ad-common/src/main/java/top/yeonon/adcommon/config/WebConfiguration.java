package top.yeonon.adcommon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/19 0019 19:17
 **/
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //清空默认的几个配置
        converters.clear();

        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
