package com.seckill.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Json的序列化和反序列化工具类  jackson-mapper-asl
 * @author mengxin
 */

public class JsonUtil {

        private static ObjectMapper mapper=new ObjectMapper();

        static{
            //对象的所有字段全部录入 NON_NULL 对象为空的排除
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            //取消默认转换timestamps
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
            //忽略空bean转json的错误
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
            //反序列化时，JSON字符串中字段出现，java对象字段不存在的错误
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

            //所有的时间格式统一为
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }


    /**
     * 序列化，将对象转换为字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2Str(T obj){
            if(obj==null)
                return null;
            try {
                return obj instanceof String?(String)obj:mapper.writeValueAsString(obj);
            }catch (Exception e){
                return null;
            }
        }

    /**
     * 完美的序列化，将对象格式化为字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StrPretty(T obj){
        if(obj==null)
            return null;
        try {
            return obj instanceof String?(String)obj:mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 将字符串转换为对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T str2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str)||clazz==null)
            return null;
        try {
            return String.class.equals(clazz)?(T)str:mapper.readValue(str,clazz);
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 高可用反序列化方法
     * 传入参数的方式：str2Obj(str,new TypeReference<List<User>>(){})
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T str2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str)||typeReference==null)
            return null;
        try {
            return (T)(typeReference.getType().equals(String.class)?str:mapper.readValue(str,typeReference));
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 高可用反序列化方法
     * @param str
     * @param collectionClass   集合类型
     * @param elementClazz  实体类型
     * @param <T>
     * @return
     */
    public static <T> T str2Obj(String str, Class<?> collectionClass,Class<?>... elementClazz){
        try {
            JavaType javaType=mapper.getTypeFactory().constructParametricType(collectionClass,elementClazz);
            return mapper.readValue(str,javaType);
        }catch (Exception e){
            return null;
        }
    }

}
