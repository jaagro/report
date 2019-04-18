package com.jaagro.report.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.report.api.dto.settlemanage.*;
import com.jaagro.report.api.entity.CustomerSettleFeeMonthly;
import com.jaagro.utils.BaseResponse;

import java.util.List;
import com.jaagro.report.api.entity.DriverSettleFeeMonthly;

import java.util.List;


public interface SettleManageService {

    /**
     * 运单结算费用报表
     *
     * @param criteria
     * @return
     * @author @Gao.
     */
    PageInfo listWaybillFee(WaybillFeeCriteria criteria);

    /**
     * 生成司机费用月度报表
     *
     * @param
     * @author @Gao.
     * @return
     */
    void createDriverSettleFeeMonthly(String month);

    /**
     * 生成客户结算费用月度报表
     *
     * @param month
     * @author yj
     * @return
     */
    void createCustomerSettleFeeMonthly(String month);

    /**
     * 查询客户结算费用月度报表
     * @param criteria
     * @return
     */
    PageInfo<CustomerSettleFeeMonthly> listCustomerSettleFeeMonthly(CustomerSettleFeeMonthlyCriteria criteria);

    /**
     * 计算账单起始时间
     * @param month
     * @param settleBillingDayConfigType
     * @return
     */
    ReturnTimeIntervalDto accumulativeTimeInterval(String month, Integer settleBillingDayConfigType);

    /**
     * 司机费用月度报表列表
     *
     * @param criteria
     * @return
     */
    PageInfo listDriverSettleFeeMonthly(ListDriverFeeCriteria criteria);

    /**
     * 司机结算费用详情
     *
     * @param criteria
     * @return
     */
    PageInfo driverSettleFeeMonthlyDetails(DriverFeeDetailsCriteria criteria);

    /**
     * 客户结算费用详情
     * @param criteria
     * @return
     */
    PageInfo customerSettleFeeMonthlyDetails(CustomerFeeDetailsCriteria criteria);
}
