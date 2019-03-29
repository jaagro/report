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
public class CreateLoanApplyRecordDto implements Serializable {
    /**
     * 商品采购单编号
     */
    private String purchaseNo;

}
