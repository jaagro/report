package com.jaagro.report.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.report.api.dto.settlemanage.DriverFeeCriteria;
import com.jaagro.report.api.dto.settlemanage.WaybillFeeCriteria;


public interface SettleManageService {

    /**
     * 运单结算费用报表
     *
     * @param criteria
     * @return
     */
    PageInfo listWaybillFee(WaybillFeeCriteria criteria);

    /**
     * 司机费用
     *
     * @param
     * @return
     */
    void createDriverSettleFeeMonthly(String month);

    /**
     * 生成客户结算费用月度报表
     *
     * @param month
     * @author yj
     */
    void createCustomerSettleFeeMonthly(String month);
}
