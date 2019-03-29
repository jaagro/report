package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.finance.LoanApplyRecord;

import javax.annotation.Resource;


/**
 * LoanApplyRecordMapperExt接口
 *
 * @author :generator
 * @date :2019/3/29
 */
@Resource
public interface LoanApplyRecordMapperExt {
    /**
     * 保存贷款记录
     *
     * @param record
     */
    void insertSelective(LoanApplyRecord record);
}