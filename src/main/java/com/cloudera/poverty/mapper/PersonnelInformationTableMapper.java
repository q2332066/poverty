package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudera.poverty.entity.api.PersonnelInformationTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.vo.PersonGetAllVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
@Repository
public interface PersonnelInformationTableMapper extends BaseMapper<PersonnelInformationTable> {

    List<PersonGetAllVo> selectAllPerson(
            Page<PersonGetAllVo> pageParam,
            @Param(Constants.WRAPPER) QueryWrapper<PersonGetAllVo> wrapper);

    List<PersonGetAllVo> selectListAll();

    Long selectAllPersonCount(
            @Param(Constants.WRAPPER) QueryWrapper<PersonGetAllVo> wrapper);

    String insertPer(PersonnelInformationTable personnelInformationTable);

    String selectIdCard(
            @Param(Constants.WRAPPER) QueryWrapper<ResettlementPointTable> wrapper);
}


