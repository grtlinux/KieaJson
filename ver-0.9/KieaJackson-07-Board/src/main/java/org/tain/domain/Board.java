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

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_board"
	, indexes = {
			@Index(name = "board_idx_1", unique = false, columnList = "title"),
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
	@JsonIgnore
	private Long id;
	
	@Column(name = "title", length = 256)
	private String title;
	
	@Column(name = "sub_title", length = 256)
	private String subTitle;
	
	@Column(name = "content", length = 1024)
	private String content;
	
	@Column(name = "user_id", length = 32)
	private String userId;
	
	@Column(name = "created_date")
	//@JsonIgnore
	//@JsonProperty(access = Access.WRITE_ONLY)
	//@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate = LocalDateTime.now();

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
	}
}
