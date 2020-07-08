package com.cloudera.poverty.entity.region;

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
@ApiModel(value="ResettlementPointTable对象", description="")
public class ResettlementPointTable implements Serializable {

    private static final long serialVersionUID=1L;

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
    
}
