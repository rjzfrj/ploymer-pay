package com.zyzf;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement 
//@MapperScan("com.demo.mapper")
public class Application {
//public class Application extends WebMvcConfigurerAdapter{
	
	
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		super.configureMessageConverters(converters);
//		FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
//		FastJsonConfig fastJsonConfig=new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		fastConverter.setFastJsonConfig(fastJsonConfig);
//		converters.add(fastConverter);
//		
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
