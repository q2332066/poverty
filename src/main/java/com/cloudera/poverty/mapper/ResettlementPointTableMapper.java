package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
@Repository
public interface ResettlementPointTableMapper extends BaseMapper<ResettlementPointTable> {


      String selectId(
              @Param(Constants.WRAPPER) QueryWrapper<ResettlementPointTable> wrapper);


}
