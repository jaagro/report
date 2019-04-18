package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.settlemanage.*;
import com.jaagro.report.api.service.SettleManageService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author @Gao.
 */
@Slf4j
@RestController
@Api(description = "结算管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class SettleManageController {
    @Autowired
    private SettleManageService settleManageService;

    @ApiOperation("运单费用报表")
    @PostMapping("/listWaybillFee")
    public BaseResponse listWaybillFee(@RequestBody WaybillFeeCriteria criteria) {
        log.info("O listWaybillFee criteria={}",criteria);
        if (criteria.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (criteria.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        return BaseResponse.successInstance(settleManageService.listWaybillFee(criteria));
    }

    @ApiOperation("生成司机费用月度报表")
    @PostMapping("/createDriverSettleFeeMonthly")
    public BaseResponse createDriverSettleFeeMonthly(@RequestBody String month) {
        log.info("O createDriverSettleFeeMonthly month={}", month);
        settleManageService.createDriverSettleFeeMonthly(month);
        return BaseResponse.successInstance("触发成功");
    }

    @ApiOperation("司机费用月度报表")
    @PostMapping("/listDriverSettleFeeMonthly")
    public BaseResponse listDriverSettleFeeMonthly(@RequestBody ListDriverFeeCriteria criteria) {
        log.info("O listDriverSettleFeeMonthly params={}",criteria);
        return BaseResponse.successInstance(settleManageService.listDriverSettleFeeMonthly(criteria));
    }

    @ApiOperation("司机费用月度报表详情")
    @PostMapping("/driverSettleFeeMonthlyDetails")
    public BaseResponse driverSettleFeeMonthlyDetails(@RequestBody DriverFeeDetailsCriteria criteria) {
        log.info("O driverSettleFeeMonthlyDetails params={}",criteria);
        return BaseResponse.successInstance(settleManageService.driverSettleFeeMonthlyDetails(criteria));
    }

    @ApiOperation("生成客户费用月度报表")
    @PostMapping("/createCustomerSettleFeeMonthly")
    public BaseResponse createCustomerSettleFeeMonthly(@RequestBody String month) {
        log.info("O createCustomerSettleFeeMonthly month={}", month);
        settleManageService.createCustomerSettleFeeMonthly(month);
        return BaseResponse.successInstance("触发成功");
    }

    @ApiOperation("查询客户费用月度报表")
    @PostMapping("/listCustomerSettleFeeMonthly")
    public BaseResponse listCustomerSettleFeeMonthly(@RequestBody CustomerSettleFeeMonthlyCriteria criteria) {
        log.info("O listCustomerSettleFeeMonthly params={}", criteria);
        return BaseResponse.successInstance(settleManageService.listCustomerSettleFeeMonthly(criteria));
    }

    @ApiOperation("客户费用月度报表详情")
    @PostMapping("/customerSettleFeeMonthlyDetails")
    public BaseResponse customerSettleFeeMonthlyDetails(@RequestBody CustomerFeeDetailsCriteria criteria) {
        log.info("O customerSettleFeeMonthlyDetails params={}",criteria);
        return BaseResponse.successInstance(settleManageService.customerSettleFeeMonthlyDetails(criteria));
    }
}