package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 养殖批次详情
 * @author: @Gao.
 * @create: 2019-03-28 15:21
 **/
@Data
@Accessors(chain = true)
public class ReturnBreedingPlanDetailsDto implements Serializable {
    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 客户类型
     */
    private String customerType;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 养殖类型
     */
    private String breedingType;

    /**
     * 商品类型
     */
    private String productType;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;
    
    /**
     * 签订日期
     */
    private Date contractDate;

    /**
     * 商品采购单编号
     */
    private String purchaseNo;

    /**
     * 采购时间
     */
    private Date purchaseTime;

    /**
     * 购买数量
     */
    private BigDecimal quantity;

    /**
     * 采购订单总金额
     */
    private BigDecimal totalPrice;

    /**
     * 规格
     */
    private String specification;

    /**
     * 操作员编码
     */
    private String operatorCode;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 来源
     */
    private String source;

}
