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
@ApiModel(value="DistrictTable对象", description="")
public class DistrictTable implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "d_id", type = IdType.ASSIGN_ID)
    private String dId;

    @ApiModelProperty(value = "区县")
    private String district;

    @ApiModelProperty(value = "市标识")
    private String cityId;




}
