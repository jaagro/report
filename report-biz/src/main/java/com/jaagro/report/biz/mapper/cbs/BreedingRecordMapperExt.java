package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.finance.FeedMedicineDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 养殖记录查询
 * @author yj
 * @date 2019/4/1 11:50
 */
public interface BreedingRecordMapperExt {
    /**
     * 统计喂养次数及重量
     * @param batchNo
     * @param recordType
     * @param dayAge
     * @return
     * feedTimes 喂养次数
     * totalFeed 喂养总重量
     * unit 单位
     */
    Map<String,Object> statisticsByParams(@Param("batchNo") String batchNo,@Param("recordType") Integer recordType,@Param("dayAge") Integer dayAge);

    /**
     * 查询喂药明细
     * @param batchNo
     * @param productId
     * @param dayAgeStart
     * @param dayAgeEnd
     * @return
     */
    List<FeedMedicineDto> listFeedDrugItems(@Param("batchNo") String batchNo,@Param("productId") Integer productId, @Param("dayAgeStart") Integer dayAgeStart, @Param("dayAgeEnd") Integer dayAgeEnd);
}
