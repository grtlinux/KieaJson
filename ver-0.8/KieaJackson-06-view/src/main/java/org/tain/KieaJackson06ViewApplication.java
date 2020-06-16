package org.tain;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Board;
import org.tain.domain.FieldInfo;
import org.tain.domain.Group;
import org.tain.domain.Meta;
import org.tain.repository.BoardRepository;
import org.tain.repository.FieldInfoRepository;
import org.tain.repository.GroupRepository;
import org.tain.repository.MetaRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaJackson06ViewApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson06ViewApplication.class, args);
	}

	////////////////////////////////////////////////////////////////////////

	@Override
	public void run(String... args) throws Exception {
		if (Flag.flag) log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) jobJsonToTable_meta();
		if (Flag.flag) jobJsonToTable_group();
		if (Flag.flag) jobJsonToTable_field_info();
		if (Flag.flag) jobBoard();
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	
	@Value("${json.table-file.meta}")
	private String jsonTableFileMeta;
	
	@Autowired
	private MetaRepository metaRepository;

	private void jobJsonToTable_meta() throws Exception {
		if (Flag.flag) log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		
		List<Meta> lstMeta = new ObjectMapper().readValue(new File(this.jsonTableFileMeta), new TypeReference<List<Meta>>() {});
		lstMeta.forEach(entry -> {
			this.metaRepository.save(entry);
		});
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	
	@Value("${json.table-file.group}")
	private String jsonTableFileGroup;
	
	@Autowired
	private GroupRepository groupRepository;

	private void jobJsonToTable_group() throws Exception {
		if (Flag.flag) log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		
		List<Group> lstGroup = new ObjectMapper().readValue(new File(this.jsonTableFileGroup), new TypeReference<List<Group>>() {});
		lstGroup.forEach(entry -> {
			this.groupRepository.save(entry);
		});
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	
	@Value("${json.table-file.field-info}")
	private String jsonTableFileFieldInfo;
	
	@Autowired
	private FieldInfoRepository fieldInfoRepository;

	private void jobJsonToTable_field_info() throws Exception {
		if (Flag.flag) log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		
		List<FieldInfo> lstFieldInfo = new ObjectMapper().readValue(new File(this.jsonTableFileFieldInfo), new TypeReference<List<FieldInfo>>() {});
		lstFieldInfo.forEach(entry -> {
			this.fieldInfoRepository.save(entry);
		});
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private BoardRepository boardRepository;

	private void jobBoard() throws Exception {
		if (Flag.flag) log.info("KANG-20200616 >>>>> {}", CurrentInfo.get());
		
		IntStream.rangeClosed(1, 200).forEach(index ->
			this.boardRepository.save(Board.builder()
					.title("게시글"+index)
					.subTitle("순서"+index)
					.content("컨텐츠")
					.createdDate(LocalDateTime.now())
					.updatedDate(LocalDateTime.now())
					.build())
		);
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
}
