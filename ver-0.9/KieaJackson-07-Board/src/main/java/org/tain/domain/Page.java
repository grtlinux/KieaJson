package org.tain.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"book"})
public class Page {

	@Id
	private Long pageNum;
	
	private String content;
	
	@ManyToOne
	private Book book;
}
