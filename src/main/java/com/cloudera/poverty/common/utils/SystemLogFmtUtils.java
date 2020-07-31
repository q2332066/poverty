package com.cloudera.poverty.common.utils;

import com.cloudera.poverty.annotation.FmtType;
import com.cloudera.poverty.base.config.BeanFactory;
import com.google.common.primitives.Longs;
import org.apache.commons.text.StringSubstitutor;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemLogFmtUtils {

    public static final StringSubstitutor sub = StringSubstitutor.createInterpolator();

    public static String getValue(FmtType fmtType, String desc, Class bean, String[] params, Object[] args, String[] sysParams) throws InvocationTargetException, IllegalAccessException {
        String rs = desc;
        Map<String, Object> obj = getMap(params, args, sysParams);
        if(fmtType == FmtType.Bean){
            if(args.length < 1){
                return "错误：不符合查找必要条件，方法必须有第一个参数";
            }

            Method m;
            try {
                m = bean.getInterfaces()[0].getDeclaredMethod("getById", Serializable.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return "错误：日志配置错误";
            }
            m.setAccessible(true);
            Object returnObj = null;
            if(args[0].getClass().isArray()){ //判断是否为数组
                obj.put("length", ((Object[])args[0]).length);
            } else {
                returnObj = m.invoke(BeanFactory.getBean(bean), args[0]);
                JSONObject o = new JSONObject(returnObj);
                if(o != null){
                    obj.putAll(o.toMap());
                }
            }
        }

        //最后替换数字
        MessageFormat mf = new MessageFormat(sub.replace(desc, obj, "{", "}"));
        rs = mf.format(args);
        return rs;
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args)
            throws Throwable {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                .unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
    }


    public static Map<String, Object> getMap(String[] params, Object[] args, String[] sysParams){
        Map<String, Object> obj = new HashMap<>();
        List pl = Arrays.asList(sysParams);
        for(int i = 0; i < params.length; i++){
            if(pl.contains(params[i])){
                if(args[i] instanceof MultipartFile){
                    obj.put("filename", ((MultipartFile) args[i]).getOriginalFilename());
                    obj.put("size", FileSizeUtils.convertFileSize(((MultipartFile) args[i]).getSize()));
                }
                obj.putAll(new JSONObject(args[i]).toMap());
            }
            obj.put(params[i], args[i]);
        }
        return obj;
    }

    public static void main(String[] args) {
        String desc = "我是{0}是{1}是是";
        MessageFormat mf = new MessageFormat(desc);
        System.out.println(mf.format(new Object[]{"john", Longs.tryParse("24"), "123", "12312"}));

        // Build map
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("animal", "quick brown fox");
        valuesMap.put("target", "lazy dog");
        valuesMap.put("target1", "lazy dog");
        String templateString = "The {animal} {1} jumped over the {target}. Oh this is {animal}. Date is {date:yyyy-MM-dd}  - {java:version}";

        // Build StringSubstitutor
        StringSubstitutor sub = new StringSubstitutor(valuesMap, "{", "}");
        sub.setEnableSubstitutionInVariables(true);
        // Replace
        String resolvedString = sub.replace(templateString);
        System.out.println(StringSubstitutor.replaceSystemProperties(resolvedString));

        final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        interpolator.setEnableSubstitutionInVariables(true); // Allows for nested $'s.
//        final String text = interpolator.replace(
//                "Base64 Decoder:        ${base64Decoder:SGVsbG9Xb3JsZCE=}\n"
//                + "Base64 Encoder:        ${base64Encoder:HelloWorld!}\n"
//                + "Java Constant:         ${const:java.awt.event.KeyEvent.VK_ESCAPE}\n"
//                + "Date:                  ${date:yyyy-MM-dd}\n" + "DNS:                   ${dns:address|apache.org}\n"
//                + "Environment Variable:  ${env:USERNAME}\n"
//                + "File Content:          ${file:UTF-8:src/test/resources/document.properties}\n"
//                + "Java:                  ${java:version}\n" + "Localhost:             ${localhost:canonical-name}\n"
//                + "Properties File:       ${properties:src/test/resources/document.properties::mykey}\n"
//                + "Resource Bundle:       ${resourceBundle:org.example.testResourceBundleLookup:mykey}\n"
//                + "Script:                ${script:javascript:3 + 4}\n" + "System Property:       ${sys:user.dir}\n"
//                + "URL Decoder:           ${urlDecoder:Hello%20World%21}\n"
//                + "URL Encoder:           ${urlEncoder:Hello World!}\n"
//                + "URL Content (HTTP):    ${url:UTF-8:http://www.apache.org}\n"
//                + "URL Content (HTTPS):   ${url:UTF-8:https://www.apache.org}\n"
//                + "URL Content (File):    ${url:UTF-8:file:///${sys:user.dir}/src/test/resources/document.properties}\n"
//                + "XML XPath:             ${xml:src/test/resources/document.xml:/root/path/to/node}\n"
//        );
//        System.out.println(text);
    }
}
