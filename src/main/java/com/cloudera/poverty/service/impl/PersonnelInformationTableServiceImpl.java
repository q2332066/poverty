package com.cloudera.poverty.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.result.UserRE;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.api.CareerPolicyTable;
import com.cloudera.poverty.entity.api.EnjoyHelpPolicyTable;
import com.cloudera.poverty.entity.api.IndustrialPolicyTable;
import com.cloudera.poverty.entity.api.PersonnelInformationTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.vo.PersonGetAllVo;
import com.cloudera.poverty.entity.vo.PersonQueryVo;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;
import com.cloudera.poverty.listener.ExcelSubjectDataListener;
import com.cloudera.poverty.mapper.*;
import com.cloudera.poverty.service.PersonAllVoService;
import com.cloudera.poverty.service.PersonnelInformationTableService;
import org.apache.http.HttpRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
@Service
public class PersonnelInformationTableServiceImpl extends ServiceImpl<PersonnelInformationTableMapper, PersonnelInformationTable> implements PersonnelInformationTableService {

    @Autowired
    private IndustrialPolicyTableMapper industrialPolicyTableMapper;
    @Autowired
    private CareerPolicyTableMapper careerPolicyTableMapper;
    @Autowired
    private EnjoyHelpPolicyTableMapper enjoyHelpPolicyTableMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PersonAllVoService personAllVoService;
    @Autowired
    private ResettlementPointTableMapper resettlementPointTableMapper;

