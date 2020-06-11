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

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class KieaJackson03PacketApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson03PacketApplication.class, args);
	}
	
	///////////////////////////////////////////////////////////////////////////////////

	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (flag) test01();
		if (!flag) test02();
		if (flag) test03();
	}

	///////////////////////////////////////////////////////////////////////////////////

	@Value("${json.test.file}")
	private String jsonTestFile;
	
	private void test01() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonTestFile), JsonNode.class);
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
	
	private void test03() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonTestFile), JsonNode.class);
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
}
