package com.cloudera.poverty.entity.api;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@ApiModel(value="EnjoyHelpPolicyTable对象", description="")
public class EnjoyHelpPolicyTable implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "e_id", type = IdType.ASSIGN_ID)
    private String eId;

    @ApiModelProperty(value = "易地搬迁补助")
    private Double relocationAllowance;

    @ApiModelProperty(value = "教育保障就读学校")
    private String educationGuaranteeSchool;

    @ApiModelProperty(value = "教育扶贫补助类型")
    private String typeSubsidy;

    @ApiModelProperty(value = "教育扶贫保障补助金额")
    private Double educationGrantAmount;

    @ApiModelProperty(value = "健康扶贫保障新农合减免")
    private Double ncmsReduction;

    @ApiModelProperty(value = "健康扶贫保障合疗报销")
    private Double reimbursementCombinedTherapy;

    @ApiModelProperty(value = "生态扶贫保障公益林补偿")
    private Double linCompensation;

    @ApiModelProperty(value = "生态扶贫保障护林员")
    private Double rangerCompensation;

    @ApiModelProperty(value = "金融扶贫保障及互助资金协会借款金额")
    private Double loanAmount;

    @ApiModelProperty(value = "金融扶贫保障发展产业类型及规模")
    private String industryTypeScale;

    @ApiModelProperty(value = "社会保障养老保险年收入")
    private Double pensionIncome;

    @ApiModelProperty(value = "社会保障高龄补贴年收入")
    private Double oldAgeAllowance;

    @ApiModelProperty(value = "社会保障残疾补助年收入")
    private Double disabilityAllowance;

    @ApiModelProperty(value = "社会保障兜底保障类型")
    private String pocketType;

    @ApiModelProperty(value = "社会保障兜底保障金额")
    private Double pocketAmount;

    @ApiModelProperty(value = "人员标识")
    private String personnelInformationId;


}
