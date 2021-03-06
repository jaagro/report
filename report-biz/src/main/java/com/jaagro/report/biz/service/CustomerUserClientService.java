package com.jaagro.report.biz.service;

import com.jaagro.report.api.dto.customer.GetCustomerUserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author tony
 */
@FeignClient(value = "user")
public interface CustomerUserClientService {

    /**
     * id获取customerUser
     *
     * @param id
     * @return
     */
    @GetMapping("/customerUser/{id}")
    GetCustomerUserDto getCustomerUserById(@PathVariable("id") Integer id);
}
