package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;

/**
 * @version V1.0
 * @Package com.cloudera.pa.service.est.service
 * @date 2020/6/21 16:22
 * @Copyright © 铜川瀚海睿智大数据学院
 */
public interface PersonAllVoService extends IService<PersonAllVo> {

    boolean savePerson(PersonAllVo personAllVo);

    Integer countPerson(String idCard);



}
