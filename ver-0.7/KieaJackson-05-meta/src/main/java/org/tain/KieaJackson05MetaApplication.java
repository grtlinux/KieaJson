package org.tain;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.domain.FieldInfo;
import org.tain.domain.Group;
import org.tain.domain.Meta;
import org.tain.jobs.JobFieldInfo;
import org.tain.jobs.JobJsonData;
import org.tain.jobs.JobMetaInfo;
import org.tain.object.FieldObject;
import org.tain.object.GroupObject;
import org.tain.object.MetaObject;
import org.tain.repository.FieldInfoRepository;
import org.tain.repository.GroupRepository;
import org.tain.repository.MetaRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.PrintObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
		if (Flag.flag) test06();
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
				
				//this.sampleStream = result;
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////

	@Autowired
	private FieldInfoRepository fieldInfoRepository;
	
	private List<FieldInfo> lstFieldInfo = null;
	private Map<String, FieldInfo> mapFieldInfo = null;

	@Value("${json.field-info.out.file}")
	private String jsonFieldInfoOutFile;

	/**
	 * get field info and insert this into table
	 * 
	 * @throws Exception
	 */
	private void test05() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("KANG-20200614 >>>>> {}", this.jsonDataFile202);
			
			if (this.mapFieldInfo != null) this.mapFieldInfo.clear();
			this.mapFieldInfo = new JobFieldInfo(this.mapMeta2, this.jsonDataFile202).get();
			if (Flag.flag) {
				// insert into table
				for (Map.Entry<String, FieldInfo> field : this.mapFieldInfo.entrySet()) {
					this.fieldInfoRepository.save(field.getValue());
					if (Flag.flag) System.out.println(">>>>> fld = " + field.getValue());
				}
			}
			
			if (Flag.flag) {
				// select from table and make json file
				if (this.lstFieldInfo != null) this.lstFieldInfo.clear();
				this.lstFieldInfo = this.fieldInfoRepository.findAll();
				
				// String strJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this.lstFieldInfo);
				if (Flag.flag) {
					ObjectMapper objectMapper = new ObjectMapper();
					ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
					writer.writeValue(Paths.get(this.jsonFieldInfoOutFile).toFile(), this.lstFieldInfo);
				}
			}
			
			if (Flag.flag) {
				// select from table
				if (this.lstFieldInfo != null) this.lstFieldInfo.clear();
				if (this.mapFieldInfo != null) this.mapFieldInfo.clear();
				this.lstFieldInfo = this.fieldInfoRepository.findAll();
				for (FieldInfo field : this.lstFieldInfo) {
					this.mapFieldInfo.put(field.getFullName(), field);
				}
			}
		}
		
		if (Flag.flag) {
			// select from table and create map
			if (Flag.flag) {
				if (this.lstFieldInfo != null) this.lstFieldInfo.clear();
				if (this.mapFieldInfo != null) this.mapFieldInfo.clear();
				this.lstFieldInfo = this.fieldInfoRepository.findAll();
				this.lstFieldInfo.forEach(field -> {
					this.mapFieldInfo.put(field.getFullName(), field);
				});
			}
			
			// read data file
			if (Flag.flag) {
				// parsing the json data file
				if (this.lstFieldInfo != null) this.lstFieldInfo.clear();
				JsonNode jsonNode = new ObjectMapper().readValue(new File(this.jsonDataFile202), JsonNode.class);
				String prefix = "";
				_parsing05(prefix, jsonNode);
			}
			
			if (Flag.flag) {
				// concat
				String result = "";
				for (FieldInfo fld : this.lstFieldInfo) {
					if (!Flag.flag) System.out.printf(">>>>> tgtValue = [%s]%n", fld.getTgtValue());
					result += fld.getTgtValue();
				}
				if (Flag.flag) System.out.printf(">>>>> result(%d) = [%s]%n", result.length(), result);
				
				this.sampleStream = result;
			}
		}
		
		if (Flag.flag) {
			// select from table and create map
			if (Flag.flag) {
				if (this.lstFieldInfo != null) this.lstFieldInfo.clear();
				if (this.mapFieldInfo != null) this.mapFieldInfo.clear();
				this.lstFieldInfo = this.fieldInfoRepository.findAll();
				this.lstFieldInfo.forEach(field -> {
					this.mapFieldInfo.put(field.getFullName(), field);
				});
			}
			
			if (Flag.flag) {
				if (Flag.flag) System.out.printf(">>>>> sampleStream(%d) = [%s]%n", this.sampleStream.length(), this.sampleStream);
			}
			
			if (Flag.flag) {
				//this.index = 0;
				this.offset = 0;
				_analStream("", 0);
			}
			
			// read data file
			//if (!Flag.flag) {
			//	// parsing the json data file
			//	if (this.lstFieldInfo != null) this.lstFieldInfo.clear();
			//	JsonNode jsonNode = new ObjectMapper().readValue(new File(this.jsonDataFile202), JsonNode.class);
			//	String prefix = "";
			//	_parsing05(prefix, jsonNode);
			//}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	//
	// TODO: have to do your best
	//     have to learn by heart
	//
	private void jsonToYaml(JsonNode jsonNode, StringBuilder yaml, int depth) {
		if (jsonNode.isArray()) {
			for (JsonNode arrayItem: jsonNode) {
				appendNodeToYaml(arrayItem, yaml, depth, true);
			}
		} else if (jsonNode.isObject()) {
			appendNodeToYaml(jsonNode, yaml, depth, false);
		} else if (jsonNode.isValueNode()) {
			yaml.append(jsonNode.asText());
		} else {
			yaml.append(jsonNode.asText());
		}
	}
	
	private void appendNodeToYaml(JsonNode jsonNode, StringBuilder yaml, int depth, boolean isArray) {
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
		boolean isFirst = true;
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> node = fields.next();
			addFieldNameToYaml(yaml, node.getKey(), depth, isArray && isFirst);
			jsonToYaml(node.getValue(), yaml, depth+1);
			isFirst = false;
		}
	}
	
	private void addFieldNameToYaml(StringBuilder yaml, String fieldName, int depth, boolean isFirstInArray) {
		if (yaml.length() > 0) {
			yaml.append("\n");
			int requiredDepth = (isFirstInArray) ? depth-1 : depth;
			for (int i=0; i < requiredDepth; i++) {
				yaml.append("  ");
			}
			if (isFirstInArray) {
				yaml.append("- ");
			}
		}
		yaml.append(fieldName);
		yaml.append(": ");
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	//private int index = -1;
	
	private int _analStream(String prefix, int index) {
		while (index < this.lstFieldInfo.size()) {
			
			FieldInfo fieldInfo = this.lstFieldInfo.get(index);
			if (!Flag.flag) System.out.println(">>>>> " + fieldInfo);
			
			if (fieldInfo.getLastName().contains(".arrSize")) {
				// array
				int arrSize = Integer.valueOf(_getFieldValue(fieldInfo).trim());
				index ++;
				
				// make prefix for compare
				String fullName = fieldInfo.getFullName();
				String _prefix = fullName.substring(0, fullName.length() - 8);
				
				int ret = -1;
				for (int i=0; i < arrSize; i++) {
					ret = _analStream(_prefix, index);
				}
				index = ret;
			} else {
				// not array
				String fullName = fieldInfo.getFullName();
				if (fullName.indexOf(prefix) != 0) {
					return index;
				}
				// if not prefix and return
				
				String value = _getFieldValue(fieldInfo);
				if (Flag.flag) System.out.printf(">>>>> [%s] <- [%s]%n", value, fieldInfo);
				
				index ++;
			}
		}
		
		return index;
	}
	
	private int offset = -1;
	
	private String _getFieldValue(FieldInfo fieldInfo) {
		String ret = this.sampleStream.substring(offset, offset + fieldInfo.getSize());
		this.offset += fieldInfo.getSize();
		return ret;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	private void _parsing05(String prefix, JsonNode jsonNode) throws Exception {
		if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			_processing05(prefix + ".arrSize", String.valueOf(arrayNode.size()));
			
			Iterator<JsonNode> node = arrayNode.elements();
			while (node.hasNext()) {
				String _prefix = prefix;
				JsonNode _jsonNode = node.next();
				_parsing05(_prefix, _jsonNode);
			}
		} else if (jsonNode.isObject()) {
			jsonNode.fields().forEachRemaining(entry -> {
				String _prefix = prefix + "/" + entry.getKey();
				try {
					_parsing05(_prefix, entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} else if (jsonNode.isValueNode()) {
			_processing05(prefix, jsonNode.asText(""));
		} else {
			throw new Exception("ERROR: wrong json data...");
		}
	}
	
	private void _processing05(String prefix, String value) throws Exception {
		if (!Flag.flag) log.info("KANG-20200614 >>>>> {} = {}", prefix, value);
		
		FieldInfo fieldInfo = this.mapFieldInfo.get(prefix);

		int size = fieldInfo.getSize();
		
		String srcValue = String.format("%s", value);
		String tgtValue = "";
		if (size > 0)
			tgtValue = String.format("%-" + size + "s", value);
		
		fieldInfo.setSrcValue(srcValue);
		fieldInfo.setTgtValue(tgtValue);
		
		this.lstFieldInfo.add(FieldInfo.builder()
				.idx(fieldInfo.getIdx())
				.fullName(fieldInfo.getFullName())
				.lastName(fieldInfo.getLastName())
				.size(fieldInfo.getSize())
				.srcValue(srcValue)
				.tgtValue(tgtValue)
				.build());
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	
	private String sampleStream = null;
	
	private void test06() throws Exception {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		if (!Flag.flag) {
			// Ready to transfer from stream to JSON
			if (Flag.flag) System.out.printf(">>>>> stream(%d): [%s]%n", this.sampleStream.length(), this.sampleStream);
			
			
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
