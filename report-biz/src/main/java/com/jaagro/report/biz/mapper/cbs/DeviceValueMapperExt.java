package com.jaagro.report.biz.mapper.cbs;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设备检测值查询
 * @author yj
 * @date 2019/4/1 21:34
 */
public interface DeviceValueMapperExt {
    /**
     * 根据批次号和值类型查询
     * @param batchNo
     * @param deviceType
     * @return
     */
    List<BigDecimal> listByBatchNoAndValueType(@Param("batchNo") String batchNo,@Param("deviceType") Integer deviceType);
}