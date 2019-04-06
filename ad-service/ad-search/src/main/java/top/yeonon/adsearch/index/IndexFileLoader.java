package top.yeonon.adsearch.index;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import top.yeonon.adcommon.dump.DataConstants;
import top.yeonon.adcommon.dump.table.*;
import top.yeonon.adsearch.client.vo.response.AdPlan;
import top.yeonon.adsearch.handler.AdLevelHandler;
import top.yeonon.adsearch.mysql.constant.OpType;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author yeonon
 * @date 2019/4/6 0006 19:07
 **/
@Component
@DependsOn(value = "dataTable")
public class IndexFileLoader {


    @PostConstruct
    public void init() {

        //加载第二层级
        List<String> adPlanStringList = loadDumpTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_PLAN);
        adPlanStringList.forEach(adPlanStr-> {
            AdLevelHandler.handleLevel2(JSON.parseObject(adPlanStr, AdPlanTable.class), OpType.ADD);
        });

        List<String> adCreativeStringList = loadDumpTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_CREATIVE);
        adCreativeStringList.forEach(adCreativeTableStr -> {
            AdLevelHandler.handleLevel2(JSON.parseObject(adCreativeTableStr, AdCreativeTable.class), OpType.ADD);
        });

        //加载第三层级
        List<String> adUnitTableStringList = loadDumpTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT);
        adUnitTableStringList.forEach(adUnitTableStr -> {
            AdLevelHandler.handleLevel3(JSON.parseObject(adUnitTableStr, AdUnitTable.class), OpType.ADD);
        });

        List<String> adCreativeUnitStringList = loadDumpTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_CREATIVE_UNIT);
        adCreativeUnitStringList.forEach(adCreativeUnitTableStr -> {
            AdLevelHandler.handleLevel3(JSON.parseObject(adCreativeUnitTableStr, AdCreativeUnitTable.class), OpType.ADD);
        });

        //加载第四层级
        List<String> adUnitDistrictStringList = loadDumpTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT_DISTRICT);
        adUnitDistrictStringList.forEach(adUnitDistrictStr -> {
            AdLevelHandler.handleLevel4(JSON.parseObject(adUnitDistrictStr, AdUnitDistrictTable.class), OpType.ADD);
        });

        List<String> adUnitItStringList = loadDumpTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT_IT);
        adUnitItStringList.forEach(adUnitItStr -> {
            AdLevelHandler.handleLevel4(JSON.parseObject(adUnitItStr, AdUnitItTable.class), OpType.ADD);
        });

        List<String> adUnitKeywordList = loadDumpTable(DataConstants.DATA_ROOT_DIR + DataConstants.AD_UNIT_KEYWORD);
        adUnitKeywordList.forEach(adUnitKeywordStr -> {
            AdLevelHandler.handleLevel4(JSON.parseObject(adUnitKeywordStr, AdUnitKeywordTable.class), OpType.ADD);
        });
    }


    private List<String> loadDumpTable(String filename) {

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("can't read the file : " + filename);
        }
    }
}
