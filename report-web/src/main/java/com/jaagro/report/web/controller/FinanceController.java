package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.finance.CustomerBaseInfoDto;
import com.jaagro.report.api.dto.finance.ReturnBreedingPlanInfoDto;
import com.jaagro.report.api.service.FinanceService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对接金融接口
 *
 * @author yj
 * @date 2019/3/27 11:01
 */
@RestController
@Slf4j
@Api(description = "对接金融接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class FinanceController {
    @Autowired
    private FinanceService financeService;

    @ApiOperation("获取客户基本信息")
    @GetMapping("/getCustomerBaseInfo")
    public BaseResponse getCustomerBaseInfo() {
        CustomerBaseInfoDto dto = financeService.getCustomerBaseInfo();
        if (dto == null) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.successInstance(dto);
    }

    @ApiOperation("获取当前客户养殖批次列表")
    @GetMapping("/listBreedingPlanBaseInfo")
    public BaseResponse listBreedingPlanInfo() {
        List<ReturnBreedingPlanInfoDto> dtos = financeService.listBreedingPlanInfo();
        if (CollectionUtils.isEmpty(dtos)) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.successInstance(dtos);
    }

    @ApiOperation("获取当前客户养殖批次信息")
    @GetMapping("/getBreedingPlanInfo")
    public BaseResponse getBreedingPlanInfo() {

        return BaseResponse.successInstance(null);
    }
}
