package org.tain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * TODO: FAIL
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/").setViewName("home");
		//registry.addViewController("/home").setViewName("home/home");
		//registry.addViewController("/login").setViewName("login/login");
		//registry.addViewController("/board").setViewName("board/form");
		//registry.addViewController("/board/list").setViewName("board/list");
		
		//WebMvcConfigurer.super.addViewControllers(registry);
	}
}
