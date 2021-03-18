package com.orange.bookstore.category;

import javax.validation.constraints.NotBlank;

public class CategoryForm {
	
	@NotBlank
	private String name;
	
	public Category toModel() {
		return new Category(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
