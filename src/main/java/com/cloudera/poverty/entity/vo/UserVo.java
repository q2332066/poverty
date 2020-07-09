package com.cloudera.poverty.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserVo implements Serializable, AuthCachePrincipal {
    @ApiModelProperty(value = "用户id")
    @TableId(value = "u_id", type = IdType.ASSIGN_ID)
    private String uId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @TableField(fill =  FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "显示名称")
    private String showName;

    @ApiModelProperty(value = "地域ID")
    private String regionalId;

    @ApiModelProperty(value = "账号级别")
    private String level;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDeleted;

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
