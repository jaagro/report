package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 司机结算费用查询条件
 * @author: @Gao.
 * @create: 2019-04-17 14:00
 **/
@Data
@Accessors(chain = true)
public class ListDriverFeeCriteria implements Serializable {

    /**
     * 起始页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 司机名称
     */
    private String driverName;

    /**
     * 车牌号
     */
    private String truckNumber;

    /**
     * 月份
     */
    private String month;
}
