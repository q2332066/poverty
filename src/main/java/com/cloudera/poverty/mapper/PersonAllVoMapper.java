package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudera.poverty.entity.api.PersonnelInformationTable;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Package com.cloudera.pa.service.est.mapper
 * @date 2020/6/21 11:13
 * @Copyright © 铜川瀚海睿智大数据学院
 */
@Repository
public interface PersonAllVoMapper  extends BaseMapper<PersonAllVo> {
    void savePerson(List<PersonnelInformationTable> list);

}

