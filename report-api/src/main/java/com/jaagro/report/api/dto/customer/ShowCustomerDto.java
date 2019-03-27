package com.jaagro.report.api.dto.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
@Accessors(chain = true)
public class ShowCustomerDto implements Serializable {

    private static final long serialVersionUID = 4109223964021226162L;
    private Integer id;

    /**
     * 客户名称(个体客户时，就是自然人姓名)
     */
    private String customerName;


    /**
     * 客户是否直接下单
     */
    private String enableDirectOrder;
}
