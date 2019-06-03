package com.example.server.manager.dao.mysql.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 首页轮播广告大图
 */
@Data
public class Pchomead {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 链接地址
     */
    private String linkUrl;
    /**
     * 描述
     */
    private String remark;
    /**
     * 状态,0-正常,1-已下线
     */
    private Integer status;
    /**
     * 是否删除,0-正常-false,1-删除-true
     */
    private Boolean isDelete;
    /**
     * 开始时间, NULL表示永久
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtStart;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtEnd;
    /**
     * 最后操作人(ERP账号)
     */
    private String operator;
    /**
     * 主动创建时间
     */
    private Date gmtCreate;
    /**
     * 被动更新时间
     */
    private Date gmtModified;
}
