package com.clem.movieapi.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDtos,
                                Integer PageNumber,
                                Integer pageSize,
                                Long totalElements,
                                int totalPages,
                                boolean isLast) {
}
