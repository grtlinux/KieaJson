package org.tain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tain.config.interceptor.TestInterceptor;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
		registry.addInterceptor(new TestInterceptor())
			.addPathPatterns("/test/**")
			.excludePathPatterns("/test/user/**", "/test/login/**");
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
