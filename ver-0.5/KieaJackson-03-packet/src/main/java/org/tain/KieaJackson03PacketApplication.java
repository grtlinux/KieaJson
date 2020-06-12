package org.tain;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Packet;
import org.tain.object.PacketObject;
import org.tain.repository.PacketRepository;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaJackson03PacketApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson03PacketApplication.class, args);
	}
	
	///////////////////////////////////////////////////////////////////////////////////

	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (!flag) test00();
		if (!flag) test01();
		if (!flag) test02();
		if (!flag) test03();
		if (!flag) test04();
		if (!flag) test05();
		if (!flag) test06();
		if (flag) test07();
		//if (flag) test08();
		//if (flag) test09();
		//if (flag) test10();
	}

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	private void test00() throws Exception {
		log.info("KANG-20200611 >>>>> {} {} - {}", CurrentInfo.get(), 123, "Hello");
	}

	///////////////////////////////////////////////////////////////////////////////////

	@Value("${json.test.file01}")
	private String jsonTestFile01;
	
	private void test01() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonTestFile01), JsonNode.class);
		if (flag) log.info("KANG-20200611 >>>>> " + rootNode.toPrettyString());
		
		if (flag) {
			// create
			this.processMap = new LinkedHashMap<>();
			
			// parsing and make
			String key = "";
			String prefix = "";
			process(key, prefix, rootNode);
			
			// 
			int idx = 0;
			for (Map.Entry<String, Object> entry : this.processMap.entrySet()) {
				if (flag) System.out.println(">>>>> map[" + (idx++) + "] - " + entry.getKey() + " -> " + entry.getValue());

				PacketObject packetObject = (PacketObject) entry.getValue();
				
				if (flag) this.packetRepository.save(Packet.builder()
						.grpCode(packetObject.getGrpCode())
						.seqNo(packetObject.getSeqNo())
						.name(packetObject.getName())
						.size(packetObject.getSize())
						.align(packetObject.getAlign())
						.type(packetObject.getType())
						.description(packetObject.getDescription())
						.build()
						);
			}
		}
	}
	
	private Map<String, Object> processMap = null;
	private int seqNo = 0;

	private void process(String key, String prefix, JsonNode currentNode) {
		if (currentNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) currentNode;
			
			if (flag) System.out.println(">>>>> " + prefix + "._arrSize = " + arrayNode.size());
			
			Iterator<JsonNode> node = arrayNode.elements();
			int idx = 1;
			StringBuffer sb = new StringBuffer("");
			while (node.hasNext()) {
				JsonNode jsonNode = node.next();
				
				if (jsonNode.isNumber()) {
					sb.append(", " + jsonNode.asLong());
				} else if (jsonNode.isTextual()) {
					sb.append(", " + jsonNode.asText());
				} else {
					process(!key.isEmpty() ? key : "/", !prefix.isEmpty() ? prefix + "[" + idx + "]" : "[" + String.valueOf(idx) + "]", jsonNode);
				}
				
				idx ++;
			}
			
			if (!"".equals(sb.toString())) {
				if (flag) System.out.println(">>>>> " + prefix + ": " + sb.delete(0, 2).toString());
			}
		} else if (currentNode.isObject()) {
			currentNode.fields().forEachRemaining(entry -> process(!key.isEmpty() ? key + "/" + entry.getKey() : "/" + entry.getKey(), !prefix.isEmpty() ? prefix + "/" + entry.getKey() : "/" + entry.getKey(), entry.getValue()));
		} else {
			if (flag) System.out.println(">>>>> (" + key + ") " + prefix + ": " + (currentNode.isNull() ? "-NULL-" : currentNode.toString()));
			this.processMap.put(key, new PacketObject(
					"101"
					, seqNo ++
					, key
					, 0
					, "LEFT"
					, "TEXT"
					, key + " description."
					));
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	@Value("${json.packet.file}")
	private String jsonFile;
	
	@Autowired
	private PacketRepository packetRepository;
	
	private void test02() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<PacketObject> lstPacket = objectMapper.readValue(new File(jsonFile), new TypeReference<List<PacketObject>>() {});
		for (PacketObject packetObject : lstPacket) {
			log.info("KANG-20200611 >>>>> " + packetObject);
			if (flag) this.packetRepository.save(Packet.builder()
					.grpCode(packetObject.getGrpCode())
					.seqNo(packetObject.getSeqNo())
					.name(packetObject.getName())
					.size(packetObject.getSize())
					.align(packetObject.getAlign())
					.type(packetObject.getType())
					.description(packetObject.getDescription())
					.build()
					);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	
	@Value("${json.test.file03}")
	private String jsonTestFile03;
	
	private void test03() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonTestFile03), JsonNode.class);
		if (flag) log.info("KANG-20200611 >>>>> " + rootNode.toPrettyString());
		
		if (flag) {
			String key = "";
			String prefix = "";
			process2(key, prefix, rootNode);
		}
	}
	
	private void process2(String key, String prefix, JsonNode currentNode) {
		if (currentNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) currentNode;
			
			if (!flag) System.out.println(">>>>> " + prefix + "._arrSize = " + arrayNode.size());
			
			Iterator<JsonNode> node = arrayNode.elements();
			int idx = 1;
			StringBuffer sb = new StringBuffer("");
			while (node.hasNext()) {
				JsonNode jsonNode = node.next();
				
				if (jsonNode.isNumber()) {
					sb.append(", " + jsonNode.asLong());
				} else if (jsonNode.isTextual()) {
					sb.append(", " + jsonNode.asText());
				} else {
					process(!key.isEmpty() ? key : "/", !prefix.isEmpty() ? prefix + "[" + idx + "]" : "[" + String.valueOf(idx) + "]", jsonNode);
				}
				
				idx ++;
			}
			
			if (!"".equals(sb.toString())) {
				// print
				if (!flag) System.out.println(">>>>> (" + key + ") " + prefix + ": " + sb.delete(0, 2).toString());
				if (flag) {
					PacketObject packetObject = (PacketObject) this.processMap.get(key);
					if (flag) System.out.println(">>>>> " + packetObject);
				}
			}
		} else if (currentNode.isObject()) {
			currentNode.fields().forEachRemaining(entry -> {
				String _key = !key.isEmpty() ? key + "/" + entry.getKey() : "/" + entry.getKey();
				String _prefix = !prefix.isEmpty() ? prefix + "/" + entry.getKey() : "/" + entry.getKey();
				process(_key, _prefix, entry.getValue());
			});
		} else {
			// print
			if (!flag) System.out.println(">>>>> (" + key + ") " + prefix + ": " + (currentNode.isNull() ? "-NULL-" : currentNode.toString()));
			if (flag) {
				PacketObject packetObject = (PacketObject) this.processMap.get(key);
				if (flag) System.out.println(">>>>> " + packetObject);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	
	@Value("${json.test.file04}")
	private String jsonTestFile04;
	
	private void test04() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonTestFile04), JsonNode.class);
		if (!flag) log.info("KANG-20200611 >>>>> " + rootNode.toPrettyString());

		if (flag) {
			String prefix = "";
			parsing04(prefix, rootNode);
		}
	}
	
	private void parsing04(String prefix, JsonNode jsonNode) {
		if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			//if (flag) System.out.println(">>>>> " + prefix + ".arrSize: " + arrayNode.size());
			print04(prefix + ".arrSize", String.valueOf(arrayNode.size()));  // addition
			
			Iterator<JsonNode> node = arrayNode.elements();
			//int idx = 1;
			while (node.hasNext()) {
				//String _prefix = prefix.isEmpty() ? "[" + idx + "]" : prefix + "[" + idx + "]";
				//String _prefix = prefix + "[" + idx + "]";
				String _prefix = prefix;
				JsonNode _jsonNode = node.next();
				parsing04(_prefix, _jsonNode);
				
				//idx ++;
			}
		} else if (jsonNode.isObject()) {
			jsonNode.fields().forEachRemaining(entry -> {
				//String _prefix = prefix.isEmpty() ? "/" + entry.getKey() : prefix + "/" + entry.getKey();
				String _prefix = prefix + "/" + entry.getKey();
				parsing04(_prefix, entry.getValue());
			});
		} else {
			print04(prefix, jsonNode.asText());
		}
	}
	
	private void print04(String prefix, String value) {
		//if (flag) System.out.println(">>>>> " + prefix + ": " + (jsonNode.isNull() ? "-NULL-" : jsonNode.toString()));
		if (flag) System.out.println(">>>>> " + prefix + ": " + value);
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	
	@Value("${yaml.test.file05}")
	private String yamlTestFile05;
	
	//private static String YAML_INDENT = " ";
	//private static String YAML_ARR_INDENT_START = "- ";
	//private static String YAML_END_OF_LINE = "\n";
	
	private void test05() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		if (!flag) System.out.println(">>>>> " + yamlTestFile05);
		
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		
		if (flag) {
			JsonNode rootNode = objectMapper.readValue(new File(yamlTestFile05), JsonNode.class);
			if (flag) System.out.println(">>>>> " + rootNode.toPrettyString());
		}
		
		if (flag) {
			String strYaml = ""
					+ "number:\n"
					+ "- 1\n"
					+ "- 2\n"
					+ "- 3\n"
					+ "- 4\n"
					+ "";
			JsonNode rootNode = objectMapper.readValue(strYaml, JsonNode.class);
			if (flag) System.out.println(">>>>> " + rootNode.toPrettyString());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	
	@Value("${json.test.file06}")
	private String jsonTestFile06;
	
	private Map<String, Object> map06 = null;
	
	private void test06() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		if (!flag) System.out.println(">>>>> " + jsonTestFile06);
		
		//ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		ObjectMapper objectMapper = new ObjectMapper();
		//JsonNode rootNode = objectMapper.readValue(new File(jsonTestFile06), JsonNode.class);
		JsonNode rootNode = objectMapper.readTree(new File(jsonTestFile06));
		if (flag) System.out.println(">>>>> " + rootNode.toPrettyString());
		
		if (flag) {
			this.map06 = new LinkedHashMap<>();
			this.map06.clear();
		}
		
		if (flag) {
			String prefix = "";
			parsing06(prefix, rootNode);
		}
		
		if (flag) {
			for (Map.Entry<String, Object> entry : this.map06.entrySet()) {
				log.info("KANG-20200612 >>>>> {} -> {}", entry.getKey(), entry.getValue());
			}
		}
		
		if (flag) {
			StringBuilder yaml = new StringBuilder();
			processNode(rootNode, yaml, 0);
			if (flag) System.out.println(">>>>>>>>>>> \n" + yaml.toString());
		}
	}
	
	private void parsing06(String prefix, JsonNode jsonNode) throws Exception {
		if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			//if (flag) System.out.println(">>>>> " + prefix + ".arrSize: " + arrayNode.size());
			print06(prefix + ".arrSize", String.format("%04d", arrayNode.size()));  // addition
			
			Iterator<JsonNode> node = arrayNode.elements();
			//int idx = 1;
			while (node.hasNext()) {
				//String _prefix = prefix.isEmpty() ? "[" + idx + "]" : prefix + "[" + idx + "]";
				//String _prefix = prefix + "[" + idx + "]";
				String _prefix = prefix;
				JsonNode _jsonNode = node.next();
				parsing06(_prefix, _jsonNode);
				
				//idx ++;
			}
		} else if (jsonNode.isObject()) {
			jsonNode.fields().forEachRemaining(entry -> {
				//String _prefix = prefix.isEmpty() ? "/" + entry.getKey() : prefix + "/" + entry.getKey();
				String _prefix = prefix + "/" + entry.getKey();
				try {
					parsing06(_prefix, entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} else if (jsonNode.isValueNode()) {
			print06(prefix, jsonNode.asText().equals("null") ? "" : jsonNode.asText());
		} else {
			throw new Exception("wrong json type - 1");
		}
	}
	
	private void print06(String prefix, String value) {
		//if (flag) System.out.println(">>>>> " + prefix + ": " + (jsonNode.isNull() ? "-NULL-" : jsonNode.toString()));
		if (!flag) System.out.println(">>>>> " + prefix + ": " + value);
		this.map06.put(prefix, value);
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	
	private void processNode(JsonNode jsonNode, StringBuilder yaml, int depth) {
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
	
	private void appendNodeToYaml(JsonNode node, StringBuilder yaml, int depth, boolean isArrayItem) {
		Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
		boolean isFirst = true;
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> jsonField = fields.next();
			addFieldNameToYaml(yaml, jsonField.getKey(), depth, isArrayItem && isFirst);
			processNode(jsonField.getValue(), yaml, depth+1);
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
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	@Value("${json.test.file07}")
	private String jsonTestFile07;

	private void test07() throws Exception {
		log.info("KANG-20200611 >>>>> {} - {} {}", CurrentInfo.get(), 123, "Hello");
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(new File(jsonTestFile07));
		if (flag) System.out.println(">>>>> " + rootNode.toPrettyString());
		
	}

	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
}
