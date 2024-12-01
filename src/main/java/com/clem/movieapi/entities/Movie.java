package com.clem.movieapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length =200, name = "title")
    @NotBlank(message = "Please enter a title")
    private String title;

    @Column(name = "director", nullable = false, length = 120)
    @NotBlank(message = "Please enter a director")
    private String director;

    @Column(name = "studio", nullable = false, length = 120)
    @NotBlank(message = "Please enter a studio")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(name = "release_year", nullable = false, length = 120)
    private Integer releaseYear;

    @Column(name = "poster", nullable = false)
    @NotBlank(message = "Please enter a poster")
    private String poster;
}
