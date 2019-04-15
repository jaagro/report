package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 养殖详情
 * @author yj
 * @date 2019/3/29 18:04
 */
@Data
@Accessors(chain = true)
public class BatchDetailDto implements Serializable{
    private static final long serialVersionUID = -7075741664076370334L;
    /**
     * 客户id
     */
    private Integer customerId;
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
     * 上鸡数量
     */
    private Integer planChickenQuantity;
    /**
     * 上鸡时间
     */
    private Date planTime;
    /**
     * 出栏时间
     */
    private Date expectSuchTime;
    /**
     * 养殖参数列表
     */
    private List<BreedingParamDto> breedingParamDtoList;
    /**
     * 喂药参数列表
     */
    private List<BreedingBatchDrugDto> breedingBatchDrugDtoList;
    /**
     * 操作员编码
     */
    private String operatorCode;
    /**
     * 操作员姓名
     */
    private String operatorName;

}
