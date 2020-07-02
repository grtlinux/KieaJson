package org.tain.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tain.annotation.ColumnPosition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_board"
	, indexes = {
			@Index(name = "board_title_idx", unique = false, columnList = "title"),
			@Index(name = "board_userid_idx", unique = false, columnList = "user_id"),
	}
)
@SequenceGenerator(name = "board_seq"
	, sequenceName = "board_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
//@JsonIgnoreProperties({"id", "created_date"})
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
	@Column(name = "id")
	@ColumnPosition(1)
	@JsonIgnore
	private Long id;
	
	@Column(name = "title", length = 256)
	@ColumnPosition(2)
	private String title;
	
	@Column(name = "sub_title", length = 256)
	@ColumnPosition(3)
	private String subTitle;
	
	@Column(name = "content", length = 1024)
	@ColumnPosition(4)
	private String content;
	
	@Column(name = "user_id", length = 32)
	@ColumnPosition(5)
	private String userId;
	
	@Column(name = "created_date")
	@ColumnPosition(6)
	//@JsonIgnore
	//@JsonProperty(access = Access.WRITE_ONLY)
	//@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate = LocalDateTime.now();

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "job_date")
	@ColumnPosition(7)
	private Date jobDate;
	
	@JsonIgnore
	//@Temporal(TemporalType.TIMESTAMP)   // ERROR
	@Column(name = "work_date")
	private Timestamp workDate;

	@Builder
	public Board(
			String title,
			String subTitle,
			String content,
			String userId
			) {
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.userId = userId;
		this.jobDate = new Date();
		//this.workDate = new Timestamp(this.jobDate.getTime());
		this.workDate = new Timestamp(System.currentTimeMillis());
	}
}
