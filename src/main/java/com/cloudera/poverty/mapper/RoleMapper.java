package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudera.poverty.entity.admin.RoleTable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper  extends BaseMapper<RoleTable> {
}
