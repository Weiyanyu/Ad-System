package top.yeonon.adsponsor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yeonon.adcommon.dump.DataConstants;
import top.yeonon.adcommon.dump.table.*;
import top.yeonon.adsponsor.constant.CommonStatus;
import top.yeonon.adsponsor.entity.AdCreative;
import top.yeonon.adsponsor.entity.AdPlan;
import top.yeonon.adsponsor.entity.AdUnit;
import top.yeonon.adsponsor.entity.unitCondition.AdCreativeUnit;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitDistrict;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitIt;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitKeyword;
import top.yeonon.adsponsor.repository.AdCreativeRepository;
import top.yeonon.adsponsor.repository.AdPlanRepository;
import top.yeonon.adsponsor.repository.AdUnitRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdCreativeUnitRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitDistrictRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitItRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitKeywordRepository;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class AdSponsorApplicationTests {

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdCreativeRepository adCreativeRepository;

    @Autowired
    private AdUnitRepository adUnitRepository;

    @Autowired
    private AdCreativeUnitRepository adCreativeUnitRepository;

    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;

    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;

    @Autowired
    private AdUnitItRepository adUnitItRepository;

    @Test
    public void contextLoads() {
        dumpAdPlanTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_PLAN);
        dumpAdUnitTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT);
        dumpAdCreative(DataConstants.DATA_ROOT_DIR + DataConstants.AD_CREATIVE);
        dumpCreativeUnitTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_CREATIVE_UNIT);
        dumpAdUnitItTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT_IT);
        dumpAdUnitKeywordTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT_KEYWORD);
        dumpAdUnitDistrictTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT_DISTRICT);

    }


    private void dumpAdPlanTable(String filename) {
        List<AdPlan> adPlanList =  adPlanRepository.findAllByPlanStatus(CommonStatus.VALID.getCode());

        List<AdPlanTable> adPlanTableList = new LinkedList<>();

        adPlanList.forEach(adPlan -> {
            adPlanTableList.add(new AdPlanTable(
                    adPlan.getId(),
                    adPlan.getUserId(),
                    adPlan.getPlanStatus(),
                    adPlan.getStartDate(),
                    adPlan.getEndDate()
            ));
        });

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable adPlanTable : adPlanTableList) {
                writer.write(JSON.toJSONString(adPlanTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump adplan table error!");
        }
    }

    private void dumpAdCreative(String filename) {
        List<AdCreative> adCreativeList =  adCreativeRepository.findAllByAuditStatus(CommonStatus.VALID.getCode());

        List<AdCreativeTable> adCreativeTableList = new LinkedList<>();

        adCreativeList.forEach(adCreative -> {
            adCreativeTableList.add(new AdCreativeTable(
                    adCreative.getId(),
                    adCreative.getName(),
                    adCreative.getType(),
                    adCreative.getMaterialType(),
                    adCreative.getHeight(),
                    adCreative.getWidth(),
                    adCreative.getAuditStatus(),
                    adCreative.getUrl()
            ));
        });

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable adCreativeTable : adCreativeTableList) {
                writer.write(JSON.toJSONString(adCreativeTable));
                writer.newLine();
            }

        } catch (IOException e) {
            log.error("dump ad creative table error!");
        }
    }

    private void dumpAdUnitTable(String filename) {
        List<AdUnit> adUnitList = adUnitRepository.findAllByUnitStatus(CommonStatus.VALID.getCode());

        List<AdUnitTable> adUnitTableList = new LinkedList<>();

        adUnitList.forEach(adUnit -> {
            adUnitTableList.add(new AdUnitTable(
               adUnit.getId(),
               adUnit.getUnitStatus(),
               adUnit.getPositionType(),
               adUnit.getPlanId()
            ));
        });

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdUnitTable adUnitTable : adUnitTableList) {
                writer.write(JSON.toJSONString(adUnitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump ad unit table error!");
        }
    }

    private void dumpCreativeUnitTable(String filename) {
        List<AdCreativeUnit> adCreativeUnitList = adCreativeUnitRepository.findAll();

        List<AdCreativeUnitTable> adCreativeUnitTableList = new LinkedList<>();

        adCreativeUnitList.forEach(adCreativeUnit -> {
            adCreativeUnitTableList.add(new AdCreativeUnitTable(
                    adCreativeUnit.getCreativeId(),
                    adCreativeUnit.getUnitId()
            ));
        });

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable adCreativeUnitTable : adCreativeUnitTableList) {
                writer.write(JSON.toJSONString(adCreativeUnitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump creative unit table error!");
        }
    }

    private void dumpAdUnitKeywordTable(String filename) {
        List<AdUnitKeyword> adUnitKeywordList = adUnitKeywordRepository.findAll();

        List<AdUnitKeywordTable> adUnitKeywordTableList = new LinkedList<>();

        adUnitKeywordList.forEach(adUnitKeyword -> {
            adUnitKeywordTableList.add(new AdUnitKeywordTable(
                    adUnitKeyword.getUnitId(),
                    adUnitKeyword.getKeyword()
            ));
        });

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable adUnitKeywordTable : adUnitKeywordTableList) {
                writer.write(JSON.toJSONString(adUnitKeywordTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump ad unit keyword table error!");
        }
    }


    private void dumpAdUnitItTable(String filename) {
        List<AdUnitIt> adUnitItList = adUnitItRepository.findAll();

        List<AdUnitItTable> adUnitItTableList = new LinkedList<>();

        adUnitItList.forEach(adUnitIt -> {
            adUnitItTableList.add(new AdUnitItTable(
                    adUnitIt.getUnitId(),
                    adUnitIt.getItTag()
            ));
        });

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable adUnitItTable : adUnitItTableList) {
                writer.write(JSON.toJSONString(adUnitItTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump ad unit it table error!");
        }
    }


    private void dumpAdUnitDistrictTable(String filename) {
        List<AdUnitDistrict> adUnitDistrictList = adUnitDistrictRepository.findAll();

        List<AdUnitDistrictTable> adUnitDistrictTableList = new LinkedList<>();

        adUnitDistrictList.forEach(adUnitDistrict -> {
            adUnitDistrictTableList.add(new AdUnitDistrictTable(
                    adUnitDistrict.getUnitId(),
                    adUnitDistrict.getProvince(),
                    adUnitDistrict.getCity()
            ));
        });

        Path path = Paths.get(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable adUnitDistrictTable : adUnitDistrictTableList) {
                writer.write(JSON.toJSONString(adUnitDistrictTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dump ad unit district table error!");
        }
    }
}
