package com.jaagro.report.biz.service;

import com.jaagro.report.api.dto.TruckDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author tony
 */
@FeignClient(value = "${feignClient.application.crm}")
//@FeignClient(value = "crm")
public interface TruckClientService {
    /**
     * 批量查询车辆信息
     *
     * @param truckIdList
     * @return
     */
    @GetMapping("/listTruckByIds")
    BaseResponse<List<TruckDto>> listTruckByIds(@RequestParam(value = "truckIdList") List<Integer> truckIdList);
}
