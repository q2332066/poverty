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
@ApiModel(value="TownshipTable对象", description="")
public class TownshipTable implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "t_id", type = IdType.ASSIGN_ID)
    private String tId;

    @ApiModelProperty(value = "乡镇名称")
    private String township;

    @ApiModelProperty(value = "区县标识")
    private String districtId;


}
