package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.entity.DriverSettleFeeMonthly;
import com.jaagro.report.api.entity.DriverSettleFeeMonthlyExample;
import java.util.List;

public interface DriverSettleFeeMonthlyMapper {
    /**
     *
     * @mbggenerated 2019-04-16
     */
    int countByExample(DriverSettleFeeMonthlyExample example);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int insert(DriverSettleFeeMonthly record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int insertSelective(DriverSettleFeeMonthly record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    List<DriverSettleFeeMonthly> selectByExample(DriverSettleFeeMonthlyExample example);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    DriverSettleFeeMonthly selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int updateByPrimaryKeySelective(DriverSettleFeeMonthly record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int updateByPrimaryKey(DriverSettleFeeMonthly record);
}