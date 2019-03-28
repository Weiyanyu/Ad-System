package top.yeonon.adsearch.index.creativeUnit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import top.yeonon.adsearch.index.IndexAware;
import top.yeonon.adsearch.index.adunit.AdUnitObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author yeonon
 * @date 2019/3/28 0028 19:57
 **/
@Component
@Slf4j
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {


    //<creativeId-unitId> -> CreativeUnitObject
    private static final Map<String, CreativeUnitObject> objectMap;

    //<creativeId> -> unitIds
    private static final Map<Long, Set<Long>> creativeUnitMap;

    //unitId -> creativeIds
    private static final Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        objectMap.put(key, value);

        Set<Long> unitIds = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isEmpty(unitIds)) {
            unitIds = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getCreativeId(), unitIds);
        }
        unitIds.add(value.getUnitId());

        Set<Long> creativeIds = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeIds)) {
            creativeIds = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeIds);
        }
        creativeIds.add(value.getCreativeId());
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {

        objectMap.remove(key);

        Set<Long> unitSet = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isNotEmpty(unitSet)) {
            unitSet.remove(value.getUnitId());
        }

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getCreativeId());
        }

    }

    public List<Long> selectAds(List<AdUnitObject> unitObjects) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return Collections.emptyList();
        }

        List<Long> result = new ArrayList<>();

        for (AdUnitObject unitObject : unitObjects) {

            Set<Long> adIds = unitCreativeMap.get(unitObject.getUnitId());
            if (CollectionUtils.isNotEmpty(adIds)) {
                result.addAll(adIds);
            }
        }

        return result;
    }
}
