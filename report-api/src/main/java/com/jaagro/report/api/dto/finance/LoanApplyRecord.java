package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 贷款记录
 * @author: @Gao.
 * @create: 2019-03-29 16:16
 **/
@Data
@Accessors(chain = true)
public class LoanApplyRecord implements Serializable {

    /**
     * 贷款申请记录表id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 计划id
     */
    private Integer planId;

    /**
     * 采购订单id
     */
    private Integer purchaseOrderId;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

}
