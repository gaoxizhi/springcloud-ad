package net.gaox.search.index;

import com.alibaba.fastjson.JSON;
import net.gaox.common.dump.DataConstant;
import net.gaox.common.dump.table.*;
import net.gaox.search.handler.AdLevelDataHandler;
import net.gaox.search.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1. 索引之间存在着层级的划分, 也就是依赖关系的划分
 * <p> 2. 加载全量索引其实是增量索引 "添加" 的一种特殊实现 </p>
 * <p>
 * 依赖dataTable Bean，加载
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    /**
     * 系统启动时将索引全量加载到系统
     */
    @PostConstruct
    public void init() {

        List<String> adPlanStrings = loadDumpData(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_PLAN));
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, AdPlanTable.class), OpType.ADD));

        List<String> adCreativeStrings = loadDumpData(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_CREATIVE));
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(JSON.parseObject(c, AdCreativeTable.class), OpType.ADD));

        List<String> adUnitStrings = loadDumpData(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT));
        adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(JSON.parseObject(u, AdUnitTable.class), OpType.ADD));

        List<String> adCreativeUnitStrings = loadDumpData(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_CREATIVE_UNIT));
        adCreativeUnitStrings.forEach(cu -> AdLevelDataHandler.handleLevel3(JSON.parseObject(cu, AdCreativeUnitTable.class), OpType.ADD));

        List<String> adUnitDistrictStrings = loadDumpData(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT_DISTRICT));
        adUnitDistrictStrings.forEach(d -> AdLevelDataHandler.handleLevel4(JSON.parseObject(d, AdUnitDistrictTable.class), OpType.ADD));

        List<String> adUnitItStrings = loadDumpData(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT_IT));
        adUnitItStrings.forEach(i -> AdLevelDataHandler.handleLevel4(JSON.parseObject(i, AdUnitItTable.class), OpType.ADD));

        List<String> adUnitKeywordStrings = loadDumpData(String.format("%s%s", DataConstant.DATA_ROOT_DIR, DataConstant.AD_UNIT_KEYWORD));
        adUnitKeywordStrings.forEach(k -> AdLevelDataHandler.handleLevel4(JSON.parseObject(k, AdUnitKeywordTable.class), OpType.ADD));
    }

    /**
     * 读取写入数据，**.data文件
     *
     * @param fileName
     * @return
     */
    private List<String> loadDumpData(String fileName) {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            //抛出异常，在过滤器中拦截信息
            throw new RuntimeException(ex.getMessage());
        }
    }
}