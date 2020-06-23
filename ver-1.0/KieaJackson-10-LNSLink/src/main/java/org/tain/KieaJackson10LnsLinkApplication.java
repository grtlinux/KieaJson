package org.tain;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Board;
import org.tain.domain.Lightnet;
import org.tain.repository.BoardRepository;
import org.tain.repository.LightnetRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.SkipSSLConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaJackson10LnsLinkApplication implements CommandLineRunner {

	public static void main(String[] args) {
		log.info("KANG-20200618 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
		SpringApplication.run(KieaJackson10LnsLinkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (Flag.flag)
			job01();
		if (Flag.flag)
			job02();
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	@Autowired
	private BoardRepository boardRepository;
	
	private void job01() {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
		Random random = new Random(new Date().getTime());
		
		IntStream.rangeClosed(1, 200).forEach(index -> {
			String userId = null;
			if (Flag.flag) {
				switch (random.nextInt(1000) % 3) {
				case 1:
					userId = "kiea";
					break;
				case 2:
					userId = "kang";
					break;
				default:
					userId = "seok";
					break;
				}
			}
			this.boardRepository.save(Board.builder().title("제목-" + index).subTitle("부제목-" + index)
					.content("안녕하세요. 내용입니다. Hello, world!!!").userId(userId).build());
		});
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	@Value("${json.table-file.lightnet}")
	private String jsonTableFileLightnet;

	@Autowired
	private LightnetRepository lightnetRepository;

	private void job02() throws Exception {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());

		SkipSSLConfig.skip();
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Lightnet> lstLightnet = objectMapper.readValue(new File(this.jsonTableFileLightnet),
				new TypeReference<List<Lightnet>>() {
				});

		lstLightnet.forEach(entry -> {
			this.lightnetRepository.save(entry);
		});
	}

}
