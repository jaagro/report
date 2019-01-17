package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 * @Date 2019/1/17
 */
@Accessors(chain = true)
@Data
public class ReturnDataScreenDto implements Serializable {

    private String name = "";

    public Integer value = 50717;

}
