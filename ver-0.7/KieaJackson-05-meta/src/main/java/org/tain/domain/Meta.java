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
@Table(name = "tb_meta"
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
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "size")
	private int size;
	
	@Column(name = "create_at")
	private LocalDateTime createAt = LocalDateTime.now();
	
	@Builder
	public Meta(
			String name,
			int size
			) {
		this.name = name;
		this.size = size;
	}
}
