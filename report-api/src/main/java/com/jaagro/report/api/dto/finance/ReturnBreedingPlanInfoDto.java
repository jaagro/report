package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 贷款-选择协议 出参
 * （养殖计划信息列表）
 * @author: @Gao.
 * @create: 2019-03-28 10:05
 **/
@Data
@Accessors(chain = true)
public class ReturnBreedingPlanInfoDto implements Serializable {
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
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;
    /**
     * 鸡单位：只
     */
    private String chickenUnit;


    private String breedingType;

    /**
     * 批次金额
     */
    private BigDecimal batchAmount;

    /**
     * 签订日期
     */
    private Date contractDate;

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

    /**
     * 养殖场相关信息
     */
    private List<ReturnPlantDto> returnPlantDtos;

    /**
     * 采购单列表
     */
    List<ReturnPurchaseOrderDto> returnPurchaseOrderDtos;
}
