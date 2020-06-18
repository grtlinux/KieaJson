package org.tain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.CurrentInfo;
import org.tain.utils.SkipSSLConfig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestLightnetWithEps {
	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		
		SkipSSLConfig.skip();

		if (flag) auth();
		sleep(3);
		if (flag) list();
		sleep(3);
		if (flag) detail();
		sleep(3);
		if (flag) validate();
	}

	private static void sleep(int seconds) {
		try { Thread.sleep(seconds * 1000); } catch (InterruptedException e) {}
	}
	
	/*
	 * POST: https://test-public.lightnetapis.io/v1/auth
	 */
	private static final String POST_AUTH_ENDPOINT_URL = "https://test-public.lightnetapis.io/v1/auth";
	
	private static String accessToken = "qBl1ayd5nF9oX8xg5kSsHyER7pPj";
	
	private static void auth() throws Exception {
		if (flag) {
			System.out.println("\n\n############### (" + CurrentInfo.get() + ") ###########");
			
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
			accessToken = response.getHeaders().get("AccessToken").get(0);
			System.out.println(">>>>> AccessToken: " + accessToken);
			System.out.println(">>>>> response: " + response);
			
			if (flag) {
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
	}

	/*
	 * GET: https://test-public.lightnetapis.io/v1/remittances
	 */
	private static final String GET_LIST_ENDPOINT_URL = "https://test-public.lightnetapis.io/v1/remittances";
	
	private static void list() throws Exception {
		if (flag) {
			System.out.println("\n\n############### (" + CurrentInfo.get() + ") ########### " + accessToken);
			
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + accessToken);
			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			
			/*
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("operatorCode", "");
			parameters.put("offset", "1");
			parameters.put("limit", "20");
			//parameters.put("from", "");
			//parameters.put("to", "");
			*/
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(GET_LIST_ENDPOINT_URL)
					.queryParam("operatorCode", "")
					.queryParam("offset", "1")
					.queryParam("limit", "20")
					//.queryParam("from", "")
					//.queryParam("to", "")
					.build(false);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(builder.toString(), HttpMethod.GET, httpEntity, String.class);
			System.out.println(">>>>> response: " + response);
			
			if (flag) {
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
	}

	/*
	 * GET: https://test-public.lightnetapis.io/v1/remittances.detail
	 */
	private static final String GET_DETAIL_ENDPOINT_URL = "https://test-public.lightnetapis.io/v1/remittances.detail";
	
	private static void detail() throws Exception {
		if (flag) {
			System.out.println("\n\n############### (" + CurrentInfo.get() + ") ########### " + accessToken);
			
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + accessToken);
			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(GET_DETAIL_ENDPOINT_URL)
					.queryParam("sourceCountry", "KOR")
					.queryParam("destinationCountry", "KHM")
					.queryParam("destinationOperatorCode", "lyhour")
					.queryParam("withdrawableAmount", "1.500")
					.queryParam("transactionCurrency", "USD")
					.queryParam("deliveryMethod", "cash")
					.build(false);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(builder.toString(), HttpMethod.GET, httpEntity, String.class);
			System.out.println(">>>>> response: " + response);
			
			if (flag) {
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
	}

	/*
	 * POST: https://test-public.lightnetapis.io/v1/remittances.validate
	 */
	private static final String POST_VALIDATE_ENDPOINT_URL = "https://test-public.lightnetapis.io/v1.1/remittances.validate";
	
	private static void validate() throws Exception {
		if (flag) {
			System.out.println("\n\n############### (" + CurrentInfo.get() + ") ###########");
			
			HttpHeaders headers = new HttpHeaders();
			//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + accessToken);
			
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
			sb.append("        \"transactionId\": \"AUTOMATE-SCB-12345\"\n");
			sb.append("    },\n");
			sb.append("    \"saveReport\": \"TRUE\"\n");
			sb.append("}\n");

			HttpEntity<String> httpEntity = new HttpEntity<>(sb.toString(), headers);
			
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.postForObject(POST_VALIDATE_ENDPOINT_URL, httpEntity, String.class);
			
			System.out.println(">>>>> response: " + response);
			
			if (flag) {
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
	}
}