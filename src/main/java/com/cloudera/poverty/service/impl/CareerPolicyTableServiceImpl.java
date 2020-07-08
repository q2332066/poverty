package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.api.CareerPolicyTable;
import com.cloudera.poverty.mapper.CareerPolicyTableMapper;
import com.cloudera.poverty.service.CareerPolicyTableService;
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
public class CareerPolicyTableServiceImpl extends ServiceImpl<CareerPolicyTableMapper, CareerPolicyTable> implements CareerPolicyTableService {

    /**
     * 删除
     * 就业创业模块
     * @param id
     */
    @Override
    public void removeByPersonId(String id) {
        QueryWrapper<CareerPolicyTable> wrapper=new QueryWrapper<>();
        wrapper.eq("personnel_information_id",id);
        int i = baseMapper.delete(wrapper);
    }

    /**
     * 查询
     * @param id
     * @return
     */
    @Override
    public CareerPolicyTable selectByPersonId(String id) {
        QueryWrapper<CareerPolicyTable> wrapper=new QueryWrapper<>();
        wrapper.eq("personnel_information_id",id);
        CareerPolicyTable careerPolicy = baseMapper.selectOne(wrapper);
        return careerPolicy;
    }
}
