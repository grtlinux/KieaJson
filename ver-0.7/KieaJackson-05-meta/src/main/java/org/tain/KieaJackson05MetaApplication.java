package org.tain;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.Group;
import org.tain.domain.Meta;
import org.tain.jobs.JobJsonData;
import org.tain.jobs.JobMetaInfo;
import org.tain.object.FieldObject;
import org.tain.object.GroupObject;
import org.tain.object.MetaObject;
import org.tain.repository.GroupRepository;
import org.tain.repository.MetaRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.PrintObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaJackson05MetaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson05MetaApplication.class, args);
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public void run(String... args) throws Exception {
		if (Flag.flag) test01();
		if (Flag.flag) test02();
		if (Flag.flag) test03();
		if (Flag.flag) test04();
		if (Flag.flag) test05();
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////
	
	@Value("${json.group.path}")
	private String jsonGroupPath;

	@Value("${json.group.out.file}")
	private String jsonGroupOutFile;

	//private Map<String, GroupObject> mapGroup1 = null;
	
	@Autowired
	private GroupRepository groupRepository;
	
	private Map<String, Group> mapGroup2 = null;
	
	//////////////////////////////////////////////////////////
	
	@Value("${json.meta.path}")
	private String jsonMetaPath;

	@Value("${json.meta.out.file}")
	private String jsonMetaOutFile;

	private Map<String, MetaObject> mapMeta1 = null;
	
	@Autowired
	private MetaRepository metaRepository;
	
	private Map<String, Meta> mapMeta2 = null;

	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * make TB_GROUP from table_group.json
	 * make TB_META from JSON Data files
	 * 
	 * @throws Exception
	 */
	private void test01() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// create table from json file and insert data into group table
			ObjectMapper objectMapper = new ObjectMapper();
			List<GroupObject> list = objectMapper.readValue(new File(this.jsonGroupOutFile), new TypeReference<List<GroupObject>>() {});
			for (GroupObject obj : list) {
				this.groupRepository.save(Group.builder()
						.grpCode(obj.getGrpCode())
						.grpName(obj.getGrpName())
						.name(obj.getName())
						.type(obj.getType())
						.description(obj.getDescription())
						.content(obj.getContent())
						.build());
			}
		}
		
		/////////////////////////////////////////////////////////////////////////
		
		if (Flag.flag) {
			// get map of MetaInfo
			this.mapMeta1 = new JobMetaInfo(this.jsonMetaPath).get();
			if (!Flag.flag) PrintObject.printMetaObject(this.mapMeta1);
		}
		
		if (Flag.flag) {
			// create table and insert into table
			List<MetaObject> list = new ArrayList<>(this.mapMeta1.values());
			for (MetaObject meta : list) {
				this.metaRepository.save(Meta.builder().name(meta.getName()).size(meta.getSize()).build());
			}
			
			// String strJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list);
			if (Flag.flag) {
				ObjectMapper objectMapper = new ObjectMapper();
				ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
				writer.writeValue(Paths.get(jsonMetaOutFile).toFile(), list);
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * save to JSON file from info List
	 * 
	 * @throws Exception
	 */
	private void test02() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// select from table
			List<GroupObject> lstObject = new ArrayList<>();
			List<Group> list = this.groupRepository.findAll();
			list.forEach(entry -> {
				lstObject.add(GroupObject.builder()
						.grpCode(entry.getGrpCode())
						.grpName(entry.getGrpName())
						.name(entry.getName())
						.type(entry.getType())
						.description(entry.getDescription())
						.content(entry.getContent())
						.build());
			});

			// rewrite to JSON File
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
			writer.writeValue(Paths.get(this.jsonGroupOutFile).toFile(), lstObject);
		}
		
		/////////////////////////////////////////////////////////////////////////

		if (Flag.flag) {
			// select from table
			List<MetaObject> lstObject = new ArrayList<>();
			List<Meta> list = this.metaRepository.findAll();
			list.forEach(entry -> {
				lstObject.add(MetaObject.builder()
						.name(entry.getName())
						.size(entry.getSize())
						.build());
			});

			// rewrite to JSON File
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
			writer.writeValue(Paths.get(this.jsonMetaOutFile).toFile(), lstObject);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * get map info from info table (TB_GROUP, TB_META)
	 * 
	 * @throws Exception
	 */
	private void test03() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// select from table
			this.mapGroup2 = new LinkedHashMap<>();
			List<Group> list = this.groupRepository.findAll();
			for (Group obj : list) {
				this.mapGroup2.put(obj.getGrpCode(), obj);
			}
			// print info
			if (!Flag.flag) PrintObject.printGroup(this.mapGroup2);
		}
		
		/////////////////////////////////////////////////////////////////////////

		if (Flag.flag) {
			// select from table
			this.mapMeta2 = new LinkedHashMap<>();
			List<Meta> list = this.metaRepository.findAll();
			for (Meta obj : list) {
				this.mapMeta2.put(obj.getName(), obj);
			}
			// print info
			if (!Flag.flag) PrintObject.printMeta(this.mapMeta2);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	@Value("${json.data.file101}")
	private String jsonDataFile101;

	@Value("${json.data.file102}")
	private String jsonDataFile102;

	//@Value("${json.data.file201}")
	//private String jsonDataFile201;

	@Value("${json.data.file202}")
	private String jsonDataFile202;

	/**
	 * JSON to Stream
	 * 
	 * @throws Exception
	 */
	private void test04() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("KANG-20200614 >>>>> {}", this.jsonDataFile101);
			
			List<FieldObject> lstField = new JobJsonData(this.mapMeta2, this.jsonDataFile101).get();
			if (Flag.flag) {
				// confirm print
				String result = "";
				for (FieldObject fld : lstField) {
					if (!Flag.flag) System.out.printf(">>>>> tgtValue = [%s]%n", fld.getTgtValue());
					result += fld.getTgtValue();
				}
				if (Flag.flag) System.out.printf(">>>>> result = [%s]%n", result);
			}
			
		}
		
		if (Flag.flag) {
			log.info("KANG-20200614 >>>>> {}", this.jsonDataFile102);
			
			List<FieldObject> lstField = new JobJsonData(this.mapMeta2, this.jsonDataFile102).get();
			if (Flag.flag) {
				// confirm print
				String result = "";
				for (FieldObject fld : lstField) {
					if (!Flag.flag) System.out.printf(">>>>> tgtValue = [%s]%n", fld.getTgtValue());
					result += fld.getTgtValue();
				}
				if (Flag.flag) System.out.printf(">>>>> result = [%s]%n", result);
			}
		}
		
		if (Flag.flag) {
			log.info("KANG-20200614 >>>>> {}", this.jsonDataFile202);
			
			List<FieldObject> lstField = new JobJsonData(this.mapMeta2, this.jsonDataFile202).get();
			if (Flag.flag) {
				// confirm print
				String result = "";
				for (FieldObject fld : lstField) {
					if (!Flag.flag) System.out.printf(">>>>> tgtValue = [%s]%n", fld.getTgtValue());
					result += fld.getTgtValue();
				}
				if (Flag.flag) System.out.printf(">>>>> result = [%s]%n", result);
				
				this.sampleStream = result;
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	private String sampleStream = null;
	
	private void test05() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			System.out.printf(">>>>> stream(%d): [%s]%n", this.sampleStream.length(), this.sampleStream);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
}
