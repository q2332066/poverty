package com.cloudera.poverty.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSONObject;
import com.cloudera.poverty.entity.vo.excel.PersonAllVo;
import com.cloudera.poverty.service.PersonAllVoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
public class ExcelSubjectDataListener extends AnalysisEventListener<PersonAllVo> {

    private String redisKey;
    List<String> msgList=new ArrayList<>();
    List<PersonAllVo> persons = new ArrayList<>();
    private int totalSize = 0;
    private int totalError = 0;
    private String uid;
    private PersonAllVoService personAllVoService;
    private RedisTemplate<String, Serializable> redisTemplate;

    public ExcelSubjectDataListener(PersonAllVoService personAllVoService, RedisTemplate<String, Serializable> redisTemplate, String uid) {
        this.redisTemplate = redisTemplate;
        this.personAllVoService=personAllVoService;
        this.uid = uid;
        this.redisKey = uid + "_EXCEL";
    }

    public String getRedisKey() {
        return redisKey;
    }

    @Override
    public void invoke(PersonAllVo personAllVo, AnalysisContext analysisContext) {
        persons.add(personAllVo);
        totalSize++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析完成");
        saveData();
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception{

        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            Integer rowIndex = excelDataConvertException.getRowIndex();
            Integer columnIndex = excelDataConvertException.getColumnIndex();
            String msg="第"+rowIndex+"行，第"+columnIndex+"列解析异常"+exception.getMessage();
            msgList.add(msg);
            totalError++;
            totalSize++;
        }

    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        int i = 1;
        for(PersonAllVo person : this.persons){
            this.personAllVoService.savePerson(person);
            JSONObject o = new JSONObject();
            o.put("total", totalSize);
            o.put("current", i);
            o.put("error", totalError);
            this.redisTemplate.opsForValue().set(redisKey, o.toJSONString(), Duration.ofHours(2L));
            i++;
        }
    }
    private Integer getIdCard(String idCard){
        Integer integer = personAllVoService.countPerson(idCard);
        return integer;
    }
    public List<String> massage(){
        return this.msgList;
    }

}
