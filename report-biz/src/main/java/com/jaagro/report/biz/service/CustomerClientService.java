package com.jaagro.report.biz.service;

import com.jaagro.report.api.dto.customer.*;
import com.jaagro.utils.BaseResponse;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


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
     *
     * @param id
     * @return
     */
    @GetMapping("/getCustomerDetail/{id}")
    BaseResponse<CustomerReturnDto> getCustomerDetail(@PathVariable("id") Integer id);

    /**
     * 查询客户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getContactsById/{id}")
    BaseResponse<CustomerContacts> getContactsById(@PathVariable("id") Integer id);

    /**
     * 根据客户id查询主客户联系人
     *
     * @param customerId
     * @return
     */
    @Ignore
    @GetMapping("/getCustomerContactByCustomerId/{customerId}")
    CustomerContactsReturnDto getCustomerContactByCustomerId(@PathVariable("customerId") Integer customerId);

    /**
     * 根据关键字查询客户id集合
     *
     * @param keyword
     * @return
     */
    @GetMapping("/listCustomerIdByKeyWord/{keyword}")
    BaseResponse<List<Integer>> listCustomerIdByKeyWord(@PathVariable("keyword") String keyword);

    /**
     * 获取装卸货地显示对象
     *
     * @param id
     * @return
     */
    @GetMapping("/getShowSite/{id}")
    ShowSiteDto getShowSiteById(@PathVariable("id") Integer id);

    /**
     * 查询全部正常合作的客户
     *
     * @author yj
     * @return
     * @param tenantId
     *
     */
    @GetMapping("/listNormalCustomerByTenantId/{tenantId}")
    List<ShowCustomerDto> listNormalCustomerByTenantId(@PathVariable("tenantId") Integer tenantId);
}
