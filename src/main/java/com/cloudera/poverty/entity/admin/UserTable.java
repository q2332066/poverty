package com.cloudera.poverty.entity.admin;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ct
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserTable对象", description="")
public class UserTable implements Serializable {

    private static final long serialVersionUID=1L;

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

    @ApiModelProperty(value = "市ID")
    private String regionalId;

    @ApiModelProperty(value = "区ID")
    private String did;

    @ApiModelProperty(value = "镇ID")
    private String tid;

    @ApiModelProperty(value = "安置点ID")
    private String rid;

    @ApiModelProperty(value = "账号级别")
    private String level;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDeleted;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String orgPwd;
}
