package cn.tedu.ttms.common.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/**springmvc 在将日期对象转换为字符串时,
 * 一般默认会转换为长整型,假如我们需要自己
 * 定义格式,通常会写一个类继承JsonSerializer
 * 假如在对象需要将日期转换为我们需要的格式
 * 则在泛型中指定Date
 * 可以在对应的实体对象的get方法中使用注解
 * @JsonSerialize(using=JsonDateTypeConvert.class)
 * */
public class JsonDateTypeConvert extends JsonSerializer<Date>{
/**
 * @param value是要转换的日期
 * @param gen 为一个json对象生成器
 */
	@Override
	public void serialize(Date value, 
			JsonGenerator gen, 
			SerializerProvider serializers)
			throws IOException, 
			JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String dataStr = sdf.format(value);
		//将此字符串写入到json对象中
		gen.writeString(dataStr);
	}

}
