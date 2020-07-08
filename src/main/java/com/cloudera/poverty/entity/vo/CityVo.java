package com.cloudera.poverty.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="City对象", description="")
public class CityVo implements Serializable {

    private static final long serialVersionUID=1L;
    @TableId(value = "ci_id")
    @JsonProperty(value = "patentId")
    private String ciId;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "县名称")
    private List<DistrictVo> children=new ArrayList<>();
}
