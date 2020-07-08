package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.api.CareerPolicyTable;
import com.cloudera.poverty.entity.api.EnjoyHelpPolicyTable;
import com.cloudera.poverty.entity.api.IndustrialPolicyTable;
import com.cloudera.poverty.entity.api.PersonnelInformationTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.vo.PersonGetAllVo;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;
import com.cloudera.poverty.mapper.*;
import com.cloudera.poverty.service.PersonAllVoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @Package com.cloudera.pa.service.est.service.impl
 * @date 2020/6/21 16:33
 * @Copyright
 */
@Service
public class PersonAllVoServiceImpl extends ServiceImpl<PersonAllVoMapper, PersonAllVo> implements PersonAllVoService {


    @Autowired
    private IndustrialPolicyTableMapper industrialPolicyTableMapper;
    @Autowired
    private CareerPolicyTableMapper careerPolicyTableMapper;
    @Autowired
    private EnjoyHelpPolicyTableMapper enjoyHelpPolicyTableMapper;
    @Autowired
    private PersonnelInformationTableMapper personnelInformationTableMapper;
    @Autowired
    private ResettlementPointTableMapper resettlementPointTableMapper;



    public Integer countPerson(String idCard){
        QueryWrapper<PersonnelInformationTable> wrapper=new QueryWrapper<>();
        wrapper.eq("id_card",idCard);
        Integer integer = personnelInformationTableMapper.selectCount(wrapper);
        return integer;
    }

    @Override
    public boolean savePerson(PersonAllVo personAllVo) {
        PersonGetAllVo personGetAllVo=new PersonGetAllVo();
        BeanUtils.copyProperties(personAllVo,personGetAllVo);

        QueryWrapper<ResettlementPointTable> wrapper=new QueryWrapper<>();
        wrapper.eq("resettlement_point",personAllVo.getResettlementPoint());
        String s = resettlementPointTableMapper.selectId(wrapper);

        PersonnelInformationTable personnelInformationTable = new PersonnelInformationTable();
        BeanUtils.copyProperties(personGetAllVo,personnelInformationTable);
        personnelInformationTable.setResettlementPointId(s);
        QueryWrapper<ResettlementPointTable> wrapperPer=new QueryWrapper<>();
        wrapperPer.eq("id_card",personnelInformationTable.getIdCard());
        String id=personnelInformationTableMapper.selectIdCard(wrapperPer);
        if (id!=null){

            QueryWrapper<IndustrialPolicyTable> wrapperIndus=new QueryWrapper<>();
            wrapperIndus.eq("personnel_information_id",id);
            industrialPolicyTableMapper.delete(wrapperIndus);

            QueryWrapper<CareerPolicyTable> wrapperCareer=new QueryWrapper<>();
            wrapperCareer.eq("personnel_information_id",id);
            careerPolicyTableMapper.delete(wrapperCareer);

            QueryWrapper<EnjoyHelpPolicyTable> wrapperEnjoy=new QueryWrapper<>();
            wrapperCareer.eq("personnel_information_id",id);
            enjoyHelpPolicyTableMapper.delete(wrapperEnjoy);

            personnelInformationTableMapper.deleteById(id);
        }

       personnelInformationTableMapper.insert(personnelInformationTable);
        String pId = personnelInformationTable.getPId();

            IndustrialPolicyTable industrialPolicyTable=new IndustrialPolicyTable();
            BeanUtils.copyProperties(personGetAllVo,industrialPolicyTable);
            industrialPolicyTable.setPersonnelInformationId(pId);
            industrialPolicyTableMapper.insert(industrialPolicyTable);

            CareerPolicyTable careerPolicyTable=new CareerPolicyTable();
            BeanUtils.copyProperties(personGetAllVo,careerPolicyTable);
            careerPolicyTable.setPersonnelInformationId(pId);
            careerPolicyTableMapper.insert(careerPolicyTable);

            EnjoyHelpPolicyTable enjoyHelpPolicyTable=new EnjoyHelpPolicyTable();
            BeanUtils.copyProperties(personGetAllVo,enjoyHelpPolicyTable);
            enjoyHelpPolicyTable.setPersonnelInformationId(pId);
            enjoyHelpPolicyTableMapper.insert(enjoyHelpPolicyTable);

        return true;
    }
}
