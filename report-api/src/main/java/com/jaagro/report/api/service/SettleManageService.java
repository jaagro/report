package com.jaagro.report.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.report.api.dto.settlemanage.DriverFeeCriteria;
import com.jaagro.report.api.dto.settlemanage.DriverFeeDetailsCriteria;
import com.jaagro.report.api.dto.settlemanage.ListDriverFeeCriteria;
import com.jaagro.report.api.dto.settlemanage.WaybillFeeCriteria;
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
     * @return
     * @author @Gao.
     */
    void createDriverSettleFeeMonthly(String month);

    /**
     * 生成客户结算费用月度报表
     *
     * @param month
     * @author yj
     */
    void createCustomerSettleFeeMonthly(String month);

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
}
