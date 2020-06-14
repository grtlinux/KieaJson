package org.tain;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.object.Test03Object;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j  // Simple Logging Facade for Java
public class KieaJackson04PacketApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson04PacketApplication.class, args);
	}

	///////////////////////////////////////////////////////////////////////////////
	
	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (!flag) test01();
		if (!flag) test02();
		if (flag) test03();
	}

	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	
	@Value("${json.test.file01}")
	private String jsonTestFile01;
	
	@Value("${yaml.test.file01}")
	private String yamlTestFile01;

	/**
	 * Parsing JSON file
	 * 
	 * @throws Exception
	 */
	private void test01() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (!flag) {
			// JsonObject from JsonFile
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readValue(new File(jsonTestFile01), JsonNode.class);
			log.info("KANG-20200614 >>>>> {}", jsonNode.toPrettyString());
		}
		
		if (!flag) {
			// JsonObject from YamlFile
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			JsonNode jsonNode = objectMapper.readValue(new File(yamlTestFile01), JsonNode.class);
			log.info("KANG-20200614 >>>>> {}", jsonNode.toPrettyString());
		}
		
		if (flag) {
			// parsing JsonObject
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readValue(new File(jsonTestFile01), JsonNode.class);
			//log.info("KANG-20200614 >>>>> {}", jsonNode.toPrettyString());
			System.out.printf("KANG-20200614 >>>>> %s%n", jsonNode.toPrettyString());
			
			String prefix = "";
			_parsing01(prefix, jsonNode);
		}
	}

	///////////////////////////////////////////////////////////////////////////////
	private void _parsing01(String prefix, JsonNode jsonNode) {
		if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			_print01(prefix + ".arrSize", String.valueOf(arrayNode.size()));
			
			Iterator<JsonNode> node = arrayNode.elements();
			//int idx = 1;
			while (node.hasNext()) {
				String _prefix = prefix;
				JsonNode _jsonNode = node.next();
				_parsing01(_prefix, _jsonNode);
				
				//idx++;
			}
		} else if (jsonNode.isObject()) {
			jsonNode.fields().forEachRemaining(entry -> {
				String _prefix = prefix + "/" + entry.getKey();
				_parsing01(_prefix, entry.getValue());
			});
		} else if (jsonNode.isValueNode()) {
			//_print01(prefix, jsonNode.toString());
			_print01(prefix, jsonNode.asText("_NULL_"));
		} else {
			new Exception("ERROR: wrong json node.....").printStackTrace(System.err);
		}
	}
	
	private void _print01(String prefix, String value) {
		//log.info(">>>>> prefix={}    value={}", prefix, value);
		System.out.printf(">>>>> prefix=%s   value=%s%n", prefix, value);
	}
	
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	
	@Value("${json.test.file02}")
	private String jsonTestFile02;
	
	@Value("${yaml.test.file02}")
	private String yamlTestFile02;
	
	/**
	 * Convert JSON to Yaml
	 * 
	 * @throws Exception
	 */
	private void test02() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (!flag) {
			// JsonNode to Yaml: first: FAIL
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readValue(new File(jsonTestFile02), JsonNode.class);
			//log.info("KANG-20200614 >>>>> {}", jsonNode.toPrettyString());
			System.out.printf("KANG-20200614 >>>>> %s%n", jsonNode.toPrettyString());
			
			StringBuilder yaml = new StringBuilder();
			// TODO: try to do after you have free time..
			jsonToYaml(jsonNode, yaml, 0);
			System.out.printf("KANG-20200614 >>>>> %s%n", yaml.toString());
		}
		
		if (flag) {
			// SUCCESS
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readValue(new File(jsonTestFile02), JsonNode.class);
			//log.info("KANG-20200614 >>>>> {}", jsonNode.toPrettyString());
			System.out.printf("KANG-20200614 >>>>> %s%n", jsonNode.toPrettyString());
			
			String yaml = new YAMLMapper().writeValueAsString(jsonNode);
			System.out.printf("KANG-20200614 >>>>> %s%n", yaml);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////
	private void jsonToYaml(JsonNode jsonNode, StringBuilder yaml, int depth) {
		if (jsonNode.isArray()) {
			for (JsonNode arrayItem: jsonNode) {
				appendNodeToYaml(arrayItem, yaml, depth, true);
			}
		} else if (jsonNode.isObject()) {
			appendNodeToYaml(jsonNode, yaml, depth, false);
		} else if (jsonNode.isValueNode()) {
			yaml.append(jsonNode.asText());
		} else {
			yaml.append(jsonNode.asText());
		}
	}
	
	private void appendNodeToYaml(JsonNode jsonNode, StringBuilder yaml, int depth, boolean isArray) {
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
		boolean isFirst = true;
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> node = fields.next();
			addFieldNameToYaml(yaml, node.getKey(), depth, isArray && isFirst);
			jsonToYaml(node.getValue(), yaml, depth+1);
			isFirst = false;
		}
	}
	
	private void addFieldNameToYaml(StringBuilder yaml, String fieldName, int depth, boolean isFirstInArray) {
		if (yaml.length() > 0) {
			yaml.append("\n");
			int requiredDepth = (isFirstInArray) ? depth-1 : depth;
			for (int i=0; i < requiredDepth; i++) {
				yaml.append("  ");
			}
			if (isFirstInArray) {
				yaml.append("- ");
			}
		}
		yaml.append(fieldName);
		yaml.append(": ");
	}
	
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	
	@Value("${json.test.file03}")
	private String jsonTestFile03;
	
	@Value("${json.out.file03}")
	private String jsonOutFile03;

	@Value("${yaml.test.file03}")
	private String yamlTestFile03;
	
	private Map<String, Object> map03 = null;
	private int sortNo03 = -1;
	private List<Object> list03 = null;
	
	/**
	 * Convert JSON to Map(LinkedHashMap)
	 * 
	 * @throws Exception
	 */
	private void test03() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (flag) {
			// Json to Map
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(new File(jsonTestFile03));
			
			this.map03 = new LinkedHashMap<>();
			this.sortNo03 = 0;
			
			_parsing03("", jsonNode);
		}
		
		if (flag) {
			// Map to List
			list03 = new ArrayList<Object>(this.map03.values());
			for (Object object : list03) {
				Test03Object obj = (Test03Object) object;
				System.out.println(">>>>> " + obj);
			}
		}
		
		if (flag) {
			// List to Json
			
			//JSONObject jsonObject = new JSONObject(this.list03);
			//JSONArray jsonArray = JSONArray.fromObject(list);
			//ArrayNode node = new ArrayNode()
			//JSONArray jsonArray = new JSONArray();
			//jsonArray.add(this.list03);
			//System.out.println(">>>>> " + jsonArray.toString());
			
			ObjectMapper objectMapper = new ObjectMapper();
			String strJson = objectMapper.writeValueAsString(this.list03);
			System.out.println(">>>>> strJson: " + strJson);
			String strPrettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.list03);
			System.out.println(">>>>> strPrettyJson: " + strPrettyJson);
			
			List<Test03Object> list = Arrays.asList(objectMapper.readValue(strPrettyJson, Test03Object[].class));
			for (Test03Object obj : list) {
				System.out.println(">>>>> " + obj);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////
	private void _parsing03(String prefix, JsonNode jsonNode) {
		if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			_print03(prefix + ".arrSize", String.valueOf(arrayNode.size()));
			
			Iterator<JsonNode> node = arrayNode.elements();
			while (node.hasNext()) {
				String _prefix = prefix;
				JsonNode _jsonNode = node.next();
				_parsing03(_prefix, _jsonNode);
			}
		} else if (jsonNode.isObject()) {
			jsonNode.fields().forEachRemaining(entry -> {
				String _prefix = prefix + "/" + entry.getKey();
				_parsing03(_prefix, entry.getValue());
			});
		} else if (jsonNode.isValueNode()) {
			_print03(prefix, jsonNode.asText("_NULL_"));
		} else {
			new Exception("ERROR: wrong json node.....").printStackTrace(System.err);
		}
	}
	
	private void _print03(String prefix, String value) {
		System.out.printf(">>>>> prefix=%s   value=%s%n", prefix, value);
		if (flag) {
			// info to map
			this.map03.put(prefix, Test03Object.builder().name(prefix).size(10).sortNo(this.sortNo03++).build());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
}
