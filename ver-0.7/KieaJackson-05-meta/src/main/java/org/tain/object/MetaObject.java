package org.tain.object;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MetaObject {

	private String name;
	private int size;
	
	@Builder
	public MetaObject(
			String name,
			int size
			) {
		this.name = name;
		this.size = size;
	}
}
