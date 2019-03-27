package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author gavin
 * @Date 2019/3/27
 */
@Accessors(chain = true)
@Data
public class ContributionTopTenCustomerDto implements Serializable {

    /**
     * 客户名称
     */
    private String content;

    /**
     * 运量总和
     */
    public Integer value;

}
