package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 返回客户基本信息
 * @author: @Gao.
 * @create: 2019-03-29 16:58
 **/
@Data
@Accessors(chain = true)
public class ReturnCustomerInfoDto implements Serializable {

    private Integer id;

    /**
     * 客户名称(个体客户时，就是自然人姓名)
     */
    private String customerName;

    /**
     * 商品采购单编号
     */
    private List<String> purchaseNos;

    /**
     * 批次号
     */
    private String batchNo;
}
