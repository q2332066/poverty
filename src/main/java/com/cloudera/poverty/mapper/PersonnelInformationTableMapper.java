package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudera.poverty.entity.api.PersonnelInformationTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.vo.PersonGetAllVo;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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


    Long selectAllPersonCount(
            @Param(Constants.WRAPPER) QueryWrapper<PersonGetAllVo> wrapper);

    String insertPer(PersonnelInformationTable personnelInformationTable);

    String selectIdCard(
            @Param(Constants.WRAPPER) QueryWrapper<ResettlementPointTable> wrapper);

    List<PersonAllVo> selectAllPersonExcel(Page<PersonAllVo> pageParam,
                                           @Param(Constants.WRAPPER) QueryWrapper<PersonAllVo> wrapper);

    @Select("select count(${item})\n" +
            "        from\n" +
            "        personnel_information_table  P\n" +
            "        left join\n" +
            "        industrial_policy_table  I\n" +
            "        on P.p_id=I.personnel_information_id\n" +
            "        left join\n" +
            "        career_policy_table Ca\n" +
            "        on P.p_id=Ca.personnel_information_id\n" +
            "        left join\n" +
            "        enjoy_help_policy_table E\n" +
            "        on P.p_id=E.personnel_information_id\n" +
            "        left join\n" +
            "        resettlement_point_table R\n" +
            "        on P.resettlement_point_id=R.r_id\n" +
            "        left join\n" +
            "        township_table T\n" +
            "        on R.township_id=T.t_id\n" +
            "        left join\n" +
            "        district_table D\n" +
            "        on T.district_id=D.d_id\n" +
            "        left join\n" +
            "        city Ci\n" +
            "        on D.city_id=Ci.ci_id where D.d_id=#{did}")
    Integer disPercentage(@Param("did")String regionalId, @Param("item")String item);

    List<PersonGetAllVo> selectPerson(@Param(Constants.WRAPPER)QueryWrapper<PersonGetAllVo> wrapper);
}


