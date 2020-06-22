package org.tain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Board;
import org.tain.repository.BoardRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

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

	private void job02() {
		log.info("KANG-20200618 >>>>> {}", CurrentInfo.get());
		
	}

}
