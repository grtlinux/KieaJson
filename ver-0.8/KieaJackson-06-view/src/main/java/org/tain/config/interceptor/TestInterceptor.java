package org.tain.config.interceptor;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("KANG-20200616 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
