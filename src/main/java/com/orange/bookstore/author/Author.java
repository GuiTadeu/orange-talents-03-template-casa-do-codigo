package com.orange.bookstore.author;

import java.time.LocalDateTime;	
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Author {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@Email
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(max=400)
	private String description;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public Author(@NotEmpty String name, @Email @NotEmpty String email, @NotEmpty @Size(max=400) String description) {
		this.name = name;
		this.email = email;
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getDescription() {
		return description;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
