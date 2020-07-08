package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.region.TownshipTable;
import com.cloudera.poverty.mapper.TownshipTableMapper;
import com.cloudera.poverty.service.TownshipTableService;
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
public class TownshipTableServiceImpl extends ServiceImpl<TownshipTableMapper, TownshipTable> implements TownshipTableService {

}
