package org.tain;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.tain.object.StmtObject;
import org.tain.object.UserObject;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@Log
public class KieaJacksonExample1Application implements CommandLineRunner {

	private static boolean flag = true;
	
	public static void main(String[] args) {
		SpringApplication.run(KieaJacksonExample1Application.class, args);
	}

	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	@Override
	public void run(String... args) throws Exception {
		if (flag) test01();
		if (flag) test02();
	}

	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	@Value("${file.stmt.path}")
	private String fileStmtPath;
	
	private void test01() throws Exception {
		log.info("KANG-20200609 >>>>> " + CurrentInfo.get());
		ObjectMapper objectMapper = new ObjectMapper();
		List<StmtObject> lstStmt = objectMapper.readValue(new File(fileStmtPath), new TypeReference<List<StmtObject>>() {});
		for (StmtObject stmt : lstStmt) {
			log.info("KANG-20200609 >>>>> " + stmt);
		}
	}
	
	@Value("${file.user.path}")
	private String fileUserPath;
	
	private void test02() throws Exception {
		log.info("KANG-20200609 >>>>> " + CurrentInfo.get());
		ObjectMapper objectMapper = new ObjectMapper();
		List<UserObject> lstUser = objectMapper.readValue(new File(fileUserPath), new TypeReference<List<UserObject>>() {});
		for (UserObject user : lstUser) {
			log.info("KANG-20200609 >>>>> " + user);
		}
	}
}
