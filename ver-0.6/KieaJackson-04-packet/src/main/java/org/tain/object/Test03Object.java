package org.tain.object;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Test03Object {

	private String name;
	private int size;
	private int sortNo;
	
	@Builder
	public Test03Object(
			String name,
			int size,
			int sortNo
			) {
		this.name = name;
		this.size = size;
		this.sortNo = sortNo;
	}
}
