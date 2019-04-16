package com.jaagro.report.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 */
@Data
@Accessors(chain = true)
public class SettleBillingDayConfig implements Serializable{
    private static final long serialVersionUID = 7613880366841049082L;
    /**
     * 费用报表账单配置表id
     */
    private Integer id;

    /**
     * 类型(1-客户,2-司机)
     */
    private Integer type;

    /**
     * 账单日
     */
    private String billingDay;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

}