package com.cloudera.poverty.entity.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

@Data
public class PersonAllVo {


    private String pid;

    @ExcelProperty(value = "区县",index = 1)
    private String district;

    @ExcelProperty(value = "乡镇",index = 2)
    private String township;

    @ExcelProperty(value = "户主",index = 3)
    private String host;

    @ExcelProperty(value = "搬迁人员姓名",index = 4)
    private String name;

    @ExcelProperty(value = "搬迁人员身份证号",index = 5)
    private String idCard;

    @ExcelProperty(value = "是否为户主(是/否)",index = 6)
    private String torfHost;

    @ExcelProperty(value = "户主身份证号",index = 7)
    private String hostId;

    @ExcelProperty(value = "与户主关系",index = 8)
    private String relationship;
    @ExcelProperty(value = "家庭人口",index = 9)
    private Integer population;
    @ExcelProperty(value = "是否劳动力(是/否)",index = 10)
    private String torfLabor;
    @ExcelProperty(value = "劳动力人口",index = 11)
    private Integer laborNumber;

    @ExcelProperty(value = "迁出地",index = 12)
    private String movedOut;

    @ExcelProperty(value = "迁入地",index = 13)
    private String moveIn;

    @ExcelProperty(value = "安置方式",index = 14)
    private String arrangement;

    @ExcelProperty(value = "安置点名称",index = 15)
    private String resettlementPoint;

    @ExcelProperty(value = "是否脱贫",index = 16)
    private String torfPoor;
    @ExcelProperty(value = "文化程度",index = 17)
    private String education;

    @ExcelProperty(value = "健康状况",index = 18)
    private String stateOfHealths;

    @ExcelProperty(value = "劳动技能",index = 19)
    private String laborSkills;

    @ExcelProperty(value = "致贫原因",index = 20)
    private String causePoverty;

    @ExcelProperty(value = "人均纯收入",index = 21)
    private Double averageIncome;

    @ExcelProperty(value = "联系方式",index = 22)
    private String phone;

    @ExcelProperty(value = "备注(新增或者死亡人员)",index = 23)
    private String remarks;

    @ExcelProperty(value = "房屋面积",index = 25)
    private Double houseArea;
    @ExcelProperty(value = "脱贫时间",index = 27)
    @DateTimeFormat("yyyy/MM/dd")
    private String poorTime;


    @ExcelProperty(value = "是否实际入住",index = 28)
    private String torfLive;

    @ExcelProperty(value = "目前具体地址",index = 29)
    private String specificAddr;

    @ExcelProperty(value = "是否拆旧复垦",index = 30)
    private String torfDismantle;

    @ExcelProperty(value = "务工省外转移",index = 31)
    private String outsideProvince;

    @ExcelProperty(value = "务工县外省内转移",index = 32)
    private String withinCounty;

    @ExcelProperty(value = "务工县内转移",index = 33)
    private String outsideCounty;

    @ExcelProperty(value = "社区工厂吸纳及就业扶贫基地带动",index = 34)
    private String communityDriven;

    @ExcelProperty(value = "公益岗位",index = 35)
    private String publicWelfare;

    @ExcelProperty(value = "自主创业",index = 36)
    private String entrepreneurship;

    @ExcelProperty(value = "农业就业",index = 37)
    private String agriculture;

    @ExcelProperty(value = "预计年收入",index = 38)
    private Double estimatedIncome;

    @ExcelProperty(value = "未就业原因",index = 39)
    private String reason;

    @ExcelProperty(value = "就业培训时间",index = 40)
    @DateTimeFormat("yyyy/MM/dd")
    private String employmentTrainingTime;

    @ExcelProperty(value = "就业培训内容",index = 41)
    private String employmentTrainingContent;

    @ExcelProperty(value = "职业培训时间",index = 42)
    @DateTimeFormat("yyyy/MM/dd")
    private String vocationalTrainingTime;

    @ExcelProperty(value = "职业培训内容",index = 43)
    private String vocationalTrainingContent;

    @ExcelProperty(value = "创业培训时间",index = 44)
    @DateTimeFormat("yyyy/MM/dd")
    private String entrepreneurshipTrainingTime;

    @ExcelProperty(value = "创业培训内容",index = 45)
    private String entrepreneurshipTrainingContent;
    @ExcelProperty(value = "自主发展类型及规模",index = 46)
    private String development;
    @ExcelProperty(value = "自主发展预计年增收",index = 47)
    private Double yearIncrease;

    @ExcelProperty(value = "经营主体带贫名称",index = 48)
    private String businessEntity;

    @ExcelProperty(value = "经营主体带贫带动方式",index = 49)
    private String drivingMethod;

    @ExcelProperty(value = "经营主体带贫带动增收",index = 50)
    private Double driveIncome;

    @ExcelProperty(value = "产业发展奖补",index = 51)
    private Double ndustryAwards;

    @ExcelProperty(value = "产业发展政策性补助",index = 52)
    private Double policySubsidies;

    @ExcelProperty(value = "农业实用技术培训时间",index = 53)
    @DateTimeFormat("yyyy/MM/dd")
    private String agriculturalTraining;

    @ExcelProperty(value = "农业实用技术培训内容",index = 54)
    private String agriculturalContent;
    @ExcelProperty(value = "易地搬迁补助",index = 55)
    private Double relocationAllowance;

    @ExcelProperty(value = "教育扶贫保障就读学校",index = 56)
    private String educationGuaranteeSchool;

    @ExcelProperty(value = "教育扶贫保障补助类型",index = 57)
    private String typeSubsidy;

    @ExcelProperty(value = "教育扶贫保障补助金额",index = 58)
    private Double educationGrantAmount;

    @ExcelProperty(value = "健康扶贫保障新农合减免",index = 59)
    private Double ncmsReduction;

    @ExcelProperty(value = "健康扶贫保障合疗报销",index = 60)
    private Double reimbursementCombinedTherapy;

    @ExcelProperty(value = "生态扶贫保障公益林补偿",index = 61)
    private Double linCompensation;

    @ExcelProperty(value = "生态扶贫保障生态护林员",index =62)
    private Double rangerCompensation;

    @ExcelProperty(value = "金融扶贫保障小额信贷及互助资金协会借款金额",index = 63)
    private Double loanAmount;

    @ExcelProperty(value = "金融扶贫保障发展产业类型及规模",index = 64)
    private String industryTypeScale;

    @ExcelProperty(value = "社会保障养老保险年收入",index = 65)
    private Double pensionIncome;

    @ExcelProperty(value = "社会保障高龄补贴年收入",index = 66)
    private Double oldAgeAllowance;

    @ExcelProperty(value = "社会保障残疾补助年收入",index = 67)
    private Double disabilityAllowance;

    @ExcelProperty(value = "社会保障兜底保障类型",index = 68)
    private String pocketType;

    @ExcelProperty(value = "社会保障兜底保障年收入",index = 69)
    private Double pocketAmount;
}
