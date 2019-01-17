package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.ReturnDataScreenDto;
import com.jaagro.report.biz.schedule.DataScreenService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2019/1/17
 */
@Slf4j
@RestController
@Api(description = "大屏数据", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataScreenController {

    @Autowired
    private DataScreenService dataScreenService;

    @GetMapping("/timingGrowthData")
    public BaseResponse<List<ReturnDataScreenDto>> timingGrowthData() {
        List<ReturnDataScreenDto> dataScreenDtoList = new ArrayList<>();
        ReturnDataScreenDto screenDto = new ReturnDataScreenDto();
        screenDto.setValue(dataScreenService.doSomething());
        dataScreenDtoList.add(screenDto);
        return BaseResponse.successInstance(dataScreenDtoList);
    }

}
