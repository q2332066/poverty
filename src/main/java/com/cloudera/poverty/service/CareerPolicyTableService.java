package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.api.CareerPolicyTable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
public interface CareerPolicyTableService extends IService<CareerPolicyTable> {

    void removeByPersonId(String id);

    CareerPolicyTable selectByPersonId(String id);
}
