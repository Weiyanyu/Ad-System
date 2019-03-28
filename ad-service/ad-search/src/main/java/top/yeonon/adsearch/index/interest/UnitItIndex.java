package top.yeonon.adsearch.index.interest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import top.yeonon.adsearch.index.IndexAware;
import top.yeonon.adsearch.utils.CommonUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author yeonon
 * @date 2019/3/28 0028 13:05
 **/
@Component
@Slf4j
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    private static final Map<String, Set<Long>> itUnitMap;
    private static final Map<Long, Set<String>> unitItMap;

    static {
        unitItMap = new ConcurrentHashMap<>();
        itUnitMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(key,
                itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> its = CommonUtils.getOrCreate(unitId,
                    unitItMap, ConcurrentSkipListSet::new);
            its.add(key);
        }
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("no support update operate");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(key,
                itUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> its = CommonUtils.getOrCreate(unitId,
                    unitItMap, ConcurrentSkipListSet::new);
            its.remove(key);
        }
    }

    public boolean match(Long unitId, Set<String> itTags) {
        if (unitItMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            Set<String> unitItTags = unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(itTags, unitItTags);
        }
        return false;
    }
}
