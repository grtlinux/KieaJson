package org.tain.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.AccessToken;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.SkipSSLConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/rest/lightnet"})
@Slf4j
public class LightnetRestController {

	/*
	public String rest01() {
		return "hello, world!!!";
	}
	
	public ObjectVO rest02() {
		return (ObjectVO) obj;
	}
	
	public List<Hello> rest03() {
		return (List<Hello>) ret;
	}
	
	public Map<String,Object> rest04() {
		return (Map<>) map;
	}
	
	public ResponseEntity<Map<Stirng,Object>> rest05() {
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	//public ResponseEntity<Map<String,Object>> auth(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
	//public ResponseEntity<?> auth(@RequestParam Map<String,Object> param, HttpServletRequest request) {
	//public ResponseEntity<?> auth(HttpEntity<String> httpEntity) {

		// return new ResponseEntity<>(map, HttpStatus.OK);
		//return ResponseEntity.ok(map);
		return new ResponseEntity<>("success", HttpStatus.OK);

	*/
	
	@PostMapping(value = {"/auth"})
	public ResponseEntity<?> auth(HttpEntity<String> _httpEntity) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		ResponseEntity<String> response = null;
		
		Map<String,Object> map = null;
		Map<String,Object> mapReq = null;
		if (Flag.flag) {
			System.out.println("=====================================================");
			System.out.println("param    : " + _httpEntity.getBody());
			System.out.println("=====================================================");
			map = new ObjectMapper().readValue(_httpEntity.getBody(), new TypeReference<Map<String,Object>>(){});
			System.out.println("=====================================================");
			System.out.println("reqJsonData    : " + (String)map.get("reqJsonData"));
			System.out.println("=====================================================");
			mapReq = new ObjectMapper().readValue((String)map.get("reqJsonData"), new TypeReference<Map<String,Object>>(){});
		}
		
		if (Flag.flag) {
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("clientId", (String) mapReq.get("clientId"));
			parameters.put("secret", (String) mapReq.get("secret"));
			HttpEntity<Map<String,Object>> request = new HttpEntity<>(parameters, headers);
			
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.exchange((String)map.get("reqUrl"), HttpMethod.POST, request, String.class);
			
			//response.getStatusCodeValue();
			//response.getStatusCode();
			//response.getHeaders();
			//response.getBody();
			String accessToken = response.getHeaders().get("AccessToken").get(0);
			AccessToken.set(accessToken);
			System.out.println("=====================================================");
			System.out.println(">>>>> AccessToken: " + AccessToken.get());
			System.out.println(">>>>> response: " + response);
			System.out.println("=====================================================");
			
			if (Flag.flag) {
				// Pretty Print
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				JsonNode jsonNode = objectMapper.readTree(response.getBody());
				//String json = jsonNode.at("/").toPrettyString();
				String json = jsonNode.toPrettyString();
				System.out.println(">>>>> json: " + json);
			}
		}
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
	
	@PostMapping(value = {"/list"})
	public ResponseEntity<?> list(HttpEntity<String> _httpEntity) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		ResponseEntity<String> response = null;
		
		Map<String,Object> map = null;
		//Map<String,Object> mapReq = null;
		if (Flag.flag) {
			System.out.println("=====================================================");
			System.out.println("param    : " + _httpEntity.getBody());
			System.out.println("=====================================================");
			map = new ObjectMapper().readValue(_httpEntity.getBody(), new TypeReference<Map<String,Object>>(){});
			System.out.println("=====================================================");
			System.out.println("reqJsonData    : " + (String)map.get("reqJsonData"));
			System.out.println("=====================================================");
			//mapReq = new ObjectMapper().readValue((String)map.get("reqJsonData"), new TypeReference<Map<String,Object>>(){});
		}
		
		if (Flag.flag) {
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + AccessToken.get());
			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			
			/*
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("operatorCode", "");
			parameters.put("offset", "1");
			parameters.put("limit", "20");
			//parameters.put("from", "");
			//parameters.put("to", "");
			*/
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl((String)map.get("reqUrl"))
					.queryParam("operatorCode", "")
					.queryParam("offset", "1")
					.queryParam("limit", "20")
					//.queryParam("from", "")
					//.queryParam("to", "")
					.build(false);
			
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.exchange(builder.toString(), HttpMethod.GET, httpEntity, String.class);
			System.out.println(">>>>> response: " + response);
			
