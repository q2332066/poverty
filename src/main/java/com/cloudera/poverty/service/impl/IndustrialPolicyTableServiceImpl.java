package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.api.IndustrialPolicyTable;
import com.cloudera.poverty.mapper.IndustrialPolicyTableMapper;
import com.cloudera.poverty.service.IndustrialPolicyTableService;
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
public class IndustrialPolicyTableServiceImpl extends ServiceImpl<IndustrialPolicyTableMapper, IndustrialPolicyTable> implements IndustrialPolicyTableService {

    /**
     * 查询产
     * 业发展信息
     * @param id
     * @return
     */
    @Override
    public IndustrialPolicyTable selectByPersonId(String id) {

        QueryWrapper<IndustrialPolicyTable> wrapper=new QueryWrapper<>();
        wrapper.eq("personnel_information_id",id);
        IndustrialPolicyTable industrialPolicyTable = baseMapper.selectOne(wrapper);
        return industrialPolicyTable;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void removeByPersonId(String id) {
        QueryWrapper<IndustrialPolicyTable> wrapper=new QueryWrapper<>();
        wrapper.eq("personnel_information_id",id);
        int i = baseMapper.delete(wrapper);
    }
}
