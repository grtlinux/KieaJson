package org.tain.jobs;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.tain.domain.FieldInfo;
import org.tain.domain.Meta;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobFieldInfo {

	private Map<String, Meta> mapMeta2;
	private String jsonDataFile;
	
	private int idx = -1;
	//private List<FieldInfo> lstField;
	private Map<String, FieldInfo> mapField;

	public JobFieldInfo(Map<String, Meta> mapMeta2, String jsonDataFile) {
		this.mapMeta2 = mapMeta2;
		this.jsonDataFile = jsonDataFile;
		
		//this.lstField = new ArrayList<>();
		this.mapField = new LinkedHashMap<>();
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	//public List<FieldInfo> get() throws Exception {
	public Map<String, FieldInfo> get() throws Exception {
		log.info("KANG-20200614 >>>>> {} {}", CurrentInfo.get(), this.jsonDataFile);

		if (Flag.flag) {
			// parsing the json data file
			JsonNode jsonNode = new ObjectMapper().readValue(new File(this.jsonDataFile), JsonNode.class);
			this.idx = 0;
			String prefix = "";
			_parsing(prefix, jsonNode);
		}
		
		return this.mapField;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void _parsing(String prefix, JsonNode jsonNode) throws Exception {
		if (jsonNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) jsonNode;
			_processLeafNode(prefix + ".arrSize", String.valueOf(arrayNode.size()));
			
			Iterator<JsonNode> node = arrayNode.elements();
			while (node.hasNext()) {
				String _prefix = prefix;
				JsonNode _jsonNode = node.next();
				_parsing(_prefix, _jsonNode);
			}
		} else if (jsonNode.isObject()) {
			jsonNode.fields().forEachRemaining(entry -> {
				String _prefix = prefix + "/" + entry.getKey();
				try {
					_parsing(_prefix, entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} else if (jsonNode.isValueNode()) {
			_processLeafNode(prefix, jsonNode.asText(""));
		} else {
			throw new Exception("ERROR: wrong json file..");
		}
	}
	
	private void _processLeafNode(String prefix, String value) throws Exception {
		if (!Flag.flag) log.info("KANG-20200614 >>>>> {} = {}", prefix, value);
		
		String fullName = prefix;
		String lastName = prefix;
		int index = lastName.lastIndexOf("/");
		if (index >= 0) {
			lastName = lastName.substring(index + 1);
		}

		Meta meta = this.mapMeta2.get(lastName);
		if (meta == null) {
			throw new Exception("ERROR: worng json data file");
		}
		
		int size = meta.getSize();
		
		String srcValue = "";
		String tgtValue = "";
		
		this.mapField.put(fullName, FieldInfo.builder()
				.idx(this.idx)
				.fullName(fullName)
				.lastName(lastName)
				.size(size)
				.srcValue(srcValue)
				.tgtValue(tgtValue)
				.build());
		
		this.idx ++;
	}
	
	///////////////////////////////////////////////////////////////////////////
}
