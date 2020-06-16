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
@Table(name = "tb_group"
	, indexes = {
			@Index(name = "idx_1", unique = true, columnList = "grp_code"),
			@Index(name = "idx_2", unique = true, columnList = "grp_name"),
	}
)
@SequenceGenerator(name = "group_seq"
	, sequenceName = "group_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"id", "create_at"})
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
	@Column(name = "id")
	@JsonIgnore
	private Long id;
	
	@Column(name = "grp_code")
	private String grpCode;

	@Column(name = "grp_name")
	private String grpName;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "description", length = 1024)
	private String description;
	
	@Column(name = "content", length = 1024)
	private String content;
	
	@Column(name = "create_at")
	@JsonIgnore
	private LocalDateTime createAt = LocalDateTime.now();
	
	@Builder
	public Group(
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
