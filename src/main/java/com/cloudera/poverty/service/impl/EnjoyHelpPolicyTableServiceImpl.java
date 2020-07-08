package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.api.EnjoyHelpPolicyTable;
import com.cloudera.poverty.mapper.EnjoyHelpPolicyTableMapper;
import com.cloudera.poverty.service.EnjoyHelpPolicyTableService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
@Service
public class EnjoyHelpPolicyTableServiceImpl extends ServiceImpl<EnjoyHelpPolicyTableMapper, EnjoyHelpPolicyTable> implements EnjoyHelpPolicyTableService {

    @Override
    public void removeByPersonId(String id) {
        QueryWrapper<EnjoyHelpPolicyTable> wrapper=new QueryWrapper<>();
        wrapper.eq("personnel_information_id",id);
        int i = baseMapper.delete(wrapper);
    }
}
