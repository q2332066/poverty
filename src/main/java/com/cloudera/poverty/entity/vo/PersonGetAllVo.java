package com.cloudera.poverty.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PersonGetAllVo {

    @TableId(value = "p_id")
    private String pId;

    @ApiModelProperty(value = "户主")
    private String host;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "与户主关系")
    private String relationship;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

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

    @TableId(value = "i_id")
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

    @TableId(value = "ca_id")
    private String caId;

    @ApiModelProperty(value = "省外务工转移")
    private String outsideProvince;

    @ApiModelProperty(value = "省外县内务工转移")
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

    @ApiModelProperty("年增收")
    private Double yearIncrease;

    @ApiModelProperty(value = "自主发展类型及规模")
    private String development;

    @TableId(value = "e_id")
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

    @ApiModelProperty(value = "安置点id")
    @TableId(value = "r_id", type = IdType.ASSIGN_ID)
    private String rId;

    @ApiModelProperty(value = "安置点名称")
    private String resettlementPoint;

    @ApiModelProperty(value = "安置点经度")
    private String longitude;

    @ApiModelProperty(value = "安置点纬度")
    private String latitude;

    @ApiModelProperty(value = "乡镇标识")
    private String townshipId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "学校个数")
    private Integer schoolNumber;

    @ApiModelProperty(value = "幼儿园名称")
    private String kindergartenName;

    @ApiModelProperty(value = "幼儿园数量")
    private Integer kindergartenNumber;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医护人数")
    private Integer hospitalNumber;

    @ApiModelProperty(value = "图书馆名称")
    private String libraryName;

    @ApiModelProperty(value = "图书馆数量")
    private Integer libraryNumber;

    @ApiModelProperty(value = "安置人数")
    private Integer placeNumP;

    @ApiModelProperty(value = "带贫主体")
    private String driveSun;

    @ApiModelProperty(value = "安置户数")
    private Integer placeNumH;

    @ApiModelProperty(value = "带动户数")
    private Integer driveNum;

    private String tId;

    @ApiModelProperty(value = "乡镇名称")
    private String township;

    @ApiModelProperty(value = "区县标识")
    private String districtId;

    private String dId;

    @ApiModelProperty(value = "区县")
    private String district;

    @ApiModelProperty(value = "市标识")
    private String cityId;

    private String ciId;

    @ApiModelProperty(value = "市名称")
    private String cityName;


}
