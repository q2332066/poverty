package com.cloudera.poverty.controlller.region;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.entity.region.DistrictTable;
import com.cloudera.poverty.service.CityService;
import com.cloudera.poverty.service.DistrictTableService;
import com.cloudera.poverty.service.PersonnelInformationTableService;
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
@RequestMapping("/region/est/district")
@Api(description = "区县管理")
@CrossOrigin //跨域
@Slf4j
public class DistrictTableController {

    @Autowired
    private DistrictTableService districtTableService;

    @ApiOperation("添加区县")
    @RequestMapping(value = "saveDistrict",method = RequestMethod.POST,name = "API-DEPT")
    public Lay saveDistrict(@RequestBody DistrictTable districtTable){

        boolean save = districtTableService.save(districtTable);
        if (save){
            return Lay.ok().msg("添加成功");
        }
        return Lay.error().msg("添加失败");
    }

    @ApiOperation("根据市id对区县查询")
    @RequestMapping(value = "selectDistrictList",method = RequestMethod.GET,name = "API-DEPT")
    public Lay selectDisList(
            @ApiParam(value = "市ID",required = true)@RequestParam String cityId
    ){
        QueryWrapper<DistrictTable> wrapper=new QueryWrapper<>();
        wrapper.eq("city_id",cityId);
        List<DistrictTable> list = districtTableService.list(wrapper);
        int count = districtTableService.count(wrapper);
        return Lay.ok().count((long) count).data(list);
    }

    @ApiOperation("根据区县id删除区县")
    @RequestMapping(value = "delete",method = RequestMethod.GET,name = "API-DEPT")
    public Lay removeDistrict(@RequestParam String disId){

        boolean save = districtTableService.removeById(disId);
        if (save){
            return Lay.ok().msg("删除成功");
        }
        return Lay.ok().msg("删除失败");
    }
    @ApiOperation("根据id修改区县")
    @RequestMapping(value = "update",method = RequestMethod.POST,name = "API-DEPT")
    public Lay updateDistrict(@RequestBody DistrictTable districtTable){
        boolean b = districtTableService.updateById(districtTable);
        if (b){
            return Lay.ok().msg("修改成功");
        }
        return Lay.ok().msg("修改失败");
    }
}

