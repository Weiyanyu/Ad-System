package top.yeonon.adsearch.index.adplan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.yeonon.adsearch.index.IndexAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yeonon
 * @date 2019/3/27 0027 20:02
 **/
@Component
@Slf4j
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    private static final Map<Long, AdPlanObject> objectMap;

    static {
        //保证线程安全
        objectMap = new ConcurrentHashMap<>();
    }


    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        objectMap.put(key, value);
    }

    @Override
    public void update(Long key, AdPlanObject value) {

        if (objectMap.containsKey(key)) {
            objectMap.get(key).update(value);
        } else {
            objectMap.put(key, value);
        }
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        objectMap.remove(key);
    }
}
