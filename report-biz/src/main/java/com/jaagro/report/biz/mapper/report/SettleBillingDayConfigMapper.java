package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.entity.SettleBillingDayConfig;
import com.jaagro.report.api.entity.SettleBillingDayConfigExample;
import java.util.List;

public interface SettleBillingDayConfigMapper {
    /**
     *
     * @mbggenerated 2019-04-16
     */
    int countByExample(SettleBillingDayConfigExample example);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int insert(SettleBillingDayConfig record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int insertSelective(SettleBillingDayConfig record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    List<SettleBillingDayConfig> selectByExample(SettleBillingDayConfigExample example);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    SettleBillingDayConfig selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int updateByPrimaryKeySelective(SettleBillingDayConfig record);

    /**
     *
     * @mbggenerated 2019-04-16
     */
    int updateByPrimaryKey(SettleBillingDayConfig record);
}