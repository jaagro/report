package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.finance.*;
import com.jaagro.report.api.service.FinanceService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/listBreedingPlanBaseInfo/{type}")
    public BaseResponse listBreedingPlanInfo(@PathVariable String type) {
        if (type == null) {
            return BaseResponse.errorInstance("类型值不能为空");
        }
        List<ReturnBreedingPlanInfoDto> dtos = financeService.listBreedingPlanInfo(type);
        if (CollectionUtils.isEmpty(dtos)) {
            return BaseResponse.queryDataEmpty();
        }
        return BaseResponse.successInstance(dtos);
    }

//    @ApiOperation("获取当前客户养殖批次信息")
//    @PostMapping("/getBreedingPlanInfo")
//    public BaseResponse getBreedingPlanInfo(@RequestBody BreedingPlanInfoCriteria criteria) {
//        if (criteria.getBatchNo() == null) {
//            return BaseResponse.errorInstance("养殖批次号不能为空");
//        }
//        List<ReturnBreedingPlanDetailsDto> breedingPlanInfo = financeService.getBreedingPlanInfo(criteria);
//        if (CollectionUtils.isEmpty(breedingPlanInfo)) {
//            return BaseResponse.queryDataEmpty();
//        }
//        return BaseResponse.successInstance(breedingPlanInfo);
//    }

    @ApiOperation("保存养殖户贷款记录")
    @PostMapping("/saveLoanApplyRecord")
    public BaseResponse saveLoanApplyRecord(@RequestBody CreateLoanApplyRecordDto dto) {
        if (dto.getLoanType() == null) {
            return BaseResponse.errorInstance("贷款类型不能为空");
        }
        if (dto.getBatchNo() == null) {
            return BaseResponse.errorInstance("采购单号不能为空");
        }
        return BaseResponse.successInstance(financeService.saveLoanApplyRecord(dto));
    }

    @ApiOperation("养殖详情")
    @GetMapping("/getBreedingDetail")
    public BaseResponse getBreedingDetail(@RequestParam String batchNo){
        log.info("O getBreedingDetail batchNo={}",batchNo);
        return BaseResponse.successInstance(financeService.getBreedingDetail(batchNo));
    }
}
