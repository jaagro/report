package com.jaagro.report.biz.service;

import com.jaagro.report.api.dto.customer.CustomerReturnDto;
import com.jaagro.report.api.dto.customer.ShowCustomerDto;
import com.jaagro.utils.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author tony
 */
@FeignClient(value = "${feignClient.application.crm}")
public interface CustomerClientService {
    /**
     * 获取客户显示信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getShowCustomer/{id}")
    ShowCustomerDto getShowCustomerById(@PathVariable("id") Integer id);

    /**
     * 获取客户详细信息
     * @param id
     * @return
     */
    @GetMapping("/getCustomerDetail/{id}")
    BaseResponse<CustomerReturnDto> getCustomerDetail(@PathVariable("id") Integer id);
}
