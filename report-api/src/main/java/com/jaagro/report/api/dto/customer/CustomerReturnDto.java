package com.jaagro.report.api.dto.customer;

import com.jaagro.report.api.dto.contract.ReturnContractDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 返回的客户
 *
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class CustomerReturnDto implements Serializable {
    /**
     * 客户主键id
     */
    private Integer id;

    /**
     *
     */
    private Integer tenantId;

    /**
     * 客户名称(个体客户时，就是自然人姓名)
     */
    private String customerName;

    /**
     * 客户类型
     * (1:个体客户 2:企业客户 )
     */
    private Integer customerType;

    /**
     * 客户类别(1:物流业务 2:养殖业务 3:运力 4: 物资生产企业)
     */
    private Integer customerCategory;

    /**
     * 统一社会验证码(个体客户时，就是自然人身份证号码)
     */
    private String creditCode;

    /**
     * 审核状态
     * (0未审核，1-正常合作  10-停止合作 11-审核未通过 13-作废)
     */
    private Integer customerStatus;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 所属区县
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 账期天数
     */
    private Integer billingPeriod;

    /**
     * 是否开票
     * 0:否 1:是
     */
    private Boolean enableInvoice;

    /**
     * 发票类型
     * 1:增值税普通发票 2:增值税专用发票
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceHeader;

    /**
     * 税务编号
     */
    private String taxNumber;

    /**
     * 备注信息(用车要求)
     */
    private String notes;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 修改日期
     */
    private Date modifyTime;

    /**
     * 创建人(References: user)
     */
    private Integer createUserId;

    /**
     * 修改人(References: user)
     */
    private Integer modifyUserId;
    /**
     * 客户是否直接下单
     */
    private String enableDirectOrder;

    /**
     * 客户联系人
     */
    private List<CustomerContactsReturnDto> customerContactsReturnDtos;

    /**
     * 查询客户合同
     */
    private List<ReturnContractDto> returnContractDtos;

    /**
     * 查询客户收发货地
     */
    private List<CustomerSiteReturnDto> sites;

    /**
     * 查询客户资质证件照
     */
    private List<CustomerQualificationReturnDto> qualifications;

}
