package org.tain.object;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroupObject {

	private String grpCode;
	private String grpName;
	private String name;
	private String type;
	private String description;
	private String content;
	
	@Builder
	public GroupObject(
			String grpCode,
			String grpName,
			String name,
			String type,
			String description,
			String content
			) {
		this.grpCode = grpCode;
		this.grpName = grpName;
		this.name = name;
		this.type = type;
		this.description = description;
		this.content = content;
	}
}
