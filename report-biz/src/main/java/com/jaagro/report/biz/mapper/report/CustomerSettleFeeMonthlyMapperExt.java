package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.entity.CustomerSettleFeeMonthly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @date 2019/4/16 15:00
 */
public interface CustomerSettleFeeMonthlyMapperExt extends CustomerSettleFeeMonthlyMapper {
    /**
     * 批量插入
     * @param feeMonthlyList
     */
    void batchInsert(@Param("feeMonthlyList") List<CustomerSettleFeeMonthly> feeMonthlyList);
}
