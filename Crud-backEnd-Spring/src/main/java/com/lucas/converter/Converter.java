package com.lucas.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.lucas.enums.Category;
import com.lucas.model.Course;
import com.lucas.model.Lesson;
import com.lucas.request.CourseRequest;
import com.lucas.response.CourseResponse;
import com.lucas.response.LessonResponse;

/**
 * Utility class for converting between entities and response DTOs using
 * ModelMapper.
 */
@Component
public class Converter {

    private final ModelMapper modelMapper;

    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convert a source object to an instance of the target type.
     *
     * @param source      the source object
     * @param targetClass the class of the target type
     * @param <S>         source type
     * @param <T>         target type
     * @return mapped instance of target type
     */
    public <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, targetClass);
    }

    public CourseResponse convertToResponse(Course course) {
        if (course == null) {
            return null;
        }


        CourseResponse courseResponse = new CourseResponse(course.getId(), course.getName(), course.getCategory().getValue(),this.convertToLessonResponseList(course.getLessons()));
        return courseResponse;
    }

    public List<LessonResponse> convertToLessonResponseList(List<Lesson> lessons) {
       
      List<LessonResponse>  lessonsResponse = lessons
                                        .stream()
                                        .map(lesson -> new LessonResponse(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                                        .collect(Collectors.toList());
        return lessonsResponse;
                                            
    }

    public Course convertToEntity(CourseRequest request) {

        if (request == null) {
            return null;
        }

        Course course = new Course();

        if (request.id() != null) {
            course.setId(request.id());
        }
        course.setName(request.name());
        course.setCategory(this.convertToCategory(request.category()));

        return course;
    }

    public Category convertToCategory(String value) {
        if (value == null) {
            return null;
        }
      return switch (value) {
        case "Front-end" ->  Category.FRONT_END;
        case "Back-end" ->   Category.BACK_END ;
       
        default -> throw new IllegalArgumentException("Categoria inválida: " + value);
       };
    }

    /**
     * Convert a list of source objects to a list of target type instances.
     *
     * @param sourceList  list of source objects
     * @param targetClass the class of the target type
     * @param <S>         source type
     * @param <T>         target type
     * @return list of mapped target type instances
     */
    public <S, T> List<T> convertList(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return null;
        }
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * Convert a Page of source objects to a Page of target type instances.
     *
     * @param sourcePage  Page of source objects
     * @param targetClass the class of the target type
     * @param <S>         source type
     * @param <T>         target type
     * @return Page of mapped target type instances
     */
    public <S, T> Page<T> convertPage(Page<S> sourcePage, Class<T> targetClass) {
        if (sourcePage == null) {
            return null;
        }
        List<T> converted = sourcePage.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
        return new PageImpl<>(converted, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
