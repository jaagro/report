package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 批次喂药参数
 * @author yj
 * @date 2019/3/30 14:53
 */
@Data
@Accessors(chain = true)
public class BreedingBatchDrugDto implements Serializable{
    private static final long serialVersionUID = 4133724530833661035L;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 药品名称
     */
    private String productName;
    /**
     * 容量单位(1-ml,2-g)
     */
    private Integer capacityUnit;
    /**
     * 日龄起(包含)
     */
    private Integer dayAgeStart;

    /**
     * 日龄止(包含)
     */
    private Integer dayAgeEnd;

    /**
     * 停药标识(0-否,1-是)
     */
    private Boolean stopDrugFlag;

    /**
     * 千只日喂量
     */
    private BigDecimal feedVolume;

    /**
     * 实际喂料量
     */
    private List<FeedMedicineDto> actualValueList;
}
