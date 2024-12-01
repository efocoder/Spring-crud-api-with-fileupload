package com.clem.movieapi.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Integer id;
    @NotBlank(message = "Please enter a title")
    private String title;

    @NotBlank(message = "Please enter a director")
    private String director;

    @NotBlank(message = "Please enter a studio")
    private String studio;

    @JsonProperty("movie_cast")
    private Set<String> movieCast;

    @JsonProperty("release_year")
    private Integer releaseYear;

    @NotBlank(message = "Please enter a poster")
    private String poster;

    @NotBlank(message = "Please enter a poster url")
    private String poster_url;
}
