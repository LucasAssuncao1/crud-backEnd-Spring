package com.lucas.response;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CourseResponse(
                Long id,
                @NotBlank @NotNull @Length(min = 3, max = 25) String name,
                @NotNull @Length(max = 25) @Pattern(regexp = "Back-end|Front-end") String category) {
}
