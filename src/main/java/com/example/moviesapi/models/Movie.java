package com.example.moviesapi.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	@Column(name = "release_year", length = 4)
	private Year releaseYear;
	@Column(name = "length")
	private Short length;
	@Enumerated(EnumType.STRING)
	@Column(length = 5)
	private Rating rating;
	@Column(name = "created_at")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;
	
	@ManyToMany(mappedBy = "movies")
	private List<Actor> actors = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(
			name = "movie_category",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private List<Category> categories = new ArrayList<>();
	
	@ManyToMany(mappedBy = "movies")
	private List<Director> directors = new ArrayList<>();
	
	@PrePersist
	protected void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
}

enum Rating{
	G("G"),
	PG("PG"),
	PG13("PG-13"),
	R("R"),
	NC17("NC-17");
	
	private final String option;
	Rating(String option) {
		this.option = option;
	}
	
	public String getOption() {
		return option;
	}
}
