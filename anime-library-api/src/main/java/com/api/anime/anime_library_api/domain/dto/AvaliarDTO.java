package com.api.anime.anime_library_api.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvaliarDTO(
        @Min(0)
        @Max(5)
        @NotNull
        Double nota
) {
}
