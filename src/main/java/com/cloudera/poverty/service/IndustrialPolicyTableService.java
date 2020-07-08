package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.api.IndustrialPolicyTable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
public interface IndustrialPolicyTableService extends IService<IndustrialPolicyTable> {

    IndustrialPolicyTable selectByPersonId(String id);

    void removeByPersonId(String id);

}
