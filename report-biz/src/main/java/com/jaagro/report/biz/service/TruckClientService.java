package com.jaagro.report.biz.service;

import com.jaagro.report.api.dto.TruckDto;
import com.jaagro.report.api.dto.truck.ShowTruckDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 模糊查询车辆id
     *
     * @param truckNumber
     * @return
     */
    @PostMapping("/getTruckIdsByTruckNum/{truckNumber}")
    List<Integer> getTruckIdsByTruckNum(@PathVariable(value = "truckNumber") String truckNumber);

    /**
     * 获取车辆对象
     *
     * @param truckId
     * @return
     */
    @GetMapping("/truckToFeign/{truckId}")
    ShowTruckDto getTruckByIdReturnObject(@PathVariable("truckId") Integer truckId);

}
