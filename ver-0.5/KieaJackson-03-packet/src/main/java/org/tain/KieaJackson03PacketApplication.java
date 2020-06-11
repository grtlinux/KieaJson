package org.tain;

import java.io.File;
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
import com.fasterxml.jackson.databind.ObjectMapper;

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
	}

	@Value("${json.packet.file}")
	private String jsonFile;
	
	@Autowired
	private PacketRepository packetRepository;
	
	private void test01() throws Exception {
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
