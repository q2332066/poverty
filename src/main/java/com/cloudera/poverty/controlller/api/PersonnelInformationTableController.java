package com.cloudera.poverty.controlller.api;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.utils.ExceptionUtils;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.vo.PersonGetAllVo;
import com.cloudera.poverty.entity.vo.PersonQueryVo;
import com.cloudera.poverty.service.PersonnelInformationTableService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-06-04
 */
@Api(description = "人员基本信息管理")
@RestController
@RequestMapping("/api/est/person")
@CrossOrigin//(allowCredentials = "true", allowedHeaders = "*")//跨域
@Slf4j
public class PersonnelInformationTableController {
    @Autowired
    private PersonnelInformationTableService personnelInformationService;
    @Autowired
    private RedisTemplate redisTemplate;

    //人员添加
    @ApiOperation("人员基本信息添加")
    @RequestMapping(value = "save", method = RequestMethod.POST, name = "API-SELECT-PERSON")
    public Lay savePersonnelInformation(
            @ApiParam(value = "人员基本信息")
            @RequestBody PersonGetAllVo personGetAllVo) {
        personnelInformationService.savePerson(personGetAllVo);
        return Lay.ok().msg("添加成功");
    }

    //连表删除
    @ApiOperation("删除人员信息")
    @RequestMapping(value = "delete", method = RequestMethod.GET, name = "API-SELECT-PERSON")
    public Lay deletePersonnelInformation(
            @ApiParam(value = "删除人员", required = true) @RequestParam String id
    ) {
        boolean b = personnelInformationService.removePerById(id);
        if (b) {
            return Lay.ok().msg("删除成功");
        } else {
            return Lay.error().msg("删除失败,人员不存在");
        }
    }

    //单人全部信息
    @ApiOperation("查询单个人员全部信息")
    @RequestMapping(value = "getById", method = RequestMethod.GET, name = "API-SELECT-PERSON")
    public Lay getOnePersonnelInformation(
            @ApiParam(value = "人员id", required = true) @RequestParam String id
    ) {
        PersonGetAllVo personGetAllVo = personnelInformationService.selectPerOne(id);
        return Lay.ok().data(personGetAllVo);
    }

    //更新人员基本
    @ApiOperation("更新人员信息")
    @RequestMapping(value = "update", method = RequestMethod.POST, name = "API-SELECT-PERSON")
    public Lay updatePersonnelInformation(
            @ApiParam(value = "人员信息", required = true)
            @RequestBody PersonGetAllVo personGetAllVo) {
        boolean b = personnelInformationService.updatePerById(personGetAllVo);
        if (b) {
            return Lay.ok().msg("修改成功");
        } else {
            return Lay.error().msg("数据不存在");
        }
    }

    //导入excel
    @ApiOperation("批量导入")
    @RequestMapping(value = "import", name = "API-IMPORT")
    public Lay batchImport(
            @ApiParam(value = "Excel", required = true)
            @RequestParam MultipartFile file, HttpServletRequest request) {
        try {
            Claims claims = JwtUtils.getMemberIdByJwtToken(request);
            InputStream inputStream = file.getInputStream();
            List<String> strings = personnelInformationService.batchImport(inputStream, (String) claims.get("uid"));
            return Lay.ok().data(strings);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new PaException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @ApiOperation("批量导入")
    @GetMapping(value = "import/progress", name = "API-IMPORT-PROGRESS")
    public Lay batchImportProgress(HttpServletRequest request) {
        Claims claims = JwtUtils.getMemberIdByJwtToken(request);
        return Lay.ok().data(JSONObject.parseObject((String) redisTemplate.opsForValue().get(claims.get("uid") + "_EXCEL")));
    }

    @ApiOperation("删除人员信息")
    @RequestMapping(value = "deleteList", method = RequestMethod.POST, name = "API-SELECT-PERSON")
    @Transactional
    public Lay deletePers(
            @ApiParam(value = "删除人员", required = true) @RequestBody Map<String, Object> idList
    ) {
        List<String> list = (List<String>) idList.get("ids");
        for (int i = 0; i < list.size(); i++) {
            String o = list.get(i);
            personnelInformationService.removePerById(o);
        }
        return Lay.ok().msg("删除成功");
    }

    @ApiOperation("自定义查询人员")
    @RequestMapping(value = "select/tree", method = RequestMethod.POST, name = "API-SELECT-PERSON")
    public Lay findAll(@RequestBody PersonQueryVo personQueryVo, HttpServletRequest request) {
        Claims claims = JwtUtils.getMemberIdByJwtToken(request);
        String regionalId = (String) claims.get("regional");
        String level = (String) claims.get("level");
        if (level.equals("2")) {
            regionalId = (String) claims.get("did");
        } else if (level.equals("3")) {
            regionalId = (String) claims.get("tid");
        } else if (level.equals("4")) {
            regionalId = (String) claims.get("rid");
        }
        IPage<PersonGetAllVo> allVoIPage = personnelInformationService.findAll(personQueryVo, level, regionalId);
        long total = allVoIPage.getTotal();
        List<PersonGetAllVo> records = allVoIPage.getRecords();
        return Lay.ok().count(total).data(records);
    }
}

