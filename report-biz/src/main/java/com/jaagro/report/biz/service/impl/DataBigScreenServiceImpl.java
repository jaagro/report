package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.ListCustomerReportCriteriaDto;
import com.jaagro.report.api.dto.ShowCustomerDto;
import com.jaagro.report.api.entity.CustomerOrderDaily;
import com.jaagro.report.api.entity.CustomerOrderMonthly;
import com.jaagro.report.api.service.CustomerReportTaskService;
import com.jaagro.report.api.service.DataBigScreenService;
import com.jaagro.report.biz.mapper.report.CustomerOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.CustomerOrderMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.CustomerReportMapperExt;
import com.jaagro.report.biz.service.CustomerClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author baiyiran
 */
@Service
@Slf4j
public class DataBigScreenServiceImpl implements DataBigScreenService {

    @Autowired
    private CustomerReportMapperExt customerReportMapperExt;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private CustomerOrderDailyMapperExt dailyMapperExt;
    @Autowired
    private CustomerOrderMonthlyMapperExt monthlyMapperExt;


    /**
     * 客户贡献前十
     */
    @Override
    public void listTopTemCustomerData() {



    }
}