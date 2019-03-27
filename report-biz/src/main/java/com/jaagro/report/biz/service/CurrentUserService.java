package com.jaagro.report.biz.service;

import com.jaagro.constant.UserInfo;
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

    public UserInfo getCurrentUser() {
        String token = request.getHeader("token");
        return tokenClient.getUserByToken(token);
    }
}