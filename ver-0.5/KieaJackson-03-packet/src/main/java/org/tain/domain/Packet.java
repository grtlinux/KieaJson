package org.tain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "packet"
	//, indexes = {
	//		@Index(name = "idx_1", unique = true, columnList = "grpCode,seqNo")
	//}
)
@SequenceGenerator(name = "packet_seq"
	, sequenceName = "packet_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
public class Packet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packet_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "grp_code", length = 10)
	private String grpCode;
	
	@Column(name = "seq_no")
	private int seqNo;
	
	@Column(name = "name", length = 512)
	private String name;
	
	@Column(name = "size")
	private int size;
	
	// LEFT(TEXT) / RIGHT(NUMBER)
	@Column(name = "align", length = 16)
	private String align;
	
	// TEXT / NUMBER / ARRAY / SUBARRAY
	@Column(name = "type", length = 16)
	private String type;
	
	@Column(name = "description", length = 1024)
	private String description;
	
	@Column(name = "create_at")
	private LocalDateTime createAt = LocalDateTime.now();
	
	@Builder
	public Packet(
			String grpCode,
			int seqNo,
			String name,
			int size,
			String align,
			String type,
			String description
			) {
		this.grpCode = grpCode;
		this.seqNo = seqNo;
		this.name = name;
		this.size = size;
		this.align = align;
		this.type = type;
		this.description = description;
	}
}
