package com.orange.bookstore.author;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthorForm {
	
	@NotEmpty
	private String name;
	
	@Email
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(max=400)
	private String description;
	
	public AuthorForm(String name, String email, String description) {
		this.name = name;
		this.email = email;
		this.description = description;
	}
	
	public Author toModel() {
		return new Author(name, email, description);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
