package org.tain.utils;

import java.util.Map;

import org.tain.domain.Group;
import org.tain.domain.Meta;
import org.tain.object.MetaObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintObject {

	public static void printMetaObject(Map<String, MetaObject> map) {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		for (Map.Entry<String, MetaObject> entry : map.entrySet()) {
			String key = entry.getKey();
			MetaObject obj = entry.getValue();
			if (Flag.flag) System.out.printf(">>>>> %s %s%n", key, obj);
		}
	}
	
	public static void printMeta(Map<String, Meta> map) {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		for (Map.Entry<String, Meta> entry : map.entrySet()) {
			String key = entry.getKey();
			Meta obj = entry.getValue();
			if (Flag.flag) System.out.printf(">>>>> %s %s%n", key, obj);
		}
	}
	
	public static void printGroup(Map<String, Group> map) {
		log.info("KANG-20200614 >>>>> {}", CurrentInfo.get());
		
		for (Map.Entry<String, Group> entry : map.entrySet()) {
			String key = entry.getKey();
			Group obj = entry.getValue();
			if (Flag.flag) System.out.printf(">>>>> %s %s%n", key, obj);
		}
	}
}
