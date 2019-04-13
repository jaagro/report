package com.jaagro.report.api.dto.plant;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 养殖场信息
 * @author yj
 * @date 2019/3/27 17:10
 */
@Data
@Accessors(chain = true)
public class PlantDto implements Serializable {
    private static final long serialVersionUID = 6457172717348135634L;
    /**
     * 养殖场id
     */
    private Integer id;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 养殖户id
     */
    private Integer customerId;

    /**
     * 养殖场名称
     */
    private String plantName;

    /**
     * 养殖场类型(1-平养,2-笼养,3-网养)
     */
    private Integer plantType;

    /**
     * 产权情况
     */
    private String equityType;

    /**
     * 使用年限
     */
    private Integer durableYears;

    /**
     * 是否可以扩建(0-不可扩建,1-可扩建)
     */
    private Boolean expandable;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String county;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

    /**
     * 养殖场图片
     */
    private List<PlantImageDto> plantImageDtoList;
}
