package com.jaagro.report.biz.service;

import com.jaagro.constant.UserInfo;
import com.jaagro.report.api.dto.customer.GetCustomerUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tony
 */
@Service
public class CurrentUserService {
    @Autowired
    private AuthClientService tokenClient;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CustomerUserClientService customerUserClientService;
    @Autowired
    private CustomerClientService customerClientService;

    public UserInfo getCurrentUser() {
        String token = request.getHeader("token");
        return tokenClient.getUserByToken(token);
    }

    public GetCustomerUserDto getCustomerUserById() {
        return customerUserClientService.getCustomerUserById(getCurrentUser().getId());
    }
}