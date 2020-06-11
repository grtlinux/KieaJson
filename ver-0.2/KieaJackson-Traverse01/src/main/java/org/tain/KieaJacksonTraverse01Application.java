package org.tain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class KieaJacksonTraverse01Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJacksonTraverse01Application.class, args);
	}

	/////////////////////////////////////////////////////////////////////////
	
	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (!flag) traverse01();
		if (!flag) traverse02();
		if (!flag) traverse03();
		if (flag) traverse04();
	}

	@Value("${json.test.file}")
	private String jsonFile;
	
	private void traverse01() throws Exception {
		log.info("KANG-20200610 >>>>> " + CurrentInfo.get());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonFile), JsonNode.class);
		//JsonNode rootNode = objectMapper.valueToTree(jsonString);
		//JsonNode rootNode = objectMapper.convertValue(jsonString, JsonNode.class);
		
		log.info("KANG-20200610 >>>>> " + rootNode);
		if (flag) traverse(rootNode);
	}
	
	private void traverse02() throws Exception {
		log.info("KANG-20200610 >>>>> " + CurrentInfo.get());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonFile), JsonNode.class);
		//JsonNode rootNode = objectMapper.valueToTree(jsonString);
		//JsonNode rootNode = objectMapper.convertValue(jsonString, JsonNode.class);
		
		printNode(rootNode);
	}
	
	private void traverse03() throws Exception {
		log.info("KANG-20200610 >>>>> " + CurrentInfo.get());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonFile), JsonNode.class);
		//JsonNode rootNode = objectMapper.valueToTree(jsonString);
		//JsonNode rootNode = objectMapper.convertValue(jsonString, JsonNode.class);
		
		Map<String, String> map = new HashMap<>();
		addKeys("", rootNode, map, new ArrayList<>());
		map.entrySet().forEach(System.out::println);
	}
	
	private void traverse04() throws Exception {
		log.info("KANG-20200610 >>>>> " + CurrentInfo.get());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonFile), JsonNode.class);
		//JsonNode rootNode = objectMapper.valueToTree(jsonString);
		//JsonNode rootNode = objectMapper.convertValue(jsonString, JsonNode.class);
		
		//System.out.println(">>>>> " + rootNode);
		System.out.println(">>>>> " + rootNode.toPrettyString());
		
		process("", rootNode);
	}
	
	////////////////////////////////////////////////////////////////////////
	/*
	"result": [ "Hello", "world", "Hi", "Okey" ]
	 */
	private void process(String prefix, JsonNode currentNode) {
		if (currentNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) currentNode;
			System.out.println(">>>>> " + prefix + "._arrSize = " + arrayNode.size());
			Iterator<JsonNode> node = arrayNode.elements();
			int idx = 1;
			StringBuffer sb = new StringBuffer("");
			while (node.hasNext()) {
				JsonNode jsonNode = node.next();
				
				if (jsonNode.isNumber()) {
					//System.out.println(">>>>> isNumber: " + jsonNode.isNumber() + " -> " + jsonNode.asLong());
					sb.append(", " + jsonNode.asLong());
				} else if (jsonNode.isTextual()) {
					//System.out.println(">>>>> isTextural: " + jsonNode.isTextual() + " -> " + jsonNode.asText());
					sb.append(", " + jsonNode.asText());
				} else {
					//System.out.println(">>>>> isObject: " + jsonNode.isObject() + " -> " + jsonNode.asText());
					process(!prefix.isEmpty() ? prefix + "[" + idx + "]" : "[" + String.valueOf(idx) + "]", jsonNode);
				}
				
				idx ++;
			}
			if (!"".equals(sb.toString())) {
				if (flag) System.out.println(">>>>> " + prefix + ": " + sb.delete(0, 2).toString());
			}
		} else if (currentNode.isObject()) {
			currentNode.fields().forEachRemaining(entry -> process(!prefix.isEmpty() ? prefix + "." + entry.getKey() : entry.getKey(), entry.getValue()));
		} else {
			if (!flag) System.out.println(">>>>> " + prefix + ": " + (currentNode.isNull() ? "-NULL-" : currentNode.toString()));
			if (flag) System.out.println(">>>>> " + prefix);
		}
	}

	////////////////////////////////////////////////////////////////////////
	/*
	"result": [
		"Hello", "world", "Hi", "Okey"
	]
	 */
	private void addKeys(String currentPath, JsonNode jsonNode, Map<String, String> map, List<Integer> suffix) {
		if (jsonNode.isObject()) {
			ObjectNode objectNode = (ObjectNode) jsonNode;
			Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
			String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";
			while (iter.hasNext()) {
				Map.Entry<String, JsonNode> entry = iter.next();
				addKeys(pathPrefix + entry.getKey(), entry.getValue(), map, suffix);
			}
		} else if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			for (int i = 0; i < arrayNode.size(); i++) {
				suffix.add(i + 1);
				addKeys(currentPath, arrayNode.get(i), map, suffix);
				if (i + 1 <arrayNode.size()){
					suffix.remove(arrayNode.size() - 1);
				}
			}
		} else if (jsonNode.isValueNode()) {
			if (currentPath.contains(".")) {
				for (int i = 0; i < suffix.size(); i++) {
					currentPath += "." + suffix.get(i);
				}
				suffix = new ArrayList<>();
			}
			ValueNode valueNode = (ValueNode) jsonNode;
			map.put(currentPath, valueNode.asText());
		}
	}
	
	////////////////////////////////////////////////////////////////////////
	private void printNode(JsonNode jsonNode) throws Exception {
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			String fieldName = field.getKey();
			JsonNode fieldValue = field.getValue();
			if (fieldValue.getNodeType().equals(JsonNodeType.STRING)) {
				System.out.println(">>>>> " + fieldName + " = " + fieldValue.asText() + " [" + fieldValue.getNodeType() + "]");
			} else if (fieldValue.getNodeType().equals(JsonNodeType.NUMBER)) {
				System.out.println(">>>>> " + fieldName + " = " + fieldValue.asText() + " [" + fieldValue.getNodeType() + "]");
			} else if (fieldValue.getNodeType().equals(JsonNodeType.OBJECT)) {
				System.out.println(">>>>> " + fieldName + " = " + fieldValue.asText() + " [" + fieldValue.getNodeType() + "]");
				printNode(fieldValue);
			} else if (fieldValue.getNodeType().equals(JsonNodeType.ARRAY)) {
				System.out.println(">>>>> " + fieldName + " = " + fieldValue.asText() + " [" + fieldValue.getNodeType() + "]");
				ArrayNode arrayNode = (ArrayNode) fieldValue;
				for (int i=0; i < arrayNode.size(); i++) {
					JsonNode arrayElement = arrayNode.get(i);
					printNode(arrayElement);
				}
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////
	private void traverse(JsonNode root) throws Exception {
		if (root.isObject()) {
			Iterator<String> fieldNames = root.fieldNames();
			while (fieldNames.hasNext()) {
				String fieldName = fieldNames.next();
				JsonNode fieldValue = root.get(fieldName);
				traverse(fieldValue);
			}
		} else if (root.isArray()) {
			ArrayNode arrayNode = (ArrayNode) root;
			for (int i=0; i < arrayNode.size(); i++) {
				JsonNode arrayElement = arrayNode.get(i);
				traverse(arrayElement);
			}
		} else {
			//throw new Exception("wrong json file");
			//if (flag) System.out.println(">>>>> " + fieldName + " : " + fieldValue.asText());
			
		}
	}
}
