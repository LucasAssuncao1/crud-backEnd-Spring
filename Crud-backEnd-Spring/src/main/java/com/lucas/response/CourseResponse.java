package com.lucas.response;

import java.util.List;

import org.hibernate.validator.constraints.Length;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseResponse(
                Long id,
                @NotBlank @NotNull @Length(min = 3, max = 25) String name,
                @NotNull @Length(max = 25) String category,
                @NotNull @NotBlank List<LessonResponse> lessons) {
}
