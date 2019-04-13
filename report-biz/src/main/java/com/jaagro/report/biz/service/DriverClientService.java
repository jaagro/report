package com.jaagro.report.biz.service;

import com.jaagro.report.api.dto.DriverReturnDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yj
 * @since 2018/11/29
 */
@FeignClient(value = "${feignClient.application.user}")
//@FeignClient(value = "user")
public interface DriverClientService {
    /**
     * 批量获取司机信息
     *
     * @param driverIdList
     * @return
     */
    @GetMapping("/listDriverByIds")
    BaseResponse<List<DriverReturnDto>> listDriverByIds(@RequestParam("driverIdList") List<Integer> driverIdList);
}