			if (Flag.flag) {
				// Pretty Print
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				JsonNode jsonNode = objectMapper.readTree(response.getBody());
				//String json = jsonNode.at("/").toPrettyString();
				String json = jsonNode.toPrettyString();
				System.out.println(">>>>> json: " + json);
			}
		}
		
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
	
	@PostMapping(value = {"/detail"})
	public ResponseEntity<?> detail(HttpEntity<String> _httpEntity) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		ResponseEntity<String> response = null;
		
		Map<String,Object> map = null;
		//Map<String,Object> mapReq = null;
		if (Flag.flag) {
			System.out.println("=====================================================");
			System.out.println("param    : " + _httpEntity.getBody());
			System.out.println("=====================================================");
			map = new ObjectMapper().readValue(_httpEntity.getBody(), new TypeReference<Map<String,Object>>(){});
			System.out.println("=====================================================");
			System.out.println("reqJsonData    : " + (String)map.get("reqJsonData"));
			System.out.println("=====================================================");
			//mapReq = new ObjectMapper().readValue((String)map.get("reqJsonData"), new TypeReference<Map<String,Object>>(){});
		}
		
		if (Flag.flag) {
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + AccessToken.get());
			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl((String)map.get("reqUrl"))
					.queryParam("sourceCountry", "KOR")
					.queryParam("destinationCountry", "KHM")
					.queryParam("destinationOperatorCode", "lyhour")
					.queryParam("withdrawableAmount", "1.500")
					.queryParam("transactionCurrency", "USD")
					.queryParam("deliveryMethod", "cash")
					.build(false);
			
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.exchange(builder.toString(), HttpMethod.GET, httpEntity, String.class);
			System.out.println(">>>>> response: " + response);
			
			if (Flag.flag) {
				// Pretty Print
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				JsonNode jsonNode = objectMapper.readTree(response.getBody());
				//String json = jsonNode.at("/").toPrettyString();
				String json = jsonNode.toPrettyString();
				System.out.println(">>>>> json: " + json);
			}
		}
		
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
	
	@PostMapping(value = {"/validate"})
	public ResponseEntity<?> validate(HttpEntity<String> _httpEntity) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		String response = null;
		
		Map<String,Object> map = null;
		//Map<String,Object> mapReq = null;
		if (Flag.flag) {
			System.out.println("=====================================================");
			System.out.println("param    : " + _httpEntity.getBody());
			System.out.println("=====================================================");
			map = new ObjectMapper().readValue(_httpEntity.getBody(), new TypeReference<Map<String,Object>>(){});
			System.out.println("=====================================================");
			System.out.println("reqJsonData    : " + (String)map.get("reqJsonData"));
			System.out.println("=====================================================");
			//mapReq = new ObjectMapper().readValue((String)map.get("reqJsonData"), new TypeReference<Map<String,Object>>(){});
		}
		
		if (Flag.flag) {
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + AccessToken.get());
			
			StringBuffer sb = new StringBuffer();
			sb.append("{\n");
			sb.append("    \"receiver\": {\n");
			sb.append("        \"firstName\": \"BZIDWB\",\n");
			sb.append("        \"lastName\": \"USBARH\",\n");
			sb.append("        \"bankCode\": \"SICOTHBK\",\n");
			sb.append("        \"accountId\": \"receiverAccountId\"\n");
			sb.append("    },\n");
			sb.append("    \"deliveryMethod\": \"account_deposit\",\n");
			sb.append("    \"sender\": {\n");
			sb.append("        \"firstName\": \"SKANTE\",\n");
			sb.append("        \"lastName\": \"MYCYBX\",\n");
			sb.append("        \"address\": {\n");
			sb.append("            \"address\": \"senderAddress\",\n");
			sb.append("            \"city\": \"senderCity\",\n");
			sb.append("            \"countryCode\": \"THA\",\n");
			sb.append("            \"postalCode\": \"senderZipCode\"\n");
			sb.append("        },\n");
			sb.append("        \"nationalityCountryCode\": \"KOR\",\n");
			sb.append("        \"mobilePhone\": {\n");
			sb.append("            \"number\": \"881111111\",\n");
			sb.append("            \"countryCode\": \"66\"\n");
			sb.append("        },\n");
			sb.append("        \"idNumber\": \"idNumber\"\n");
			sb.append("    },\n");
			sb.append("    \"destination\": {\n");
			sb.append("        \"country\": \"THA\",\n");
			sb.append("        \"receive\": {\n");
			sb.append("            \"currency\": \"THB\"\n");
			sb.append("        },\n");
			sb.append("        \"operatorCode\": \"scb\"\n");
			sb.append("    },\n");
			sb.append("    \"remark\": \"This is SCB test remark\",\n");
			sb.append("    \"source\": {\n");
			sb.append("        \"country\": \"KOR\",\n");
			sb.append("        \"send\": {\n");
			sb.append("            \"amount\": \"2\",\n");
			sb.append("            \"currency\": \"USD\"\n");
			sb.append("        },\n");
			sb.append("        \"transactionId\": \"AUTOMATESCB12345\"\n");
			sb.append("    },\n");
			sb.append("    \"saveReport\": \"TRUE\"\n");
			sb.append("}\n");

			HttpEntity<String> httpEntity = new HttpEntity<>(sb.toString(), headers);
			
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.postForObject((String)map.get("reqUrl"), httpEntity, String.class);
			
			System.out.println(">>>>> response: " + response);
			
			if (Flag.flag) {
				// Pretty Print
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				JsonNode jsonNode = objectMapper.readTree(response);
				//String json = jsonNode.at("/").toPrettyString();
				String json = jsonNode.toPrettyString();
				System.out.println(">>>>> json: " + json);
			}
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = {"/commit"})
	public ResponseEntity<?> commit(HttpEntity<String> _httpEntity) throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
		Map<String,Object> map = null;
		Map<String,Object> mapReq = null;
		if (Flag.flag) {
			System.out.println("=====================================================");
			System.out.println("param    : " + _httpEntity.getBody());
			System.out.println("=====================================================");
			map = new ObjectMapper().readValue(_httpEntity.getBody(), new TypeReference<Map<String,Object>>(){});
			System.out.println("=====================================================");
			System.out.println("reqJsonData    : " + (String)map.get("reqJsonData"));
			System.out.println("=====================================================");
			mapReq = new ObjectMapper().readValue((String)map.get("reqJsonData"), new TypeReference<Map<String,Object>>(){});
		}
		
		/*
		String response = null;
		if (Flag.flag) {
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + AccessToken.get());
			
			String reqUrl = (String)map.get("reqUrl");
			String reqJsonData = (String)map.get("reqJsonData") + "\n";
			System.out.println("=====================================================");
			System.out.println("reqUrl      : " + reqUrl);
			System.out.println("reqJsonData : " + reqJsonData);
			System.out.println("=====================================================");

			HttpEntity<String> httpEntity = new HttpEntity<>(reqJsonData, headers);
			
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.postForObject(reqUrl, httpEntity, String.class);
			
			System.out.println(">>>>> response: " + response);
			
			if (Flag.flag) {
				// Pretty Print
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				JsonNode jsonNode = objectMapper.readTree(response);
				//String json = jsonNode.at("/").toPrettyString();
				String json = jsonNode.toPrettyString();
				System.out.println(">>>>> json: " + json);
			}
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		*/
		
		ResponseEntity<String> response = null;
		if (Flag.flag) {
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + AccessToken.get());
			
			String reqUrl = (String)map.get("reqUrl");
			String reqJsonData = (String)map.get("reqJsonData") + "\n";
			System.out.println("=====================================================");
			System.out.println("reqUrl      : " + reqUrl);
			System.out.println("reqJsonData : " + reqJsonData);
			System.out.println("=====================================================");
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("transactionId", (String) mapReq.get("transactionId"));
			HttpEntity<Map<String,Object>> request = new HttpEntity<>(parameters, headers);
			
			SkipSSLConfig.skip();

			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.exchange(reqUrl, HttpMethod.POST, request, String.class);
			
			//response.getStatusCodeValue();
			//response.getStatusCode();
			//response.getHeaders();
			//response.getBody();
			System.out.println("=====================================================");
			System.out.println(">>>>> response: " + response);
			System.out.println("=====================================================");
			
			if (Flag.flag) {
				// Pretty Print
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				JsonNode jsonNode = objectMapper.readTree(response.getBody());
				//String json = jsonNode.at("/").toPrettyString();
				String json = jsonNode.toPrettyString();
				System.out.println(">>>>> json: " + json);
			}
		}
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
}
