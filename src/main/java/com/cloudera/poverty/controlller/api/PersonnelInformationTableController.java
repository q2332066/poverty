package com.cloudera.poverty.controlller.api;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudera.poverty.annotation.FmtType;
import com.cloudera.poverty.annotation.SystemLog;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.utils.CompeteUtils;
import com.cloudera.poverty.common.utils.ExceptionUtils;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.api.CareerPolicyTable;
import com.cloudera.poverty.entity.api.EnjoyHelpPolicyTable;
import com.cloudera.poverty.entity.api.IndustrialPolicyTable;
import com.cloudera.poverty.entity.api.PersonnelInformationTable;
import com.cloudera.poverty.entity.region.DistrictTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.region.TownshipTable;
import com.cloudera.poverty.entity.vo.PersonGetAllVo;
import com.cloudera.poverty.entity.vo.PersonQueryVo;
import com.cloudera.poverty.service.*;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @SystemLog(description = "新增人员[{name}]", sysParams = "personGetAllVo")
    @RequestMapping(value = "save", method = RequestMethod.POST, name = "API-SELECT-PERSON")
    public Lay savePersonnelInformation(
            @ApiParam(value = "人员基本信息")
            @RequestBody PersonGetAllVo personGetAllVo) {
        personnelInformationService.savePerson(personGetAllVo);
        return Lay.ok().msg("添加成功");
    }

    //连表删除
    @ApiOperation("删除人员信息")
    @SystemLog(description = "删除[{name}]的人员所有信息资料", formatType = FmtType.Bean, beanType = PersonnelInformationTableService.class)
    @PostMapping(value = "delete", name = "API-SELECT-PERSON")
    public Lay deletePersonnelInformation(
            @ApiParam(value = "删除人员", required = true) String ids
    ) {
        boolean b = personnelInformationService.removePerById(ids);
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
    @SystemLog(description = "更新人员[{name}]，更新内容为[{personGetAllVo}]", sysParams = "personGetAllVo")
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
    @SystemLog(description = "批量导入文件[文件名：{filename}, 大小为：{size}]", sysParams = "file")
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
    @SystemLog(description = "删除[{length}]个人员所有信息", formatType = FmtType.Bean, beanType = PersonnelInformationTableService.class)
    @Transactional
    public Lay deletePers(@ApiParam(value = "删除人员", required = true) @RequestParam(value = "ids[]") String[] ids) {
        for (String id : ids) {
            personnelInformationService.removePerById(id);
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

    @RequestMapping(value = "compete/bar", method = RequestMethod.POST, name = "API-SELECT-PERSON")
    public Lay findAllCompeteEcharts(@RequestBody PersonQueryVo personQueryVo, HttpServletRequest request) throws IllegalAccessException {
        personQueryVo.setPage(1L);
        personQueryVo.setLimit(100000L);

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
        int sumCar = 0, sumCarLen = 0;
        int sumEnj = 0, sumEnjLen = 0;
        int sumInd = 0, sumIndLen = 0;
        int sumPer = 0, sumPerLen = 0;
        List<PersonGetAllVo> records = allVoIPage.getRecords();
        for (PersonGetAllVo vo : records) {
            int sumCompete = 0, sumLength = 0;
            CareerPolicyTable cpt = new CareerPolicyTable();
            BeanUtils.copyProperties(vo, cpt);
            JSONObject obj = CompeteUtils.getCompete(cpt);
            vo.setCareerCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));
            sumCar += obj.getInteger("compete");
            sumCarLen += obj.getInteger("length");
            sumCompete += obj.getInteger("compete");
            sumLength += obj.getInteger("length");

            EnjoyHelpPolicyTable ehpt = new EnjoyHelpPolicyTable();
            BeanUtils.copyProperties(vo, ehpt);
            obj = CompeteUtils.getCompete(ehpt);
            vo.setEnjoyCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));
            sumCompete += obj.getInteger("compete");
            sumLength += obj.getInteger("length");
            sumEnj += obj.getInteger("compete");
            sumEnjLen += obj.getInteger("length");

            IndustrialPolicyTable ipt = new IndustrialPolicyTable();
            BeanUtils.copyProperties(vo, ipt);
            obj = CompeteUtils.getCompete(ipt);
            vo.setIndustCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));
            sumCompete += obj.getInteger("compete");
            sumLength += obj.getInteger("length");
            sumInd += obj.getInteger("compete");
            sumIndLen += obj.getInteger("length");

            PersonnelInformationTable pit = new PersonnelInformationTable();
            BeanUtils.copyProperties(vo, pit);
            obj = CompeteUtils.getCompete(pit);
            vo.setPersonCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));
            sumCompete += obj.getInteger("compete");
            sumLength += obj.getInteger("length");
            sumPer += obj.getInteger("compete");
            sumPerLen += obj.getInteger("length");

            vo.setSumCompete(sumCompete);
            vo.setSumLength(sumLength);
            vo.setDivCompete(new BigDecimal(sumCompete*100).divide(new BigDecimal(sumLength),4, RoundingMode.HALF_UP));
        }
        JSONObject rs = new JSONObject();
        List<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal(sumPer*100).divide(new BigDecimal(sumPerLen),2, RoundingMode.HALF_UP));
        values.add(new BigDecimal(sumCar*100).divide(new BigDecimal(sumCarLen),2, RoundingMode.HALF_UP));
        values.add(new BigDecimal(sumEnj*100).divide(new BigDecimal(sumEnjLen),2, RoundingMode.HALF_UP));
        values.add(new BigDecimal(sumInd*100).divide(new BigDecimal(sumIndLen),2, RoundingMode.HALF_UP));
        rs.put("names", Arrays.asList("人员信息", "就业创业", "已享受帮扶", "产业发展"));
        rs.put("values", values);
        return Lay.ok().data(rs);
    }

    @ApiOperation("查询人员完成度")
    @RequestMapping(value = "compete/tree", method = RequestMethod.POST, name = "API-SELECT-PERSON")
    public Lay findAllCompete(@RequestBody PersonQueryVo personQueryVo, HttpServletRequest request) throws IllegalAccessException {
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
        for (PersonGetAllVo vo : records) {
            CareerPolicyTable cpt = new CareerPolicyTable();
            BeanUtils.copyProperties(vo, cpt);
            JSONObject obj = CompeteUtils.getCompete(cpt);
            vo.setCareerCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));

            EnjoyHelpPolicyTable ehpt = new EnjoyHelpPolicyTable();
            BeanUtils.copyProperties(vo, ehpt);
            obj = CompeteUtils.getCompete(ehpt);
            vo.setEnjoyCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));

            IndustrialPolicyTable ipt = new IndustrialPolicyTable();
            BeanUtils.copyProperties(vo, ipt);
            obj = CompeteUtils.getCompete(ipt);
            vo.setIndustCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));

            PersonnelInformationTable pit = new PersonnelInformationTable();
            BeanUtils.copyProperties(vo, pit);
            obj = CompeteUtils.getCompete(pit);
            vo.setPersonCompete(String.format("%s/%s", obj.getString("compete"), obj.getString("length")));
        }
        return Lay.ok().count(total).data(records);
    }
}

