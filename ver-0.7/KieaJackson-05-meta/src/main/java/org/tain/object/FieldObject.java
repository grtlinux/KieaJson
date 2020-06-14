package org.tain.object;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FieldObject {

	private int idx;
	private String fullName;
	private String lastName;
	private int size;
	private String srcValue;
	private String tgtValue;
	
	@Builder
	public FieldObject(
			int idx,
			String fullName,
			String lastName,
			int size,
			String srcValue,
			String tgtValue
			) {
		this.idx = idx;
		this.fullName = fullName;
		this.lastName = lastName;
		this.size = size;
		this.srcValue = srcValue;
		this.tgtValue = tgtValue;
	}
}
