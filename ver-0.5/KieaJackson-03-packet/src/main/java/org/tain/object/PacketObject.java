package org.tain.object;

import lombok.Data;

@Data
public class PacketObject {

	private String grpCode;
	private int seqNo;
	private String name;
	private int size;
	private String align;
	private String type;
	private String description;
}
