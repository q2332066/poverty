package com.cloudera.poverty.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DistrictVo implements Serializable {

    private static final long serialVersionUID=1L;

    @JsonProperty(value = "cId")
    private String dId;

    @ApiModelProperty(value = "区县")
    @JsonProperty(value = "cityName")
    private String district;

    @ApiModelProperty(value = "市标识")
    @JsonProperty(value = "patentId")
    private String cityId;

    @ApiModelProperty("乡镇")
    private List<TownshipVo> children=new ArrayList<>();
}
