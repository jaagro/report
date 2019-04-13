package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 喂药记录
 * @author yj
 * @date 2019/4/2 11:42
 */
@Data
@Accessors(chain = true)
public class FeedMedicineDto implements Serializable {
    private static final long serialVersionUID = -339170992886293196L;
    /**
     * 日龄
     */
    private Integer dayAge;
    /**
     * 饲喂量
     */
    private BigDecimal breedingValue;
}
