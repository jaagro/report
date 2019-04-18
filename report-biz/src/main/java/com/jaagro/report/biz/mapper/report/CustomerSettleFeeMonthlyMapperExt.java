package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.settlemanage.CustomerSettleFeeMonthlyCriteria;
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

    /**
     * 分页查询客户结算月度费用列表
     * @param criteria
     * @return
     */
    List<CustomerSettleFeeMonthly> listByCriteria(CustomerSettleFeeMonthlyCriteria criteria);

    /**
     * 根据月份删除
     * @param month
     * @return
     */
    Integer delByReportTime(@Param("month") String month);
}
