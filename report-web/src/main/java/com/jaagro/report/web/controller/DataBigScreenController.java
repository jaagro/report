package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.ContributionTopTenCustomerDto;
import com.jaagro.report.api.dto.ReturnDataScreenDto;
import com.jaagro.report.api.service.DataBigScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gavin
 * @Date 2019/3/27
 */
@Slf4j
@RestController
@Api(description = "数据大屏管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataBigScreenController {
@Autowired
private DataBigScreenService dataBigScreenService;

    /**
     * 客户贡献
     *
     * @return
     */
    @ApiOperation(value = "客户贡献前十")
    @GetMapping("/listTopTenCustomerData")
    public List<ContributionTopTenCustomerDto> listTopTenCustomerData() {
        List<ContributionTopTenCustomerDto>  dataScreenDtoList = dataBigScreenService.listTopTenCustomerData();
        return dataScreenDtoList;
    }



}