package com.cloudera.poverty.entity.api;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="CareerPolicyTable对象", description="")
public class CareerPolicyTable implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "ca_id", type = IdType.ASSIGN_ID)
    private String caId;

    @ApiModelProperty(value = "省外务工转移")
    private String outsideProvince;

    @ApiModelProperty(value = "省内县外务工转移")
    private String withinCounty;

    @ApiModelProperty(value = "务工县内转移")
    private String outsideCounty;

    @ApiModelProperty(value = "社区工厂吸纳及就业扶贫基地带动")
    private String communityDriven;

    @ApiModelProperty(value = "公益岗位")
    private String publicWelfare;

    @ApiModelProperty(value = "自主创业")
    private String entrepreneurship;

    @ApiModelProperty(value = "农业就业")
    private String agriculture;

    @ApiModelProperty(value = "预计收入")
    private Double estimatedIncome;

    @ApiModelProperty(value = "未就业原因")
    private String reason;

    @ApiModelProperty(value = "就业技能培训时间")
    private Date employmentTrainingTime;

    @ApiModelProperty(value = "就业技能培训内容")
    private String employmentTrainingContent;

    @ApiModelProperty(value = "职业技能培训时间")
    private Date vocationalTrainingTime;

    @ApiModelProperty(value = "职业技能培训内容")
    private String vocationalTrainingContent;

    @ApiModelProperty(value = "创业培训时间")
    private Date entrepreneurshipTrainingTime;

    @ApiModelProperty(value = "创业培训内容")
    private String entrepreneurshipTrainingContent;

    @ApiModelProperty(value = "人员标识")
    private String personnelInformationId;

    @ApiModelProperty("年增收")
    private Double yearIncrease;

    @ApiModelProperty(value = "自主发展类型及规模")
    private String development;


}
