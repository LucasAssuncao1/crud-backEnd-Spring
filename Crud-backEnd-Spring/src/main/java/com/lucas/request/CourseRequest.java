package com.lucas.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.lucas.enums.Category;
import com.lucas.enums.ValueOfEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CourseRequest(
        Long id,
        @NotBlank @NotNull @Length(min = 3, max = 25) String name,
        @NotNull @Length(max = 25) @ValueOfEnum(enumClass = Category.class) String category,
        @NotNull  @NotEmpty @Valid List<LessonRequest> lessons
        ) {

}
