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

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stmt"
	, indexes = {
			@Index(name = "stmt_idx_1", unique = false, columnList = "group_no"),
			@Index(name = "stmt_idx_2", unique = true, columnList = "seq_no"),
			@Index(name = "stmt_idx_3", unique = true, columnList = "group_no,seq_no"),
	}
)
@SequenceGenerator(name = "stmt_seq"
	, sequenceName = "stmt_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
public class Stmt {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stmt_seq")
	private Long id;
	
	@Column(name = "group_no")
	private int groupNo;
	
	@Column(name = "seq_no")
	private int seqNo;
	
	@Column(name = "stmt_en", length = 1024)
	private String stmtEn;
	
	@Column(name = "stmt_kr", length = 1024)
	private String stmtKr;
	
	@Column(name = "create_at")
	private LocalDateTime createAt = LocalDateTime.now();
	
	private Stmt(int groupNo, int seqNo, String stmtEn, String stmtKr) {
		this.groupNo = groupNo;
		this.seqNo = seqNo;
		this.stmtEn = stmtEn;
		this.stmtKr = stmtKr;
	}
	
	public static Stmt join(int groupNo, int seqNo, String stmtEn, String stmtKr) {
		return new Stmt(groupNo, seqNo, stmtEn, stmtKr);
	}
}