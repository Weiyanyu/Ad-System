package top.yeonon.adsearch.index.district;

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
 * @date 2019/3/28 0028 19:41
 **/
@Component
@Slf4j
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    private static final Map<String, Set<Long>> districtUnitMap;

    private static final Map<Long, Set<String>> unitDistrictMap;


    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }


    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(key, districtUnitMap,
                ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(unitId,
                    unitDistrictMap, ConcurrentSkipListSet::new);
            districts.add(key);
        }
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("no support update operate");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getOrCreate(key, districtUnitMap,
                ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(unitId,
                    unitDistrictMap, ConcurrentSkipListSet::new);
            districts.remove(key);
        }
    }

    public boolean match(Long unitId, Set<String> districts) {
        if (unitDistrictMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitDistrictMap.get(unitId))) {
            Set<String> unitDistricts = unitDistrictMap.get(unitId);
            return CollectionUtils.isSubCollection(districts, unitDistricts);
        }
        return false;
    }
}
