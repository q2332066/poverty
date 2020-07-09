package com.cloudera.poverty.controlller.region;

import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.vo.CityAllVo;
import com.cloudera.poverty.entity.vo.CityReVo;
import com.cloudera.poverty.entity.vo.CityVo;
import com.cloudera.poverty.service.CityService;
import com.cloudera.poverty.service.DistrictTableService;
import com.cloudera.poverty.service.ResettlementPointTableService;
import com.cloudera.poverty.service.TownshipTableService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/region/est/city")
@Api(description = "地域信息")
@CrossOrigin //跨域
@Slf4j
public class CityController {

    @Autowired
    private CityService cityService;
    @Autowired
    private DistrictTableService districtTableService;
    @Autowired
    private TownshipTableService townshipTableService;
    @Autowired
    private ResettlementPointTableService resettlementPointTableService;


    @ApiOperation("地域树获取")
    @RequestMapping(value = "list",method = RequestMethod.POST,name = "API-DEPT")
    public Lay nestedList(HttpServletRequest request){
        Claims claims = JwtUtils.getMemberIdByJwtToken(request);
        String uid = (String) claims.get("uid");
        String showname = (String) claims.get("showname");
        String regional = (String) claims.get("regional");
        String level = (String) claims.get("level");
        String did = (String) claims.get("did");
        String tid = (String) claims.get("tid");
        String rid = (String) claims.get("rid");
        UserTable userTable = new UserTable();
        userTable.setUId(uid);
        userTable.setShowName(showname);
        userTable.setRegionalId(regional);
        userTable.setDid(did);
        userTable.setTid(tid);
        userTable.setRid(rid);
        userTable.setLevel(level);

        List<CityVo> items=cityService.nestList(userTable);
        return Lay.ok().data(items);
    }

    @ApiOperation("地域获取")
    @RequestMapping(value = "selectlist",method = RequestMethod.POST,name = "API-DEPT")
    public Lay selectList(){
        List<CityAllVo> cityVoList=cityService.selectList();
        int count = cityService.count();
        int count1 = districtTableService.count();
        int count2 = townshipTableService.count();
        int count3 = resettlementPointTableService.count();
        int lon=count+count2+count3+count1;
        return Lay.ok().count((long) lon).data(cityVoList);
    }
    @ApiOperation("地域添加")
    @RequestMapping(value = "save",method = RequestMethod.POST,name = "API-DEPT")
    public Lay saveList(@RequestParam(required = false) String ciId,@RequestParam(required = false) String dId,@RequestParam(required = false) String tId,@RequestParam(required = false) String name){
       CityReVo cityReVo=new CityReVo();
       if(ciId!=null){
           cityReVo.setCiId(ciId);
       }
        if(dId!=null){
            cityReVo.setDId(dId);
        }
        if(tId!=null){
            cityReVo.setTId(tId);
        }


       cityReVo.setName(name);
        cityService.saveList(cityReVo);
        return Lay.ok().msg("成功");
    }
    @ApiOperation("地域删除,所有id要不相同")
    @RequestMapping(value = "delete",method = RequestMethod.POST,name = "API-DEPT")
    public Lay deleteList(String id){
        boolean b3 = resettlementPointTableService.removeById(id);
        if (b3){return Lay.ok().msg("成功");}
        boolean b2 = townshipTableService.removeById(id);
        if (b2){return Lay.ok().msg("成功");}
        boolean b1 = districtTableService.removeById(id);
        if (b1){return Lay.ok().msg("成功");}
        boolean b = cityService.removeById(id);
        if (b){return Lay.ok().msg("成功");}
        return Lay.error().msg("失败");
    }
}

