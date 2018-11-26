package com.jaagro.report.web.controller;

import com.jaagro.report.biz.entity.DriverOrderDaily;
import com.jaagro.report.biz.entity.UserLogin;
import com.jaagro.report.biz.mapper.report.DriverOrderDailyMapper;
import com.jaagro.report.biz.mapper.report.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tony
 */
@RestController
public class Test {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @GetMapping("/test")
    public void test(){
        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(1);
        System.out.println(userLogin);
    }
}
