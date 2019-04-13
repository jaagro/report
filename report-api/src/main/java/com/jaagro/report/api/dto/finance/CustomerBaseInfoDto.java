package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 客户基本信息
 * @author yj
 * @date 2019/3/27 13:52
 */
@Data
@Accessors(chain = true)
public class CustomerBaseInfoDto implements Serializable {
    private static final long serialVersionUID = -5705135548820270243L;
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
     * 头像
     */
    private String headPortrait;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 身份证正面照
     */
    private String idCardImgPositive;
    /**
     * 身份证被米娜照
     */
    private String idCardImgNegative;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 养殖场详细地址
     */
    private String plantDetailAddress;
    /**
     * 养殖场图片
     */
    private String plantImageUrl;
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