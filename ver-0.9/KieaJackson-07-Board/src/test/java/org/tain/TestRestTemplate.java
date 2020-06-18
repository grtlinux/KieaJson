package org.tain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.SkipSSLConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRestTemplate {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		if (flag) index_get();
		if (!flag) test_get();
	}

	private static void index_get() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		if (flag) {
			String endpoint;
			endpoint = "https://localhost:8443/";
			
			SkipSSLConfig.skip();
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(endpoint)
					.build();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			if (!flag) log.info("KANG-20200607 >>>>> response : " + response);
			if (flag) log.info("KANG-20200607 >>>>> response.getBody() : " + response.getBody());
		}
		
		stopWatch.stop();
		System.out.println(">>>>> " + stopWatch.prettyPrint());
	}

	private static void test_get() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		if (flag) {
			String endpoint;
			endpoint = "https://localhost:8443/test/";
			endpoint = "https://localhost:8443/test/test";
			
			SkipSSLConfig.skip();
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(endpoint)
					.build();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);
			if (!flag) log.info("KANG-20200607 >>>>> response : " + response);
			if (flag) log.info("KANG-20200607 >>>>> response.getBody() : " + response.getBody());
		}
		
		stopWatch.stop();
		System.out.println(">>>>> " + stopWatch.prettyPrint());
	}
}
