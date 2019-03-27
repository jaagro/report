package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.finance.CustomerBaseInfoDto;

/**
 * 对接金融接口服务
 * @author yj
 * @date 2019/3/27 11:05
 */
public interface FinanceService {
    /**
     * 获取客户基本信息
     * @return
     */
    CustomerBaseInfoDto getCustomerBaseInfo();
}
