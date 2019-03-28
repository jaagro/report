package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.finance.CustomerBaseInfoDto;
import com.jaagro.report.api.dto.finance.ReturnBreedingPlanInfoDto;

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
    List<ReturnBreedingPlanInfoDto> listBreedingPlanInfo();

    /**
     * 贷款申请 选择订单
     * <p>
     * 返回当前客户的养殖计划
     *
     * @return
     */
    List<ReturnBreedingPlanInfoDto> getBreedingPlanInfo();
}
