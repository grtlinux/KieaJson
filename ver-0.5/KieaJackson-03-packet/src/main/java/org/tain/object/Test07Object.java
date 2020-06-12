package org.tain.object;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Test07Object {

	private String name;
	private String type;
	private int size;
	private int sortNo;
	private String orgData;
	private String fixData;
	
	@Builder
	public Test07Object(
			String name,
			String type,
			int size,
			int sortNo
			) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.sortNo = sortNo;
		this.orgData = "";
		this.fixData = "";
	}
}
