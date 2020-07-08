package com.cloudera.poverty.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AuthVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限id")
    @TableId(value = "authority_id", type = IdType.ASSIGN_ID)
    private String authorityId;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限父id")
    private String authorityFatherId;

    @ApiModelProperty(value = "是否可用")
    private String isAble;

    @ApiModelProperty(value = "是否可删除")
    private String isDel;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "权限编号")
    private String code;

    @ApiModelProperty(value = "权限路径")
    private String url;

    @ApiModelProperty(value = "外接路径")
    private String iframe;

    private List<AuthVo> subMenus=new ArrayList<>();
}