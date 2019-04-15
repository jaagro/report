package com.jaagro.report.biz.mapper.cbs;

import org.apache.ibatis.annotations.Param;

/**
 * @author yj
 * @date 2019/4/15 14:44
 */
public interface BatchInfoMapperExt {
    /**
     * 根据批次号日龄查询
     * @param batchNo
     * @param dayAge
     * @return
     */
    Integer findByBatchNoAndDayAge(@Param("batchNo") String batchNo,@Param("dayAge") Integer dayAge);
}
