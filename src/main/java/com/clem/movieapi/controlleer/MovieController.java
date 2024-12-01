package com.clem.movieapi.controlleer;

import com.clem.movieapi.dto.MovieDto;
import com.clem.movieapi.dto.MoviePageResponse;
import com.clem.movieapi.exceptions.EmptyFileException;
import com.clem.movieapi.service.MovieService;
import com.clem.movieapi.utils.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestPart MultipartFile file, @RequestPart String movieDto) throws  EmptyFileException, IOException {
       if (file.isEmpty())  throw new EmptyFileException("File is empty");
       var dto = convertToMovieDto(movieDto);
       return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/paged")
    public ResponseEntity<MoviePageResponse> getAllMoviesPages(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer size
    ) {
        return ResponseEntity.ok(movieService.getMoviesWithPagination(page, size));
    }

    @GetMapping("/sorted")
    public ResponseEntity<MoviePageResponse> getAllMoviesSorted(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer size,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(movieService.getMoviesWithPaginationAndSorted(page, size, sortBy, sortOrder));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Integer id, @RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException {
        if (file.isEmpty()) file = null;
        var dto = convertToMovieDto(movieDto);
        return ResponseEntity.ok(movieService.updateMovie(id, dto, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Integer id) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(id));
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }
}

