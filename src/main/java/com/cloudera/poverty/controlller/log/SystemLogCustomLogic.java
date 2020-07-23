package com.cloudera.poverty.controlller.log;

import com.cloudera.poverty.aspect.ISystemLogCustomLogic;
import com.cloudera.poverty.entity.system.SystemLog;
import com.cloudera.poverty.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Order(1)
public class SystemLogCustomLogic implements ISystemLogCustomLogic {

    @Autowired
    ISystemLogService systemService;

    @Override
    public void execute(Map<String, Object> params) {
        SystemLog entity = new SystemLog();
        entity.setMethod(params.get("method").toString());
        entity.setOperationDate(LocalDateTime.now());
        entity.setType(2);
        entity.setUser((String) params.get("uid"));
        entity.setUserName((String) params.get("username"));
        entity.setParams((String) params.get("params"));
        entity.setDescription(params.get("desc").toString());
        this.systemService.save(entity);
    }
}
