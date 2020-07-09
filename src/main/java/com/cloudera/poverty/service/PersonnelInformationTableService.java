package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.api.PersonnelInformationTable;
import com.cloudera.poverty.entity.vo.PersonGetAllVo;
import com.cloudera.poverty.entity.vo.PersonQueryVo;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
public interface PersonnelInformationTableService extends IService<PersonnelInformationTable> {

    List<String> batchImport(InputStream inputStream, String uid);

    String savePerson(PersonGetAllVo personGetAllVo);

    IPage<PersonGetAllVo> selectAll(Long page, Long limit, PersonQueryVo personQueryVo);

    //  区人员查询
    IPage<PersonGetAllVo> selectDisPerson(Long page, Long limit, PersonQueryVo personQueryVo);
    //乡镇全部人员
    IPage<PersonGetAllVo> selectTowPerson(Long page, Long limit, PersonQueryVo personQueryVo);
    //安置点全部人员
    IPage<PersonGetAllVo> selectResPerson(Long page, Long limit, PersonQueryVo personQueryVo);

    boolean removePerById(String id);

    boolean updatePerById(PersonGetAllVo personGetAllVo);

    PersonGetAllVo selectPerOne(String id);

    IPage<PersonGetAllVo> findAll(PersonQueryVo personQueryVo, String level, String regionalId);

    IPage<PersonAllVo> findAllExcel(PersonQueryVo personQueryVo, String level, String regionalId);
}
