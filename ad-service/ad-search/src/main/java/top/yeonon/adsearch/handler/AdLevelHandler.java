package top.yeonon.adsearch.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import top.yeonon.adcommon.dump.table.*;
import top.yeonon.adsearch.client.vo.response.AdPlan;
import top.yeonon.adsearch.index.DataTable;
import top.yeonon.adsearch.index.IndexAware;
import top.yeonon.adsearch.index.adplan.AdPlanIndex;
import top.yeonon.adsearch.index.adplan.AdPlanObject;
import top.yeonon.adsearch.index.adunit.AdUnitIndex;
import top.yeonon.adsearch.index.adunit.AdUnitObject;
import top.yeonon.adsearch.index.creative.CreativeIndex;
import top.yeonon.adsearch.index.creative.CreativeObject;
import top.yeonon.adsearch.index.creativeUnit.CreativeUnitIndex;
import top.yeonon.adsearch.index.creativeUnit.CreativeUnitObject;
import top.yeonon.adsearch.index.district.UnitDistrictIndex;
import top.yeonon.adsearch.mysql.constant.OpType;
import top.yeonon.adsearch.utils.CommonUtils;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Level即层级，在我们的系统中，第一层级是用户，第二层级是创意和推广计划，第三层级是推广单元，推广单元约束是第四层级
 * @Author yeonon
 * @date 2019/4/6 0006 18:17
 **/
@Slf4j
public class AdLevelHandler {


    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index,
                                                 K key, V value, OpType opType) {
        switch (opType) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }

    public static void handleLevel2(AdPlanTable adPlanTable, OpType opType) {
        AdPlanObject adPlanObject = new AdPlanObject(
                adPlanTable.getId(),
                adPlanTable.getUserId(),
                adPlanTable.getPlanStatus(),
                adPlanTable.getStartDate(),
                adPlanTable.getEndDate()
        );

        handleBinlogEvent(
                DataTable.of(AdPlanIndex.class),
                adPlanObject.getPlanId(),
                adPlanObject,
                opType
        );
    }

    public static void handleLevel2(AdCreativeTable adCreativeTable, OpType opType) {
        CreativeObject creativeObject = new CreativeObject(
                adCreativeTable.getAdId(),
                adCreativeTable.getName(),
                adCreativeTable.getType(),
                adCreativeTable.getMaterialType(),
                adCreativeTable.getHeight(),
                adCreativeTable.getWidth(),
                adCreativeTable.getAuditStatus(),
                adCreativeTable.getAdUrl()
        );

        handleBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                opType
        );
    }

    public static void handleLevel3(AdUnitTable adUnitTable, OpType opType) {
        AdPlanObject adPlanObject = DataTable.
                of(AdPlanIndex.class).
                get(adUnitTable.getPlanId());
        if (adPlanObject == null) {
            log.error("can't handle the unit index. cause by can't found plan id {}", adUnitTable.getPlanId());
            return;
        }

        AdUnitObject adUnitObject = new AdUnitObject(
                adUnitTable.getUnitId(),
                adUnitTable.getUnitStatus(),
                adUnitTable.getPositionType(),
                adUnitTable.getPlanId(),
                adPlanObject
        );

        handleBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                adUnitObject.getUnitId(),
                adUnitObject,
                opType
        );
    }

    public static void handleLevel3(AdCreativeUnitTable adCreativeUnitTable, OpType opType) {
        if (OpType.UPDATE.equals(opType)) {
            log.error("can't handle update operate");
            return;
        }

        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class)
                                        .get(adCreativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class)
                                        .get(adCreativeUnitTable.getCreativeId());

        if (creativeObject == null || adUnitObject == null) {
            log.error("can't handle creative unit index. Because can't found creativeObject or unitObject in index cache");
            return;
        }

        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                adCreativeUnitTable.getCreativeId(),
                adCreativeUnitTable.getUnitId()
        );

        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(creativeUnitObject.getCreativeId().toString(), creativeUnitObject.getUnitId().toString()),
                creativeUnitObject,
                opType
        );
    }

    public static void handleLevel4(AdUnitDistrictTable adUnitDistrictTable, OpType opType) {
        if (opType.equals(OpType.UPDATE)) {
            log.error("can't handle update operate");
            return;
        }

        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class)
                .get(adUnitDistrictTable.getUnitId());
        if (adUnitObject == null) {
            log.error("can't handle creative unit index. Because can't found unitObject in index cache");
            return;
        }

        String key = CommonUtils.stringConcat(
                adUnitDistrictTable.getProvince(),
                adUnitDistrictTable.getCity());

        Set<Long> value = new HashSet<>();
        value.add(adUnitDistrictTable.getUnitId());
        handleBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key,
                value,
                opType
        );
    }

    public static void handleLevel4(AdUnitItTable adUnitItTable, OpType opType) {
        if (opType.equals(OpType.UPDATE)) {
            log.error("can't handle update operate");
            return;
        }

        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class)
                .get(adUnitItTable.getUnitId());
        if (adUnitObject == null) {
            log.error("can't handle creative unit index. Because can't found unitObject in index cache");
            return;
        }

        String key = adUnitItTable.getItTag();
        Set<Long> value = new HashSet<>();
        value.add(adUnitItTable.getUnitId());
        handleBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key,
                value,
                opType
        );
    }

    public static void handleLevel4(AdUnitKeywordTable adUnitKeywordTable, OpType opType) {
        if (opType.equals(OpType.UPDATE)) {
            log.error("can't handle update operate");
            return;
        }

        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class)
                .get(adUnitKeywordTable.getUnitId());
        if (adUnitObject == null) {
            log.error("can't handle creative unit index. Because can't found unitObject in index cache");
            return;
        }

        String key = adUnitKeywordTable.getKeyword();
        Set<Long> value = new HashSet<>();
        value.add(adUnitKeywordTable.getUnitId());
        handleBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key,
                value,
                opType
        );
    }

}
