package org.tain;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.object.GrpObject;
import org.tain.repository.GrpRepository;
import org.tain.utils.CurrentInfo;
import org.tain.domain.Grp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class KieaJackson02GroupApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson02GroupApplication.class, args);
	}

	///////////////////////////////////////////////////////////////////////////////////
	
	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (flag) test01();
	}

	@Value("${json.grp.file}")
	private String jsonFile;
	
	@Autowired
	private GrpRepository grpRepository;
	
	private void test01() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<GrpObject> lstGrp = objectMapper.readValue(new File(jsonFile), new TypeReference<List<GrpObject>>() {});
		for (GrpObject grp : lstGrp) {
			log.info("KANG-20200611 >>>>> " + grp);
			this.grpRepository.save(Grp.builder()
					.grpCode(grp.getGrpCode())
					.grpName(grp.getGrpName())
					.direction(grp.getDirection())
					.description(grp.getDescription())
					.build());
		}
	}
}
