package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.ListCustomerReportCriteriaDto;
import com.jaagro.report.api.entity.CustomerOrderDaily;
import com.jaagro.report.api.entity.CustomerOrderMonthly;

import java.util.List;


/**
 * @author baiyiran
 */
public interface DataBigScreenService {
    /**
     * 客户贡献前十
     */
    void listTopTemCustomerData();


}
