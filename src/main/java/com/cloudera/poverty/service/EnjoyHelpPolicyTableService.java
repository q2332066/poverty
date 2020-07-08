package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.api.EnjoyHelpPolicyTable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
public interface EnjoyHelpPolicyTableService extends IService<EnjoyHelpPolicyTable> {

    void removeByPersonId(String id);

}
