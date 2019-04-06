package top.yeonon.adsearch.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yeonon
 * @date 2019/3/28 0028 20:59
 **/
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    //clz -> object(component instance)
    private static Map<Class<?>, Object> dataTableMap;

    static {
        dataTableMap = new ConcurrentHashMap<>();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    public static <T> T of(Class<T> clz) {
        T instance = (T) dataTableMap.get(clz);
        if (instance != null) {
            return instance;
        }
        instance = DataTable.bean(clz);
        dataTableMap.put(clz, instance);
        return instance;
    }

    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    private static <T> T bean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }
}
