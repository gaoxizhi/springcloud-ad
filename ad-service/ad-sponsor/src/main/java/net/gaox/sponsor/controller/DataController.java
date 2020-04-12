package net.gaox.sponsor.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.gaox.common.dump.DataConstant;
import net.gaox.common.dump.table.AdCreativeUnitTable;
import net.gaox.common.dump.table.AdUnitDistrictTable;
import net.gaox.common.dump.table.AdUnitItTable;
import net.gaox.common.dump.table.AdUnitKeywordTable;
import net.gaox.sponsor.constant.CommonStatus;
import net.gaox.sponsor.dao.AdPlanRepository;
import net.gaox.sponsor.dao.AdUnitRepository;
import net.gaox.sponsor.dao.CreativeRepository;
import net.gaox.sponsor.dao.unit_condition.AdUnitDistrictRepository;
import net.gaox.sponsor.dao.unit_condition.AdUnitItRepository;
import net.gaox.sponsor.dao.unit_condition.AdUnitKeywordRepository;
import net.gaox.sponsor.dao.unit_condition.CreativeUnitRepository;
import net.gaox.sponsor.entity.AdPlan;
import net.gaox.sponsor.entity.AdUnit;
import net.gaox.sponsor.entity.Creative;
import net.gaox.sponsor.entity.unit_condition.AdUnitDistrict;
import net.gaox.sponsor.entity.unit_condition.AdUnitIt;
import net.gaox.sponsor.entity.unit_condition.AdUnitKeyword;
import net.gaox.sponsor.entity.unit_condition.CreativeUnit;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p> 更新索引数据控制器 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/21 22:41
 */
@Slf4j
@RestController
@RequestMapping("/init/data")
public class DataController {

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;
    private final AdUnitDistrictRepository districtRepository;
    private final AdUnitItRepository itRepository;
    private final AdUnitKeywordRepository keywordRepository;

    public DataController(AdPlanRepository planRepository, AdUnitRepository unitRepository,
                          CreativeRepository creativeRepository, CreativeUnitRepository creativeUnitRepository,
                          AdUnitDistrictRepository districtRepository, AdUnitItRepository itRepository,
                          AdUnitKeywordRepository keywordRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
        this.districtRepository = districtRepository;
        this.itRepository = itRepository;
        this.keywordRepository = keywordRepository;
    }

    @GetMapping
    public void dumpAdTableData() {

        dumpAdPlanTable(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT));
        dumpAdCreativeTable(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_CREATIVE));
        dumpAdCreativeUnitTable(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_CREATIVE_UNIT));
        dumpAdUnitDistrictTable(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT_DISTRICT));
        dumpAdUnitItTable(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT_IT));
        dumpAdUnitKeywordTable(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT_KEYWORD));
    }

    private void dumpAdPlanTable(String fileName) {

        List<AdPlan> adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }

        List<Object> planTables = adPlans.stream().map(AdPlan::toAdPlanTable).collect(Collectors.toList());
        writer2file(fileName, planTables, "dumpAdPlanTable");
    }

    private void dumpAdUnitTable(String fileName) {

        List<AdUnit> adUnits = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }

        List<Object> unitTables = adUnits.stream().map(AdUnit::toAdUnitTable).collect(Collectors.toList());
        writer2file(fileName, unitTables, "dumpAdUnitTable");
    }

    private void dumpAdCreativeTable(String fileName) {

        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        List<Object> creativeTables = creatives.stream().map(Creative::toAdCreativeTable).collect(Collectors.toList());
        writer2file(fileName, creativeTables, "dumpAdCreativeTable");
    }

    private void dumpAdCreativeUnitTable(String fileName) {

        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }

        List<Object> creativeUnitTables = creativeUnits.stream()
                .map(c -> new AdCreativeUnitTable(c.getCreativeId(), c.getUnitId())).collect(Collectors.toList());

        writer2file(fileName, creativeUnitTables, "dumpAdCreativeUnit");
    }

    private void dumpAdUnitDistrictTable(String fileName) {

        List<AdUnitDistrict> unitDistricts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }

        List<Object> unitDistrictTables = unitDistricts.stream()
                .map(d -> new AdUnitDistrictTable(d.getUnitId(), d.getProvince(), d.getCity())).collect(Collectors.toList());
        writer2file(fileName, unitDistrictTables, "dumpAdUnitDistrictTable");
    }

    private void dumpAdUnitItTable(String fileName) {
        List<AdUnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }

        List<Object> unitItTables = unitIts.stream()
                .map(i -> new AdUnitItTable(i.getUnitId(), i.getItTag())).collect(Collectors.toList());

        writer2file(fileName, unitItTables, "dumpAdUnitItTable");
    }

    private void dumpAdUnitKeywordTable(String fileName) {

        List<AdUnitKeyword> unitKeywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }

        List<Object> unitItTables = unitKeywords.stream()
                .map(k -> new AdUnitKeywordTable(k.getUnitId(), k.getKeyword())).collect(Collectors.toList());
        writer2file(fileName, unitItTables, "dumpAdUnitItTable");

    }

    private void writer2file(String fileName, List<Object> list, String msg) {
        File file = new File(fileName);
        File fileParent = file.getParentFile();
        // 当父目录不存在时，创建父目录
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getPath()))) {
            String collect = list.stream().map(JSON::toJSONString).collect(Collectors.joining("\n"));
            writer.write(collect);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("{} error", msg);
        }
    }
}