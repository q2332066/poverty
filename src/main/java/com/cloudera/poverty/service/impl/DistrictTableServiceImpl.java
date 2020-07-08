package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.region.DistrictTable;
import com.cloudera.poverty.mapper.DistrictTableMapper;
import com.cloudera.poverty.service.DistrictTableService;
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
public class DistrictTableServiceImpl extends ServiceImpl<DistrictTableMapper, DistrictTable> implements DistrictTableService {

}
