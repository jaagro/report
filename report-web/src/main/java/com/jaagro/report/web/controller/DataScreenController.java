package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.ReturnDataScreenDto;
import com.jaagro.utils.BaseResponse;
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
 * @author baiyiran
 * @Date 2019/1/17
 */
@Slf4j
@RestController
@Api(description = "大屏数据", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataScreenController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 大屏value的key
     */
    private static final String key = "数据大屏饲料value";
    /**
     * 大屏value的初始值
     */
    private final Integer valueFinal = 212174;
    /**
     * 毛鸡大屏value的key
     */
    private static final String chickenKey = "数据大屏value毛鸡";
    /**
     * 毛鸡大屏value的初始值
     */
    private final Integer chickenValueFinal = 9157510;

    /**
     * 饲料
     *
     * @return
     */
    @ApiOperation(value = "饲料运输总量")
    @GetMapping("/timingGrowthData")
    public List<ReturnDataScreenDto> timingGrowthData() {
        List<ReturnDataScreenDto> dataScreenDtoList = new ArrayList<>();
        ReturnDataScreenDto screenDto = new ReturnDataScreenDto();
        String value = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(value)) {
            Integer v;
            v = Integer.parseInt(value);
            screenDto.setValue(v);
        } else {
            screenDto.setValue(valueFinal);
        }
        dataScreenDtoList.add(screenDto);
        return dataScreenDtoList;
    }

    /**
     * 毛鸡
     *
     * @return
     */
    @ApiOperation(value = "毛鸡运输总量")
    @GetMapping("/timingGrowthDataChicken")
    public List<ReturnDataScreenDto> timingGrowthDataChicken() {
        List<ReturnDataScreenDto> dataScreenDtoList = new ArrayList<>();
        ReturnDataScreenDto screenDto = new ReturnDataScreenDto();
        String value = redisTemplate.opsForValue().get(chickenKey);
        if (!StringUtils.isEmpty(value)) {
            Integer v;
            v = Integer.parseInt(value);
            screenDto.setValue(v);
        } else {
            screenDto.setValue(chickenValueFinal);
        }
        dataScreenDtoList.add(screenDto);
        return dataScreenDtoList;
    }
}
