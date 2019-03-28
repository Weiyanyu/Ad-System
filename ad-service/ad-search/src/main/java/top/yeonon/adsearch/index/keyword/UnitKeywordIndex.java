package top.yeonon.adsearch.index.keyword;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import top.yeonon.adsearch.index.IndexAware;
import top.yeonon.adsearch.utils.CommonUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author yeonon
 * @date 2019/3/27 0027 20:54
 **/
@Component
@Slf4j
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

    private static final Map<String, Set<Long>> keywordUnitMap;
    private static final Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }
        Set<Long> unitIds = keywordUnitMap.get(key);
        if (unitIds == null) {
            return Collections.emptySet();
        }
        return unitIds;
    }

    @Override
    public void add(String key, Set<Long> value) {

        Set<Long> unitIds = CommonUtils.getOrCreate(key,
                keywordUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> keywords = CommonUtils.getOrCreate(unitId,
                    unitKeywordMap, ConcurrentSkipListSet::new);
            keywords.add(key);
        }
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("no support update operator");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        Set<Long> unitIds = CommonUtils.getOrCreate(key,
                keywordUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> keywords = CommonUtils.getOrCreate(unitId,
                    unitKeywordMap, ConcurrentSkipListSet::new);
            keywords.remove(key);
        }

    }


    public boolean match(Long unitId, Set<String> keywords) {
        if (unitKeywordMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {
            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }
        return false;
    }
}
