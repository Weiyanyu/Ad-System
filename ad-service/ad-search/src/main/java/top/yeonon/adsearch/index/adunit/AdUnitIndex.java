package top.yeonon.adsearch.index.adunit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.yeonon.adsearch.index.IndexAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yeonon
 * @date 2019/3/27 0027 20:09
 **/
@Component
@Slf4j
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    private static final Map<Long, AdUnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {
        objectMap.put(key, value);
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        if (objectMap.containsKey(key)) {
            objectMap.get(key).update(value);
        } else {
            objectMap.put(key, value);
        }
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        objectMap.remove(key);
    }
}
