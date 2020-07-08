package com.cloudera.poverty.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQueryVo {
    private Long page;
    private Long limit;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String password;
    @ApiModelProperty(value = "显示名称")
    private String showName;
    @ApiModelProperty(value = "地域ID")
    private String regionalId;

    private String cityId;
    private String disId;
    private String towId;
    private String resId;

}
