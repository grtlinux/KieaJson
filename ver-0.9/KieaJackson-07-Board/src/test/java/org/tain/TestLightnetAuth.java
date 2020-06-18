package org.tain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.SkipSSLConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLightnetAuth {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		SkipSSLConfig.skip();
		
		if (!flag) test01();
		if (!flag) test02();
		
		if (!flag) exec01();
		if (flag) {
			for (int i=0; i < 10000 && i == 0; i++) {
				System.out.println("################################# (" + i + ") ###########################");
				try {
					exec01();
				} catch (Exception e) {
					System.err.println(">>>>> " + e.getMessage());
				}
				try { Thread.sleep(60 * 1000); } catch (InterruptedException e) {}
			}
		}
	}

	private static final String POST_AUTH_ENDPOINT_URL = "https://test-public.lightnetapis.io/v1/auth";
	
	private static void exec01() throws Exception {
		if (flag) {
			System.out.println("----------------------------------------");
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("clientId", "pkey_tUsjZ1aL8UhvJnNibssfEGo6Y4MhSzXT");
			parameters.put("secret", "skey_D1ZL5MW4bKW7clFW2Vz3jH8sm2k7FUfWiu5wh1aL8Uivo6RMNOa74wxfSYo5ylmk");
			
			HttpEntity<Map<String,Object>> request = new HttpEntity<>(parameters, headers);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(POST_AUTH_ENDPOINT_URL, HttpMethod.POST, request, String.class);
			
			//response.getStatusCodeValue();
			//response.getStatusCode();
			//response.getHeaders();
			//response.getBody();
			
			System.out.println(">>>>> " + response.getHeaders().get("AccessToken").get(0));
			System.out.println(">>>>> " + response);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static void test01() throws Exception {
		if (flag) {
		}
	}
	
	// REF: https://vmpo.tistory.com/27
	@SuppressWarnings({ "unused", "rawtypes" })
	//@RestController
	@GetMapping("/abcdef")
	private static String test02() throws Exception {
		if (flag) {
			Map<String, Object> result = new HashMap<>();
			String jsonInString = "";
			
			try {
				HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
				factory.setConnectTimeout(5000);
				factory.setReadTimeout(5000);
				RestTemplate restTemplate = new RestTemplate(factory);
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<?> entity = new HttpEntity<>(headers);
				
				String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
				UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"key=430156241533f1d058c603178cc3ca0e&targetDt=20120101").build();
				
				ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
				result.put("statusCode", resultMap.getStatusCodeValue());
				result.put("header", resultMap.getHeaders());
				result.put("body", resultMap.getBody());
				
				LinkedHashMap lm = (LinkedHashMap)resultMap.getBody().get("boxOfficeResult");
				List<Map> dboxoffList = (ArrayList<Map>)lm.get("dailyBoxOfficeList");
				LinkedHashMap mnList = new LinkedHashMap<>();
				for (Map obj : dboxoffList) {
					mnList.put(obj.get("rnum"),obj.get("movieNm"));
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				jsonInString = objectMapper.writeValueAsString(resultMap.getBody());
			} catch (HttpClientErrorException | HttpServerErrorException e) {
				result.put("statusCode", e.getRawStatusCode());
				result.put("body", e.getStatusText());
				System.out.println("----------------");
				System.out.println(e.toString());
			} catch (Exception e) {
				result.put("statusCode", "9999");
				result.put("body", "exception Error");
				System.out.println(e.toString());
			}
			
			return jsonInString;
		}
		
		if (!flag) {
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000);
			factory.setReadTimeout(5000);
			RestTemplate restTemplate = new RestTemplate(factory);
			
			HttpHeaders header = new HttpHeaders();
			HttpEntity<?> entity = new HttpEntity<>(header);
			
			String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
			UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"key=430156241533f1d058c603178cc3ca0e&targetDt=20120101").build();

			ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
			int statusCodeValue = resultMap.getStatusCodeValue();
			HttpStatus httpStatus = resultMap.getStatusCode();
			HttpHeaders httpHeaders = resultMap.getHeaders();
			Map<?, ?> body = resultMap.getBody();
			Map<?, ?> office = (LinkedHashMap<?,?>) body.get("Office");
			
			// jsonInString = mapper.writeValueAsString(mapObject);
			
			/*
			HttpStatus httpStatus = resultMap.getStatusCode();
			httpStatus.getReasonPhrase();
			httpStatus.is1xxInformational()
			httpStatus.is2xxSuccessful()
			httpStatus.is3xxRedirection()
			httpStatus.is4xxClientError()
			httpStatus.is5xxServerError()
			*/
			
			/*
			HttpHeaders httpHeaders = resultMap.getHeaders();
			httpHeaders.add(headerName, headerValue);
			*/
		}
		
		if (!flag) {
			HttpClient httpClient = HttpClientBuilder.create()
					.setMaxConnTotal(200)
					.setMaxConnPerRoute(20)
					.build();
		}
		
		if (!flag) {
			HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
			HttpClient httpClient = HttpClientBuilder.create()
					.setMaxConnTotal(200)
					.setMaxConnPerRoute(20)
					.build();
			httpRequestFactory.setHttpClient(httpClient);
			RestTemplate restTemplate = new RestTemplateBuilder()
					.setConnectTimeout(Duration.ofSeconds(5))
					.setReadTimeout(Duration.ofSeconds(5))
					.requestFactory(()-> httpRequestFactory)
					.build();
			// return restTemplate;
		}
		
		if (!flag) {
			RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		}
		
		return null;
	}
	
	// requestSendRest
	public ResponseEntity<String> requestSendRest(String restUrl, HttpMethod httpMethod, Map<String, Object> parameters) throws Exception {
		if (log.isInfoEnabled()) {
			log.info("==== requestSendRest ====");
			log.info("restUrl    : " + restUrl);
			log.info("httpMethod : " + httpMethod);
			log.info("parameters : " + parameters.toString());
		}
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<String> response = null;
		
		if (httpMethod == HttpMethod.GET) {
			// GET method
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(restUrl)
					.queryParam("key1", parameters.get("key1"))
					.queryParam("key2", parameters.get("key2"))
					.queryParam("key3", parameters.get("key3"))
					.build(false);
			response = restTemplate.exchange(builder.toString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
		} else if (httpMethod == HttpMethod.POST) {
			// POST method
			HttpEntity<Map<String, Object>> request = new HttpEntity<>(parameters, headers);
			response = restTemplate.exchange(restUrl,  HttpMethod.POST, request, String.class);
		}
		
		response.getStatusCodeValue();  // int
		response.getStatusCode();       // HttpStatus
		response.getHeaders();          // HttpHeaders
		response.getBody();             // String
		
		return response;
	}
	
	// CustomRestTemplate
	public RestTemplate getCustomRestTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(2000);
		httpRequestFactory.setReadTimeout(3000);
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(200)
				.setMaxConnPerRoute(20)
				.build();
		httpRequestFactory.setHttpClient(httpClient);
		return new RestTemplate(httpRequestFactory);
	}
}
