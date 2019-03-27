package com.jaagro.report.biz.service.impl;

import com.jaagro.constant.UserInfo;
import com.jaagro.report.api.dto.finance.CustomerBaseInfoDto;
import com.jaagro.report.api.service.FinanceService;
import com.jaagro.report.biz.service.CurrentUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对接金融接口
 * @author yj
 * @date 2019/3/27 11:05
 */
@Service
@Slf4j
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private CurrentUserService currentUserService;
    /**
     * 获取客户基本信息
     *
     * @return
     */
    @Override
    public CustomerBaseInfoDto getCustomerBaseInfo() {
        UserInfo currentUser = currentUserService.getCurrentUser();
        Integer currentUserId = currentUser == null ? null : currentUser.getId();
        log.info("O CustomerBaseInfoDto currentUserId={}",currentUserId);

        return null;
    }
}
