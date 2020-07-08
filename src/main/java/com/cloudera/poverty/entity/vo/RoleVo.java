package com.cloudera.poverty.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class RoleVo implements Serializable, AuthCachePrincipal {

    @ApiModelProperty(value = "角色id")
    @TableId(value = "r_id", type = IdType.ASSIGN_ID)
    private String rId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "是否可用")
    private String isAble;

    @ApiModelProperty(value = "是否可删除")
    private String isDel;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "权限地域")
    private String raId;

    private List<AuthorityTable> auList=new ArrayList<>();

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
