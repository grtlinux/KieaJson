package org.tain;

import java.io.File;
import java.util.Iterator;
import java.util.List;

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
		if (flag) test02();
	}

	///////////////////////////////////////////////////////////////////////////////////

	@Value("${json.test.file}")
	private String jsonTestFile;
	
	private void test01() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readValue(new File(jsonTestFile), JsonNode.class);
		if (flag) log.info("KANG-20200611 >>>>> " + rootNode.toPrettyString());
		
		process("", rootNode);
	}

	private void process(String prefix, JsonNode currentNode) {
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
					process(!prefix.isEmpty() ? prefix + "[" + idx + "]" : "[" + String.valueOf(idx) + "]", jsonNode);
				}
				
				idx ++;
			}
			
			if (!"".equals(sb.toString())) {
				if (flag) System.out.println(">>>>> " + prefix + ": " + sb.delete(0, 2).toString());
			}
		} else if (currentNode.isObject()) {
			currentNode.fields().forEachRemaining(entry -> process(!prefix.isEmpty() ? prefix + "." + entry.getKey() : "." + entry.getKey(), entry.getValue()));
		} else {
			if (flag) System.out.println(">>>>> " + prefix + ": " + (currentNode.isNull() ? "-NULL-" : currentNode.toString()));
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
		for (PacketObject packet : lstPacket) {
			log.info("KANG-20200611 >>>>> " + packet);
			if (flag) this.packetRepository.save(Packet.builder()
					.grpCode(packet.getGrpCode())
					.seqNo(packet.getSeqNo())
					.name(packet.getName())
					.size(packet.getSize())
					.align(packet.getAlign())
					.type(packet.getType())
					.description(packet.getDescription())
					.build()
					);
		}
	}
}
