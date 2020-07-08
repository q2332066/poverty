package com.cloudera.poverty.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.cloudera.poverty.entity.admin.RoleTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserTable对象", description="")
public class UserTableVo {

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
    private String resName;
    private List<RoleTable> roleTable=new ArrayList<>();
    private List<String> roleId=new ArrayList<>();

    @TableId(value = "ci_id", type = IdType.ASSIGN_ID)
    private String ciId;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @TableId(value = "d_id", type = IdType.ASSIGN_ID)
    private String dId;

    @ApiModelProperty(value = "区县")
    private String district;

    @TableId(value = "t_id", type = IdType.ASSIGN_ID)
    private String tId;

    @ApiModelProperty(value = "乡镇名称")
    private String township;

    @ApiModelProperty(value = "安置点id")
    @TableId(value = "r_id", type = IdType.ASSIGN_ID)
    private String rId;

    @ApiModelProperty(value = "安置点名称")
    private String resettlementPoint;

}
