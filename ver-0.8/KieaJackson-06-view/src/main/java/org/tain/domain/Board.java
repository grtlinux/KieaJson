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
@Table(name = "tb_board")
@SequenceGenerator(name = "board_seq"
	, sequenceName = "board_seq"
	, initialValue = 1
	, allocationSize = 1
)
@NoArgsConstructor
@Data
public class Board {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String subTitle;
	
	@Column
	private String content;
	
	@Column
	private LocalDateTime createdDate;
	
	@Column
	private LocalDateTime updatedDate;
	
	@Builder
	public Board(
			String title,
			String subTitle,
			String content,
			LocalDateTime createdDate,
			LocalDateTime updatedDate
			) {
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}