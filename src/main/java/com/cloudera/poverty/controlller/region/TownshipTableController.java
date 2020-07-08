package com.cloudera.poverty.controlller.region;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.entity.region.TownshipTable;
import com.cloudera.poverty.service.TownshipTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2020-06-04
 */


@RestController
@RequestMapping("/region/est/township")
@Api(description = "乡镇管理")
@CrossOrigin //跨域
@Slf4j
public class TownshipTableController {

    @Autowired
    private TownshipTableService townshipTableService;

    @ApiOperation("添加乡镇")
    @RequestMapping(value = "save",method = RequestMethod.POST,name = "API-DEPT")
    public Lay saveDistrict(@RequestBody TownshipTable townshipTable){
        boolean save = townshipTableService.save(townshipTable);
        if (save){
            return Lay.ok().msg("添加成功");
        }
        return Lay.error().msg("添加失败");
    }

    @ApiOperation("乡镇查询")
    @RequestMapping(value = "select",method = RequestMethod.POST,name = "API-DEPT")
    public Lay selectDisList(
            @ApiParam(value = "县ID",required = true)@RequestParam String disId
    ){
        QueryWrapper<TownshipTable> wrapper=new QueryWrapper<>();
        wrapper.eq("district_id",disId);
        List<TownshipTable> list = townshipTableService.list(wrapper);
        int count = townshipTableService.count(wrapper);
        return Lay.ok().count((long) count).data(list);
    }

    @ApiOperation("删除乡镇")
    @RequestMapping(value = "delete",method = RequestMethod.GET,name = "API-DEPT")
    public Lay removeDistrict(@RequestParam String towId){

        boolean save = townshipTableService.removeById(towId);
        if (save){
            return Lay.ok().msg("删除成功");
        }
        return Lay.error().msg("删除失败");
    }
    @ApiOperation("修改乡镇")
    @RequestMapping(value = "update",method = RequestMethod.POST,name = "API-DEPT")
    public Lay updateDistrict(@RequestBody TownshipTable townshipTable){
        boolean b = townshipTableService.updateById(townshipTable);
        if (b){
            return Lay.ok().msg("修改成功");
        }
        return Lay.error().msg("修改失败");
    }
}

