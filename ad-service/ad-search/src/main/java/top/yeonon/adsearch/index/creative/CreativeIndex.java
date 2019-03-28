package top.yeonon.adsearch.index.creative;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.yeonon.adsearch.index.IndexAware;

import java.util.Map;

/**
 * @Author yeonon
 * @date 2019/3/28 0028 19:52
 **/
@Component
@Slf4j
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    private static Map<Long, CreativeObject> objectMap;

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        objectMap.put(key, value);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        CreativeObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }


    }

    @Override
    public void delete(Long key, CreativeObject value) {
        objectMap.remove(key);
    }
}
