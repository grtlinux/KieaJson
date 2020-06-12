package org.tain.domain;

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
@Table(name = "test07"
	, indexes = {
			@Index(name = "idx_1", unique = true, columnList = "name")
	}
)
@SequenceGenerator(name = "test07_seq"
	, sequenceName = "test07_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
public class Test07 {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test07_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "size")
	private int size;
	
	@Column(name = "sort_no")
	private int sortNo;
	
	@Column(name = "org_data")
	private String orgData;
	
	@Column(name = "fix_data")
	private String fixData;
	
	@Builder
	public Test07(
			String name,
			String type,
			int size,
			int sortNo
			){
		this.name = name;
		this.type = type;
		this.size = size;
		this.sortNo = sortNo;
		this.orgData = "";
		this.fixData = "";
	}
}
