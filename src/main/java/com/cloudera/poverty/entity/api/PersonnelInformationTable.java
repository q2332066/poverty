package com.cloudera.poverty.entity.api;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PersonnelInformationTable对象", description="")
public class PersonnelInformationTable implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "p_id", type = IdType.ASSIGN_ID)
    private String pId;

    @ApiModelProperty(value = "户主")
    private String host;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "与户主关系")
    private String relationship;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "家庭人口")
    private Integer population;

    @ApiModelProperty(value = "劳动力人口")
    private Integer laborNumber;

    @ApiModelProperty(value = "迁出地")
    private String movedOut;

    @ApiModelProperty(value = "迁入地")
    private String moveIn;

    @ApiModelProperty(value = "安置方式")
    private String arrangement;

    @ApiModelProperty(value = "安置点标识")
    private String resettlementPointId;

    @ApiModelProperty(value = "文化程度")
    private String education;

    @ApiModelProperty(value = "健康状况")
    private String stateOfHealths;

    @ApiModelProperty(value = "劳动技能")
    private String laborSkills;

    @ApiModelProperty(value = "致贫原因")
    private String causePoverty;

    @ApiModelProperty(value = "人均纯收入")
    private Double averageIncome;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty("是否为户主")
    private String torfHost;

    @ApiModelProperty("户主身份证号")
    private String hostId;

    @ApiModelProperty("是否是劳动力")
    private String torfLabor;

    @ApiModelProperty("是否脱贫")
    private String torfPoor;

    @ApiModelProperty("脱贫时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date poorTime;

    @ApiModelProperty("房屋面积")
    private Double houseArea;

    @ApiModelProperty("是否入住")
    private String torfLive;

    @ApiModelProperty("目前居住地址")
    private String specificAddr;

    @ApiModelProperty("是否拆旧复垦")
    private String torfDismantle;


}
