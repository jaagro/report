package com.jaagro.report.api.dto.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 客户合同信息
 * @author: @Gao.
 * @create: 2019-03-29 09:34
 **/
@Data
@Accessors(chain = true)
public class CustomerContacts implements Serializable {

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态(0 停用 1 启用)
     */
    private Boolean enabled;
}
