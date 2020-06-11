package org.tain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grp"
	, indexes = {
			@Index(name = "idx_1", unique = true, columnList = "grp_code")
	}
)
@SequenceGenerator(name = "grp_seq"
	, sequenceName = "grp_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
public class Grp {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grp_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "grp_code")
	private String grpCode;
	
	@Column(name = "grp_name")
	private String grpName;
	
	@Column(name = "direction")
	private String direction;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "create_at")
	private LocalDateTime createAt = LocalDateTime.now();
	
	@Builder
	public Grp(
			String grpCode,
			String grpName,
			String direction,
			String description
			) {
		this.grpCode = grpCode;
		this.grpName = grpName;
		this.direction = direction;
		this.description = description;
	}
}
