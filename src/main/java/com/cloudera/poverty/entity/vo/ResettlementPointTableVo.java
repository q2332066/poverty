package com.cloudera.poverty.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ResettlementPointTableVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "安置点id")
    @JsonProperty(value = "cId")
    private String rId;

    @ApiModelProperty(value = "安置点名称")
    @JsonProperty(value = "cityName")
    private String resettlementPoint;

    @ApiModelProperty(value = "安置点经度")
    private String longitude;

    @ApiModelProperty(value = "安置点纬度")
    private String latitude;

    @ApiModelProperty(value = "乡镇标识")
    @JsonProperty(value = "patentId")
    private String townshipId;
}
