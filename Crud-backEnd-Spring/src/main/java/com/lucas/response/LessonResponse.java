package com.lucas.response;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

public record LessonResponse(
    
    Long id,

    @NotNull @Length(max = 100) String name,

    @NotNull @Length(max = 15) String youtubeUrl

    //  Course course
){
}
