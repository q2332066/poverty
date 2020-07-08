package com.cloudera.poverty.controlller.region;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.vo.ResettlementQueryVo;
import com.cloudera.poverty.service.ResettlementPointTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/region/est/resettlement")
@Slf4j
@CrossOrigin //跨域
@Api(description = "安置点管理")
public class ResettlementPointTableController {

    @Autowired
    private ResettlementPointTableService pointTableService;


    @ApiOperation("安置点条件查询")
    @RequestMapping(value = "list/{page}/{limit}",method = RequestMethod.POST,name = "API-DEPT")
    private Lay getList(
            @ApiParam(value = "页数",required = true)@PathVariable Long page,
            @ApiParam(value = "每页记录数",required = true)@PathVariable Long limit,
            @ApiParam("安置点查询对象") ResettlementQueryVo resettlementQueryVo
            ){
        Page<ResettlementPointTable> pageParam=new Page<>(page,limit);
        IPage<ResettlementPointTable> pageModel=pointTableService.selectPage(pageParam,resettlementQueryVo);
        List<ResettlementPointTable> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return Lay.ok().count(total).data(records);
    }

    @ApiOperation("添加安置点")
    @RequestMapping(value = "save",method = RequestMethod.POST,name = "API-DEPT")
    public Lay saveDistrict(@RequestBody ResettlementPointTable resettlementPointTable){
        boolean save = pointTableService.save(resettlementPointTable);
        if (save){
            return Lay.ok().msg("添加成功");
        }
        return Lay.error().msg("添加失败");
    }
    @ApiOperation("乡镇id安置点查询")
    @RequestMapping(value = "selectList",method = RequestMethod.GET,name = "API-DEPT")
    public Lay selectDisList(
            @ApiParam(value = "乡镇ID",required = true) @RequestParam String towId
    ){
        QueryWrapper<ResettlementPointTable> wrapper=new QueryWrapper<>();
        wrapper.eq("township_id",towId);
        List<ResettlementPointTable> list = pointTableService.list(wrapper);
        int count = pointTableService.count(wrapper);
        return Lay.ok().count((long) count).data(list);
    }
    @ApiOperation("删除安置点")
    @RequestMapping(value = "delete",method = RequestMethod.GET,name = "API-DEPT")
    public Lay removeDistrict(@RequestParam String resId){
        boolean save = pointTableService.removeById(resId);
        if (save){
            return Lay.ok().msg("删除成功");
        }
        return Lay.error().msg("删除失败");
    }
    @ApiOperation("修改安置点")
    @RequestMapping(value = "update",method = RequestMethod.POST,name = "API-DEPT")
    public Lay updateDistrict(@RequestBody ResettlementPointTable resettlementPointTable){
        boolean b = pointTableService.updateById(resettlementPointTable);
        if (b){
            return Lay.ok().msg("修改成功");
        }
        return Lay.error().msg("修改失败");
    }
}


