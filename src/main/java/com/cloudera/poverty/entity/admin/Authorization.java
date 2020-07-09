package com.cloudera.poverty.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("authorization")
public class Authorization {

    @TableId(type = IdType.NONE)
    private String id;
    private String code;
    private String name;
    private Integer status; /** 0为普通菜单，1为跟菜单，2为权限项 */
    private Integer level;
    @TableField(exist = false)
    private String parentName;
    private Integer sortOrder;
    private String path;
    private Boolean isLeaf;
    private String parent;
    private String description;
    private String url;
    private String authurl;
    private String icon;
    private String iframe;
    @TableField(exist = false)
    private boolean menu = false;
    @TableField(exist = false)
    private List<Authorization> children = new ArrayList<>();
}
