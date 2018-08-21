package com.giiso.submmited.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 
 * <p> Title: JSON转换工具类 </p>
 * <p> Description: </p>
 * @创建时间 2015年7月3日
 * @版本 2.0
 * @修改记录
 * <pre>
 * 版本   修改人    修改时间    修改内容描述
 * ----------------------------------------
 * 
 * </pre>
 */
public class JsonUtil {
	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 
     * getData
     * 功能描述: 获取基础类型数据，返回类型由clazz指定
     * 逻辑描述: 
     * @param obj  需要转换的object对象
     * @param clazz  指定的返回类型
     * @return <T>  给定的 {@code JSON} 字符串表示的指定的类型对象
     * @since Version 2.0
     */
    public static <T> T getData(Object obj, Class<T> clazz) {
    	String json = toJson(obj);
    	return fromJson(json, clazz);
    }
    
    /**
     * 
     * getData
     * 功能描述: 获取列表数据
     * 逻辑描述: 
     * @param obj  需要转换的object对象
     * @param type 要转换的目标类型 
     * @return List<T>  List<T>泛型对象
     * @since Version 2.0
     */
    public static <T> List<T> getData(Object obj, Type type) {
    	String json = toJson(obj);
    	return fromList(json, type);
    }
    
    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象
     * 
     * @param <T> 要转换的目标类型
     * @param json 给定的 {@code JSON} 字符串
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象
     * @since 2.0
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }
    
    /**
     * 
     * fromList
     * 功能描述: 将json字符串转换成List<T>泛型对象
     * 逻辑描述: 
     * @param json 给定的 {@code JSON} 字符串
     * @param type 要转换的目标类型
     * @return List<T>  List<T>泛型对象
     * @since Version 2.0
     */
    public static <T> List<T> fromList(String json, Type type) {
        return getGson().fromJson(json, type);
    }
    
    /**
     * 
     * toJson
     * 功能描述: 将object对象转换为json字符串
     * 逻辑描述: 
     * @param obj  需要转换的object对象
     * @return String  json字符串
     * @since Version 2.0
     */
    public static String toJson(Object obj) {
        return getGson().toJson(obj, obj.getClass());
    }
    
    /**
     * 
     * getGson
     * 功能描述: 获取Gson对象，并设置Date格式
     * 逻辑描述: 
     * @return Gson  Gson对象
     * @since Version 2.0
     */
    public static Gson getGson() {
    	GsonBuilder builder = new GsonBuilder();
    	// 格式化日期 
        builder.setDateFormat(DEFAULT_DATE_PATTERN);
        
        // 注册Double类型格式化适配器 
		builder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
		// 注册Long类型格式化适配器 
        builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
        // 注册Integer类型格式化适配器 
        builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
		
        return builder.create();
    }
    
	static class DoubleTypeAdapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
		@Override
		public JsonElement serialize(Double d, Type type,
                                     JsonSerializationContext context) {
			DecimalFormat format = new DecimalFormat("##0.00");
			String temp = format.format(d);
			JsonPrimitive pri = new JsonPrimitive(temp);
			return pri;
		}

		@Override
		public Double deserialize(JsonElement json, Type type,
                                  JsonDeserializationContext context) throws JsonParseException {
			return json.getAsDouble();
		}
	}
	
	static class IntegerTypeAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
		@Override
		public JsonElement serialize(Integer i, Type type,
                                     JsonSerializationContext context) {
			int temp = new Double(i).intValue();
			JsonPrimitive pri = new JsonPrimitive(temp);
			return pri;
		}

		@Override
		public Integer deserialize(JsonElement json, Type type,
                                   JsonDeserializationContext Context) throws JsonParseException {
			return new Double(json.getAsString()).intValue();
		}
	}
	
	static class LongTypeAdapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
		@Override
		public JsonElement serialize(Long l, Type type,
                                     JsonSerializationContext context) {
			int temp = new Double(l).intValue();
			JsonPrimitive pri = new JsonPrimitive(temp);
			return pri;
		}

		@Override
		public Long deserialize(JsonElement json, Type type,
                                JsonDeserializationContext context) throws JsonParseException {
			return new Double(json.getAsString()).longValue();
		}
	}
}