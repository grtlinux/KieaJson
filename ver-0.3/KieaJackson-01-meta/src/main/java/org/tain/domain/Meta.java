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
@Table(name = "meta"
	, indexes = {
			@Index(name = "idx_1", unique = true, columnList = "name")
	}
)
@SequenceGenerator(name = "meta_seq"
	, sequenceName = "meta_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
public class Meta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meta_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", length = 128)
	private String name;
	
	@Column(name = "size")
	private Integer size;
	
	@Column(name = "align", length = 3)
	private String align;
	
	@Column(name = "description", length = 1024)
	private String description;
	
	@Column(name = "create_at")
	private LocalDateTime createAt = LocalDateTime.now();
	
	@Builder
	public Meta(
			String name,
			Integer size,
			String align,
			String description
			) {
		this.name = name;
		this.size = size;
		this.align = align;
		this.description = description;
	}
}
