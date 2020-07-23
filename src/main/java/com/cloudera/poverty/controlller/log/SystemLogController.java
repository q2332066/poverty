package com.cloudera.poverty.controlller.log;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.entity.system.SystemLog;
import com.cloudera.poverty.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengtoos
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/log")
public class SystemLogController {

    @Autowired
    ISystemLogService systemLogService;

    @GetMapping("/list")
    public Lay list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<SystemLog> page = new Page<>(pageNumber, pageSize);
        return Lay.ok().code(0).data((this.systemLogService.page(page)));
    }
}
