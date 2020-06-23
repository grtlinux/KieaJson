package org.tain.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tain.domain.Lightnet;
import org.tain.service.LightnetService;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/lightnet"})
@Slf4j
public class LightnetController {

	@Autowired
	private LightnetService lightnetService;
	
	@GetMapping(value = {"/list"})
	public String list(Pageable pageable, Model model) {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		model.addAttribute("lightnetList", this.lightnetService.findLightnetList(pageable));
		return "lightnet/list";
	}
	
	@GetMapping(value = {""})
	public String lightnet(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		Lightnet lightnet = this.lightnetService.findLightnetById(id);
		JsonNode jsonNode = new ObjectMapper().readTree(lightnet.getReqJsonData());
		lightnet.setReqJsonData(jsonNode.toPrettyString());
		model.addAttribute("lightnet", lightnet);
		return "lightnet/form";
	}
	
	@PostMapping(value = {"/exec_popup"})
	public String execPopup(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());

		String title = request.getParameter("execpopup_title");
		String reqUrl = request.getParameter("execpopup_req_url");
		String reqMethod = request.getParameter("execpopup_req_method");
		String reqJsonData = request.getParameter("execpopup_req_json_data");
		
		if (Flag.flag) {
			System.out.println("=====================================================");
			System.out.println("execpopup_title          : " + title);
			System.out.println("execpopup_req_url        : " + reqUrl);
			System.out.println("execpopup_req_method     : " + reqMethod);
			System.out.println("execpopup_req_json_data  : " + reqJsonData);
			System.out.println("=====================================================");
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("title", title);
		map.put("reqUrl", reqUrl);
		map.put("reqMethod", reqMethod);
		map.put("reqJsonData", reqJsonData);
		modelMap.put("lightnet", map);
		
		return "lightnet/exec_popup";
	}
}
