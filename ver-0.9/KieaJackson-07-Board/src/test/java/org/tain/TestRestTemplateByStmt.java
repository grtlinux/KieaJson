package org.tain;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.domain.Stmt;
import org.tain.utils.SkipSSLConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRestTemplateByStmt {

	private static boolean flag = true;
	
	/*
	 * TODO: GET, POST, PUT, DELETE
	 */
	public static void main(String[] args) throws Exception {
		if (flag) stmts_get_one();
		if (flag) stmts_get_list();
		if (flag) stmts_get_rest_to_list_of_stmt();
	}

	private static void stmts_get_one() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		if (flag) {
			String endpoint = "https://localhost:8443/api/stmts/3";
			
			SkipSSLConfig.skip();
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(endpoint)
					.build();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			log.info("KANG-20200607 >>>>> " + response);
			log.info("KANG-20200607 >>>>> response.getBody() : " + response.getBody());
		}
		
		stopWatch.stop();
		System.out.println(">>>>> " + stopWatch.prettyPrint());
	}

	private static void stmts_get_list() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		if (flag) {
			String endpoint = "https://localhost:8443/api/stmts";
			
			SkipSSLConfig.skip();
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(endpoint)
					.queryParam("name", "강석")
					.build();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			log.info("KANG-20200607 >>>>> " + response);
			log.info("KANG-20200607 >>>>> response.getBody() : " + response.getBody());
		}
		
		stopWatch.stop();
		System.out.println(">>>>> " + stopWatch.prettyPrint());
	}

	private static void stmts_get_rest_to_list_of_stmt() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		if (flag) {
			String endpoint = "https://localhost:8443/api/stmts";
			
			SkipSSLConfig.skip();
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(endpoint)
					.queryParam("name", "강석")
					.build();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			if (!flag) log.info("KANG-20200607 >>>>> " + response);
			if (!flag) log.info("KANG-20200607 >>>>> response.getBody() : " + response.getBody());
			
			///////////////////////////////////////////////////////
			
			String restData = response.getBody();
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			JsonNode jsonNode = objectMapper.readTree(restData);
			
			String jsonData = jsonNode.at("/_embedded/stmts").toPrettyString();
			if (flag) log.info("KANG-20200607 >>>>> jsonData: " + jsonData);
			
			List<Stmt> lstStmt = objectMapper.readValue(jsonData, new TypeReference<List<Stmt>>() {});
			for (Stmt stmt : lstStmt) {
				if (flag) log.info("KANG-20200607 >>>>> " + stmt);
			}
		}
		
		stopWatch.stop();
		System.out.println(">>>>> " + stopWatch.prettyPrint());
	}
}
