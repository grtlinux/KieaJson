package org.tain.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Book {

	@Id
	private String code;
	
	private String title;
	
	//@JoinColumn(name = "book_code")
	//@OneToMany(cascade = CascadeType.ALL)
	//@OneToMany(mappedBy = "board")
	//@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "book", targetEntity = Page.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Page> pages;
}
