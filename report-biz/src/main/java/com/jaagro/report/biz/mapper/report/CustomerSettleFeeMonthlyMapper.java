package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.entity.CustomerSettleFeeMonthly;
import com.jaagro.report.api.entity.CustomerSettleFeeMonthlyExample;
import java.util.List;

public interface CustomerSettleFeeMonthlyMapper {
    /**
     *
     * @mbggenerated 2019-04-16
     */
    int countByExample(CustomerSettleFeeMonthlyExample example);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int insert(CustomerSettleFeeMonthly record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int insertSelective(CustomerSettleFeeMonthly record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    List<CustomerSettleFeeMonthly> selectByExample(CustomerSettleFeeMonthlyExample example);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    CustomerSettleFeeMonthly selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int updateByPrimaryKeySelective(CustomerSettleFeeMonthly record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int updateByPrimaryKey(CustomerSettleFeeMonthly record);
}