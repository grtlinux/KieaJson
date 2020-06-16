package org.tain.jobs;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.tain.object.MetaObject;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobMetaInfo {

	private String jsonMetaPath;
	private Map<String, MetaObject> map;
	
	public JobMetaInfo(String jsonMetaPath) {
		this.jsonMetaPath = jsonMetaPath;
		this.map = new LinkedHashMap<>();
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	public Map<String, MetaObject> get() throws Exception {
		log.info("KANG-20200614 >>>>> {} {}", CurrentInfo.get(), this.jsonMetaPath);
		
		if (!Flag.flag) {
			// single file
			// parsing the json data file
			JsonNode jsonNode = new ObjectMapper().readValue(new File(this.jsonMetaPath), JsonNode.class);
			String prefix = "";
			_parsing(prefix, jsonNode);
		}
		
		if (!Flag.flag) {
			// TEST
			Path path3 = Paths.get(this.jsonMetaPath);
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path3);
			for(Path path : directoryStream) {
				if(Files.isDirectory(path)) {
					//System.out.println("[[디렉토리]] " + path.getFileName());
				} else {
					System.out.printf("파일 >> %s %d %s%n", path.getFileName(), Files.size(path), path.toAbsolutePath().toString());
				}
			}
		}
		
		if (Flag.flag) {
			// multi files
			// MAIN: SUCCESS
			Path paths = Paths.get(this.jsonMetaPath);
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(paths);
			for (Path path : directoryStream) {
				if (Files.isReadable(path)) {
					if (!Flag.flag) System.out.println(">>>>> " + path.toAbsolutePath().toString());
					
					if (Flag.flag) {
						// parsing the json data file
						JsonNode jsonNode = new ObjectMapper().readValue(new File(path.toAbsolutePath().toString()), JsonNode.class);
						String prefix = "";
						_parsing(prefix, jsonNode);
					}
				}
			}
			
			if (!Flag.flag) {
				// print map
				for (Map.Entry<String, MetaObject> entry : this.map.entrySet()) {
					String key = entry.getKey();
					MetaObject obj = (MetaObject) entry.getValue();
					System.out.printf(">>>>> %s = %s%n", key, obj);
				}
			}
		}
		
		return this.map;
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
	
	private void _processLeafNode(String prefix, String value) {
		if (!Flag.flag) log.info("KANG-20200614 >>>>> {} = {}", prefix, value);
		
		int idx = prefix.lastIndexOf("/");
		if (idx >= 0) {
			prefix = prefix.substring(idx + 1);
		}

		MetaObject meta = null;
		int lenValue = value.length();
		if (prefix.contains(".arrSize")) {
			lenValue = 4;
		} else {
			meta = (MetaObject) this.map.get(prefix);
			if (meta != null) {
				lenValue = Math.max(lenValue, meta.getSize());
			}
			lenValue = (lenValue + 9) / 10 * 10;
		}
		if (!Flag.flag) log.info("KANG-20200614 >>>>> {} = {}", prefix, lenValue);
		
		meta = MetaObject.builder().name(prefix).size(lenValue).build();
		this.map.put(prefix, meta);
	}
}
