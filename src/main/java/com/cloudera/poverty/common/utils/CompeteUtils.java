package com.cloudera.poverty.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.cloudera.poverty.entity.api.EnjoyHelpPolicyTable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CompeteUtils {

    public static List<String> ignores = Arrays.asList("personnelInformationId", "iId", "eId", "caId", "serialVersionUID");

    /**
     * 获取当前对象的完成度
     * @param o
     * @return
     * @throws IllegalAccessException
     */
    public static JSONObject getCompete(Object o) throws IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        int ignore = 0, nullcount = 0, size = fields.length;
        for(Field field : fields){
            //忽略ID属性
            if(ignores.contains(field.getName())){
                ignore++;
                continue;
            }

            //暴力获取
            field.setAccessible(true);
            Object fieldValue = field.get(o);
            if(fieldValue == null){
                //为空计算
                nullcount++;
            }
        }
        JSONObject rs = new JSONObject();
        rs.put("length", size - ignore);
        rs.put("compete", size - nullcount - ignore);
        return rs;
    }

    public static void main(String[] args) throws IllegalAccessException {
        EnjoyHelpPolicyTable ehpt = new EnjoyHelpPolicyTable();
        ehpt.setRangerCompensation(1.2);
        System.out.println(getCompete(ehpt));
    }
}
