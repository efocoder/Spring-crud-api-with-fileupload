package com.clem.movieapi.service;

import com.clem.movieapi.dto.MovieDto;
import com.clem.movieapi.entities.Movie;
import com.clem.movieapi.reposittories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
       if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
           throw new RuntimeException("File already exists, please enter another file name");
       }
        var uploadedFile =  fileService.uploadFile(path, file);
       movieDto.setPoster(uploadedFile);
        Movie movie = new Movie(
               null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        Movie savedMovie = movieRepository.save(movie);
        String posterUrl = baseUrl + "/file/" + uploadedFile;
        return new MovieDto(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
    }

    @Override
    public MovieDto getMovie(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new RuntimeException("Movie not found"));
        String posterUrl = baseUrl + "/file/" + movie.getPoster();
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<MovieDto>();
        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer id, MovieDto movieDto, MultipartFile file) throws IOException {
        Movie mv = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        String filename = mv.getPoster();
        if(file!= null){
            Files.deleteIfExists(Paths.get(path + File.separator + filename));
            filename = fileService.uploadFile(path, file);
        }
        movieDto.setPoster(null);
        Movie movie= new Movie(
                mv.getId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        var updatedMovie = movieRepository.save(movie);
        String posterUrl = baseUrl + "/file/" + filename;

        return new MovieDto(
                updatedMovie.getId(),
                updatedMovie.getTitle(),
                updatedMovie.getDirector(),
                updatedMovie.getStudio(),
                updatedMovie.getMovieCast(),
                updatedMovie.getReleaseYear(),
                updatedMovie.getPoster(),
                posterUrl
        );
    }

    @Override
    public String deleteMovie(Integer id) throws IOException {
        Movie mv = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));
       movieRepository.delete(mv);
        return "Movie deleted successfully";
    }
}
