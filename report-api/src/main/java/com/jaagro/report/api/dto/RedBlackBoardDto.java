package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gavin
 * @Date 2019/3/27
 */
@Accessors(chain = true)
@Data
public class RedBlackBoardDto implements Serializable {

    /**
     * 司机姓名
     */
    private String driver;

    /**
     * 所属大区名称
     */
    public String department;

    /**
     * 所属项目部名称
     */
    public String network;


    /**
     * 派单时间
     */
    public Date sendTime;
    /**
     * 接单时间
     */
    public Date receiveTime;

    /**
     * 接单时长
     */
    public  int receiveDate;

}
