package org.tain;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Meta;
import org.tain.object.MetaObject;
import org.tain.repository.MetaRepository;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class KieaJackson01MetaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson01MetaApplication.class, args);
	}

	/////////////////////////////////////////////////////////////////////

	private static boolean flag = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (flag) test01();
	}

	@Value("${json.meta.file}")
	private String jsonFile;
	
	@Autowired
	private MetaRepository metaRepository;
	
	private void test01() throws Exception {
		log.info("KANG-20200611 >>>>> " + CurrentInfo.get());
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<MetaObject> lstMeta = objectMapper.readValue(new File(jsonFile), new TypeReference<List<MetaObject>>() {});
		for (MetaObject meta : lstMeta) {
			log.info("KANG-20200611 >>>>> " + meta);
			this.metaRepository.save(Meta.builder().name(meta.getName()).size(meta.getSize()).align(meta.getAlign()).description(meta.getDescription()).build());
		}
	}
}
