package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.ContributionTopTenCustomerDto;
import com.jaagro.report.api.service.DataBigScreenService;
import com.jaagro.report.biz.mapper.report.CustomerOrderDailyMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baiyiran
 */
@Service
@Slf4j
public class DataBigScreenServiceImpl implements DataBigScreenService {

    @Autowired
    private CustomerOrderDailyMapperExt customerOrderDailyMapperExt;


    /**
     * 客户贡献前十
     */
    @Override
    public List<ContributionTopTenCustomerDto> listTopTenCustomerData() {

        return customerOrderDailyMapperExt.listTopTenCustomerData();
    }
}