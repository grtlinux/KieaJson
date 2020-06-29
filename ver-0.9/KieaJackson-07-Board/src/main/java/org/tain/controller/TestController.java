package org.tain.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/test"})
@Slf4j
public class TestController {

	@GetMapping(value = {"", "/kang"})
	public String test(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
		Map<String,Object> map = new HashMap<>();
		map.put("name", "Kiea 강석");
		map.put("date", LocalDateTime.now());
		modelMap.put("data", map);
		
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
		
		return "test/kang";
	}
	
	@GetMapping(value = {"/kang1"})
	public String testKang1(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
		return "test/kang1";
	}

	@GetMapping(value = {"/kang2"})
	public String testKang2(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
		return "test/kang2";
	}

	@GetMapping(value = {"/kang3"})
	public String testKang3(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
		return "test/kang3";
	}
}
