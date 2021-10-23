package com.example.moviesapi.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "director")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Director implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name", length = 50)
	private String firstName;
	@Column(name = "last_name", length = 50)
	private String lastName;
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	@Column(name = "created_at")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;
	
	@ManyToMany
	@JoinTable(
			name = "movie_direction",
			joinColumns = @JoinColumn(name = "director_id"),
			inverseJoinColumns = @JoinColumn(name = "movie_id")
	)
	private List<Movie> movies = new ArrayList<>();
	
	@PrePersist
	protected void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
