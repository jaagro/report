package com.jaagro.report.api.dto.plant;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 * @date 2019/3/27 17:13
 */
@Data
@Accessors(chain = true)
public class PlantImageDto implements Serializable{
    /**
     * 客户养殖场图片表id
     */
    private Integer id;

    /**
     * 养殖场id
     */
    private Integer plantId;

    /**
     * 图片地址
     */
    private String imageUrl;

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
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 更新人
     */
    private Integer modifyUserId;
}
