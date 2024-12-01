package com.clem.movieapi.service;

import com.clem.movieapi.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer id);

    List<MovieDto> getAllMovies();

    MovieDto updateMovie(Integer id, MovieDto movieDto, MultipartFile file) throws IOException;

    String deleteMovie(Integer id) throws IOException;

}
