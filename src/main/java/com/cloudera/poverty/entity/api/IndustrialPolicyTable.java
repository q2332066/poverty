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
@ApiModel(value="IndustrialPolicyTable对象", description="")
public class IndustrialPolicyTable implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "i_id", type = IdType.ASSIGN_ID)
    private String iId;

    @ApiModelProperty(value = "带贫主体名称")
    private String businessEntity;

    @ApiModelProperty(value = "带动方式")
    private String drivingMethod;

    @ApiModelProperty(value = "带动增收")
    private Double driveIncome;

    @ApiModelProperty(value = "产业奖补")
    private Double ndustryAwards;

    @ApiModelProperty(value = "产业发展政策性补助")
    private Double policySubsidies;

    @ApiModelProperty(value = "农业技能培训时间", example = "2020-01-01")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date agriculturalTraining;

    @ApiModelProperty(value = "农业技能培训内容")
    private String agriculturalContent;

    @ApiModelProperty(value = "人员标识")
    private String personnelInformationId;

}
