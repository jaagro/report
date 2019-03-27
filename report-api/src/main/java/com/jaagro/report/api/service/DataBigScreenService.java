package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.ContributionTopTenCustomerDto;

import java.util.List;


/**
 * @author baiyiran
 */
public interface DataBigScreenService {

    /**
     * 客户贡献前十
     */
    List<ContributionTopTenCustomerDto> listTopTenCustomerData();


}
