package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.finance.*;

import java.util.List;

/**
 * 对接金融接口服务
 *
 * @author yj
 * @date 2019/3/27 11:05
 */
public interface FinanceService {
    /**
     * 获取客户基本信息
     *
     * @return
     */
    CustomerBaseInfoDto getCustomerBaseInfo();

    /**
     * 贷款申请选择协议
     * <p>
     * 返回当前客户的养殖计划列表
     *
     * @return
     */
    List<ReturnBreedingPlanInfoDto> listBreedingPlanInfo(String type);

    /**
     * 贷款申请 选择订单
     * <p>
     * 返回当前客户的养殖计划
     *
     * @return
     */
    List<ReturnBreedingPlanDetailsDto> getBreedingPlanInfo(BreedingPlanInfoCriteria criteria);

    /**
     * 贷款记录保存
     *
     * @param dto
     */
    ReturnCustomerInfoDto saveLoanApplyRecord(CreateLoanApplyRecordDto dto);
}
