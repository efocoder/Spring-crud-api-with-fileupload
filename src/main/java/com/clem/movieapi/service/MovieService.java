package com.clem.movieapi.service;

import com.clem.movieapi.dto.MovieDto;
import com.clem.movieapi.dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer id);

    List<MovieDto> getAllMovies();

    MovieDto updateMovie(Integer id, MovieDto movieDto, MultipartFile file) throws IOException;

    String deleteMovie(Integer id) throws IOException;

    MoviePageResponse getMoviesWithPagination(Integer pageNumber, Integer pageSize);

    MoviePageResponse getMoviesWithPaginationAndSorted(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

}
