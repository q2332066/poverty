package com.cloudera.poverty.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CityAllVo {
    @JsonProperty(value = "cId")
    private String ciId;

    @ApiModelProperty(value = "市名称")
    @JsonProperty(value = "cityName")
    private String cityName;

    @ApiModelProperty(value = "县名称")
    @JsonProperty(value = "patentId")
    private String patentId;
}
