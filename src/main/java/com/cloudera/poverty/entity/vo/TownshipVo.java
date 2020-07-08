package com.cloudera.poverty.entity.vo;

import com.cloudera.poverty.entity.region.ResettlementPointTable;
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
public class TownshipVo implements Serializable {

    private static final long serialVersionUID=1L;

    @JsonProperty(value = "cId")
    private String tId;

    @ApiModelProperty(value = "乡镇名称")
    @JsonProperty(value = "cityName")
    private String township;

    @ApiModelProperty(value = "区县标识")
    @JsonProperty(value = "patentId")
    private String districtId;

    @ApiModelProperty("安置点")
    private List<ResettlementPointTable> children=new ArrayList<>();
}
