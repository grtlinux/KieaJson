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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_fieldinfo"
	, indexes = {
			@Index(name = "idx_1", unique = true, columnList = "idx"),
			@Index(name = "idx_2", unique = true, columnList = "full_name"),
	}
)
@SequenceGenerator(name = "fieldinfo_seq"
	, sequenceName = "fieldinfo_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"id", "create_at"})
public class FieldInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fieldinfo_seq")
	@Column(name = "id")
	@JsonIgnore
	private Long id;
	
	@Column(name = "idx")
	private int idx;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "size")
	private int size;
	
	@Column(name = "src_value")
	private String srcValue;
	
	@Column(name = "tgt_value")
	private String tgtValue;
	
	@Column(name = "create_at")
	@JsonIgnore
	private LocalDateTime createAt = LocalDateTime.now();
	
	@Builder
	public FieldInfo(
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