    @Override
    public IPage<PersonGetAllVo> selectAll(Long page, Long limit, PersonQueryVo personQueryVo) {
        QueryWrapper<PersonGetAllVo> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(personQueryVo.getPerId())){ //id
            wrapper.eq("P.p_id",personQueryVo.getPerId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getName())){ //姓名
            wrapper.like("P.name",personQueryVo.getName());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHost())){ //户主姓名
            wrapper.eq("P.host",personQueryVo.getHost());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResettlementPointId())){ //安置点id
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResettlementPointId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getArrangement())){  //安置方式
            if (personQueryVo.getArrangement().equals("1")){
                wrapper.like("P.arrangement","集中");
            }
            if (personQueryVo.getArrangement().equals("2")){
                wrapper.notLike("P.arrangement","集中");
            }
        }
        if (!StringUtils.isEmpty(personQueryVo.getIdCard())){  //身份证搜索
            wrapper.like("P.id_card",personQueryVo.getIdCard());
        }
        Page<PersonGetAllVo> pageParam=new Page<>(page,limit);
        List<PersonGetAllVo> records=baseMapper.selectAllPerson(pageParam,wrapper);
        return pageParam.setRecords(records);
    }
    //添加人员方法
    @Override
    @Transactional
    public String savePerson(PersonGetAllVo personGetAllVo) {
        String rId = personGetAllVo.getRId();

        PersonnelInformationTable personnelInformationTable = new PersonnelInformationTable();
        BeanUtils.copyProperties(personGetAllVo,personnelInformationTable);
        personnelInformationTable.setResettlementPointId(rId);
        baseMapper.insert(personnelInformationTable);
        String pId = personnelInformationTable.getPId();

        IndustrialPolicyTable industrialPolicyTable=new IndustrialPolicyTable();
        industrialPolicyTable.setPersonnelInformationId(pId);
        BeanUtils.copyProperties(personGetAllVo,industrialPolicyTable);
        industrialPolicyTableMapper.insert(industrialPolicyTable);

        CareerPolicyTable careerPolicyTable=new CareerPolicyTable();
        careerPolicyTable.setPersonnelInformationId(pId);
        BeanUtils.copyProperties(personGetAllVo,careerPolicyTable);
        careerPolicyTableMapper.insert(careerPolicyTable);

        EnjoyHelpPolicyTable enjoyHelpPolicyTable=new EnjoyHelpPolicyTable();
        enjoyHelpPolicyTable.setPersonnelInformationId(pId);
        BeanUtils.copyProperties(personGetAllVo,enjoyHelpPolicyTable);
        enjoyHelpPolicyTableMapper.insert(enjoyHelpPolicyTable);
        return pId;
    }
    /**
     * 导入数据
     * @param inputStream
     */
    @Override
    public List<String> batchImport(InputStream inputStream, String uid) {
        ExcelSubjectDataListener excelSubjectDataListener = new ExcelSubjectDataListener(personAllVoService, redisTemplate, uid);
        EasyExcel.read(inputStream, PersonAllVo.class,excelSubjectDataListener)
                .headRowNumber(3).doReadAll();
        List<String> massage = excelSubjectDataListener.massage();
        return massage;
    }

    /**
     * 区县全部人员
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<PersonGetAllVo> selectDisPerson(Long page, Long limit,PersonQueryVo personQueryVo) {
        QueryWrapper<PersonGetAllVo> wrapper=new QueryWrapper<>();
        if (UserRE.level.equals("2")){
            wrapper.eq("D.d_id", UserRE.user);
        }
        if (!StringUtils.isEmpty(personQueryVo.getPerId())){ //id
            wrapper.eq("P.p_id",personQueryVo.getPerId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getName())){ //姓名
            wrapper.like("P.name",personQueryVo.getName());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHost())){ //户主姓名
            wrapper.eq("P.host",personQueryVo.getHost());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResettlementPointId())){ //安置点id
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResettlementPointId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getArrangement())){  //安置方式
            if (personQueryVo.getArrangement().equals("1")){
                wrapper.like("P.arrangement","集中");
            }
            if (personQueryVo.getArrangement().equals("2")){
                wrapper.notLike("P.arrangement","集中");
            }
        }
        if (!StringUtils.isEmpty(personQueryVo.getIdCard())){  //身份证搜索
            wrapper.like("P.id_card",personQueryVo.getIdCard());
        }
        Page<PersonGetAllVo> pageParam=new Page<>(page,limit);
        wrapper.eq("D.d_id",personQueryVo.getDisId());
        List<PersonGetAllVo> records=baseMapper.selectAllPerson(pageParam,wrapper);
        //Long count=baseMapper.selectAllPersonCount(wrapper);
        return pageParam.setRecords(records);
    }

    /**
     * 乡镇全部人员
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<PersonGetAllVo> selectTowPerson(Long page, Long limit, PersonQueryVo personQueryVo) {
        QueryWrapper<PersonGetAllVo> wrapper=new QueryWrapper<>();
        if (UserRE.level.equals("3")){
            wrapper.eq("T.t_id", UserRE.user);
        }
        if (!StringUtils.isEmpty(personQueryVo.getPerId())){ //id
            wrapper.eq("P.p_id",personQueryVo.getPerId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getName())){ //姓名
            wrapper.like("P.name",personQueryVo.getName());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHost())){ //户主姓名
            wrapper.eq("P.host",personQueryVo.getHost());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResettlementPointId())){ //安置点id
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResettlementPointId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getArrangement())){  //安置方式
            if (personQueryVo.getArrangement().equals("1")){
                wrapper.like("P.arrangement","集中");
            }
            if (personQueryVo.getArrangement().equals("2")){
                wrapper.notLike("P.arrangement","集中");
            }
        }
        if (!StringUtils.isEmpty(personQueryVo.getIdCard())){  //身份证搜索
            wrapper.like("P.id_card",personQueryVo.getIdCard());
        }
        Page<PersonGetAllVo> pageParam=new Page<>(page,limit);
        wrapper.eq("T.t_id",personQueryVo.getTowId());
        List<PersonGetAllVo> records=baseMapper.selectAllPerson(pageParam,wrapper);
        return pageParam.setRecords(records);
    }

    /**
     * 安置点全部人员
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<PersonGetAllVo> selectResPerson(Long page, Long limit, PersonQueryVo personQueryVo) {
        QueryWrapper<PersonGetAllVo> wrapper=new QueryWrapper<>();
        if (UserRE.level.equals("4")){
            wrapper.eq("P.resettlement_point_id", UserRE.user);
        }
        if (!StringUtils.isEmpty(personQueryVo.getPerId())){ //id
            wrapper.eq("P.p_id",personQueryVo.getPerId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getName())){ //姓名
            wrapper.like("P.name",personQueryVo.getName());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHost())){ //户主姓名
            wrapper.eq("P.host",personQueryVo.getHost());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResettlementPointId())){ //安置点id
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResettlementPointId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getArrangement())){  //安置方式
            if (personQueryVo.getArrangement().equals("1")){
                wrapper.like("P.arrangement","集中");
            }
            if (personQueryVo.getArrangement().equals("2")){
                wrapper.notLike("P.arrangement","集中");
            }
        }
        if (!StringUtils.isEmpty(personQueryVo.getIdCard())){  //身份证搜索
            wrapper.like("P.id_card",personQueryVo.getIdCard());
        }
        Page<PersonGetAllVo> pageParam=new Page<>(page,limit);
        wrapper.eq("R.r_id",personQueryVo.getResId());
        List<PersonGetAllVo> records=baseMapper.selectAllPerson(pageParam,wrapper);
        return pageParam.setRecords(records);
    }

    /**
     * 删除人员信息
     * @param id
     * @return
     */
    @Override
    public boolean removePerById(String id) {

        QueryWrapper<IndustrialPolicyTable> wrapperIndus=new QueryWrapper<>();
        wrapperIndus.eq("personnel_information_id",id);
        industrialPolicyTableMapper.delete(wrapperIndus);

        QueryWrapper<CareerPolicyTable> wrapperCareer=new QueryWrapper<>();
        wrapperCareer.eq("personnel_information_id",id);
        careerPolicyTableMapper.delete(wrapperCareer);

        QueryWrapper<EnjoyHelpPolicyTable> wrapperEnjoy=new QueryWrapper<>();
        wrapperCareer.eq("personnel_information_id",id);
        enjoyHelpPolicyTableMapper.delete(wrapperEnjoy);

        baseMapper.deleteById(id);
            return true;
    }
    /**
     * 修改人员信息
     * @param personGetAllVo //
     * @return
     */
    @Override
    public boolean updatePerById(PersonGetAllVo personGetAllVo) {
        PersonnelInformationTable personnelInformationTable = new PersonnelInformationTable();
        BeanUtils.copyProperties(personGetAllVo,personnelInformationTable);
        String pId = personGetAllVo.getPId();
//        if(org.apache.commons.lang3.StringUtils.isEmpty(pId)){
//            pId = personGetAllVo.getPid();
//        }
        baseMapper.updateById(personnelInformationTable);

        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("personnel_information_id",pId);

        IndustrialPolicyTable industrialPolicyTable=new IndustrialPolicyTable();
        industrialPolicyTable.setPersonnelInformationId(pId);
        BeanUtils.copyProperties(personGetAllVo,industrialPolicyTable);
        industrialPolicyTableMapper.update(industrialPolicyTable,wrapper);

        CareerPolicyTable careerPolicyTable=new CareerPolicyTable();
        careerPolicyTable.setPersonnelInformationId(pId);
        BeanUtils.copyProperties(personGetAllVo,careerPolicyTable);
        careerPolicyTableMapper.update(careerPolicyTable,wrapper);

        EnjoyHelpPolicyTable enjoyHelpPolicyTable=new EnjoyHelpPolicyTable();
        enjoyHelpPolicyTable.setPersonnelInformationId(pId);
        BeanUtils.copyProperties(personGetAllVo,enjoyHelpPolicyTable);
        enjoyHelpPolicyTableMapper.update(enjoyHelpPolicyTable,wrapper);
        return true;
    }

    //查询单个
    @Override
    public PersonGetAllVo selectPerOne(String id) {
        PersonQueryVo personQueryVo=new PersonQueryVo();
        personQueryVo.setPerId(id);
        IPage<PersonGetAllVo> personGetAllVoIPage = this.selectAll((long) 1, (long) 1, personQueryVo);
        List<PersonGetAllVo> records = personGetAllVoIPage.getRecords();
        PersonGetAllVo personGetAllVo = new PersonGetAllVo();
        for (int i = 0; i < records.size(); i++) {
           personGetAllVo = records.get(i);
        }
        return personGetAllVo;
    }

    @Override
    public IPage<PersonGetAllVo> findAll(PersonQueryVo personQueryVo, String level, String regionalId) {
        QueryWrapper<PersonGetAllVo> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("P.host_id");
        if (level.equals("2")){
            wrapper.eq("D.d_id", regionalId);
        }
        if (level.equals("3")){
            wrapper.eq("T.t_id", regionalId);
        }
        if (level.equals("4")){
            wrapper.eq("P.resettlement_point_id", regionalId);
        }
        if (!StringUtils.isEmpty(personQueryVo.getDisId())){
            wrapper.eq("D.d_id",personQueryVo.getDisId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getTowId())){
            wrapper.eq("T.t_id",personQueryVo.getTowId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResId())){
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getPerId())){
            wrapper.eq("P.p_id",personQueryVo.getPerId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getName())){ //姓名
            wrapper.like("P.name",personQueryVo.getName());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHost())){ //户主姓名
            wrapper.eq("P.host",personQueryVo.getHost());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResettlementPointId())){ //安置点id
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResettlementPointId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getArrangement())){  //安置方式
            if (personQueryVo.getArrangement().equals("1")){
                wrapper.like("P.arrangement","集中");
            }
            if (personQueryVo.getArrangement().equals("2")){
                wrapper.notLike("P.arrangement","集中");
            }
        }
        if (!StringUtils.isEmpty(personQueryVo.getIdCard())){  //身份证搜索
            wrapper.like("P.id_card",personQueryVo.getIdCard());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHostId())){  //户主身份证搜索relationship
            wrapper.eq("P.host_id",personQueryVo.getHostId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getRelationship())){  //户主关系搜索
            wrapper.eq("P.relationship",personQueryVo.getRelationship());
        }
        Page<PersonGetAllVo> pageParam=new Page<>(personQueryVo.getPage(),personQueryVo.getLimit());
        List<PersonGetAllVo> records=baseMapper.selectAllPerson(pageParam,wrapper);
        return pageParam.setRecords(records);
    }

    @Override
    public IPage<PersonAllVo> findAllExcel(PersonQueryVo personQueryVo, String level, String regionalId) {
        QueryWrapper<PersonAllVo> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("P.host_id");
        if (level.equals("2")){
            wrapper.eq("D.d_id", regionalId);
        }
        if (level.equals("3")){
            wrapper.eq("T.t_id", regionalId);
        }
        if (level.equals("4")){
            wrapper.eq("P.resettlement_point_id", regionalId);
        }
        if (!StringUtils.isEmpty(personQueryVo.getDisId())){
            wrapper.eq("D.d_id",personQueryVo.getDisId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getTowId())){
            wrapper.eq("T.t_id",personQueryVo.getTowId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResId())){
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getPerId())){
            wrapper.eq("P.p_id",personQueryVo.getPerId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getName())){ //姓名
            wrapper.like("P.name",personQueryVo.getName());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHost())){ //户主姓名
            wrapper.eq("P.host",personQueryVo.getHost());
        }
        if (!StringUtils.isEmpty(personQueryVo.getResettlementPointId()) && !"null".equals(personQueryVo.getResettlementPointId())){ //安置点id
            wrapper.eq("P.resettlement_point_id",personQueryVo.getResettlementPointId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getArrangement())){  //安置方式
            if (personQueryVo.getArrangement().equals("1")){
                wrapper.like("P.arrangement","集中");
            }
            if (personQueryVo.getArrangement().equals("2")){
                wrapper.notLike("P.arrangement","集中");
            }
        }
        if (!StringUtils.isEmpty(personQueryVo.getIdCard())){  //身份证搜索
            wrapper.like("P.id_card",personQueryVo.getIdCard());
        }
        if (!StringUtils.isEmpty(personQueryVo.getHostId())){  //户主身份证搜索relationship
            wrapper.eq("P.host_id",personQueryVo.getHostId());
        }
        if (!StringUtils.isEmpty(personQueryVo.getRelationship())){  //户主关系搜索
            wrapper.eq("P.relationship",personQueryVo.getRelationship());
        }
        Page<PersonAllVo> pageParam=new Page<>(personQueryVo.getPage(),personQueryVo.getLimit());
        List<PersonAllVo> records=baseMapper.selectAllPersonExcel(pageParam,wrapper);
        return pageParam.setRecords(records);
    }
}
