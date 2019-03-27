package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.ReturnDataScreenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
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


    /**
     * 客户贡献
     *
     * @return
     */
    @ApiOperation(value = "客户贡献前十")
    @GetMapping("/listTopTemCustomerData")
    public List<ReturnDataScreenDto> listTopTemCustomerData() {
        List<ReturnDataScreenDto> dataScreenDtoList = new ArrayList<>();
        ReturnDataScreenDto screenDto = new ReturnDataScreenDto();

        dataScreenDtoList.add(screenDto);
        return dataScreenDtoList;
    }



}
