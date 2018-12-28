package com.jaagro.report.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 * @since 20181126
 */
@Data
@Accessors(chain = true)
public class UserLogin implements Serializable{
    private static final long serialVersionUID = 5815916736048952595L;
    /**
     * 
     */
    private Integer id;

    /**
     * 登录日期
     */
    private Date loginDate;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 最新一次登录时间
     */
    private Date newLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;
}