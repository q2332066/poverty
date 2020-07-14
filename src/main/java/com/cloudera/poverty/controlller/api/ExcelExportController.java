package com.cloudera.poverty.controlller.api;

import com.alibaba.excel.EasyExcel;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.vo.PersonQueryVo;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;
import com.cloudera.poverty.service.PersonnelInformationTableService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/api/est/person")
@CrossOrigin//(allowCredentials = "true", allowedHeaders = "*")//跨域
@Slf4j
public class ExcelExportController {

    @Autowired
    private PersonnelInformationTableService personnelInformationService;

    @GetMapping("/export")
    public void export(PersonQueryVo personQueryVo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        personQueryVo.setPage(1L);
        personQueryVo.setLimit(100000L);
//        Claims claims = JwtUtils.getMemberIdByJwtToken(request);
        String regionalId = personQueryVo.getRegional();
        String level = personQueryVo.getLevel();

        if("2".equals(level)){
            regionalId = personQueryVo.getDid();
        } else if("3".equals(level)) {
            regionalId = personQueryVo.getTid();
        } else if("4".equals(level)) {
            regionalId = personQueryVo.getRid();
        }

        Integer type = personQueryVo.getType();
        List<PersonAllVo> records = personnelInformationService.findAllExcel(personQueryVo,level,regionalId).getRecords();
//        String p = ClassUtils.getDefaultClassLoader().getResource("templates").getPath();
//        String path = this.getClass().getClassLoader().getResource("templates").getPath();
//        String path = new ClassPathResource("templates/").getPath();
//        System.out.println(path);
        String templateFileName = "templates/";
        String fileName = "";
        if(type == 0){
            templateFileName += "allpeople.xlsx";
            fileName = "所有人员信息";
        } else if(type == 1){
            templateFileName += "azdqyry.xlsx";
            fileName = "安置点搬迁人员信息";
        } else if(type == 2){
            templateFileName += "anzbqh.xlsx";
            fileName = "安置点搬迁户信息";
        } else if(type == 3){
            templateFileName += "nyfzzc.xlsx";
            fileName = "农业发展政策保障";
        } else if(type == 4){
            templateFileName += "yxsfb.xlsx";
            fileName = "已享受帮扶政策";
        }

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), PersonAllVo.class).withTemplate(new ClassPathResource(templateFileName).getInputStream()).sheet("数据").doFill(records);

        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
//        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(records);
    }
}
