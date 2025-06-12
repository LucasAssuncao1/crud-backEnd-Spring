package com.lucas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lucas.converter.Converter;
import com.lucas.enums.Category;
import com.lucas.exception.ResourceNotFoundException;
import com.lucas.model.Course;
import com.lucas.repository.CourseRepository;
import com.lucas.request.CourseRequest;
import com.lucas.response.CourseResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {

    private final Converter converter;
    private CourseRepository courseRepository;

    public List<CourseResponse> list() {
        List<CourseResponse> courses = courseRepository.findAll()
                .stream()
                .map(course -> converter.convertToResponse(course))
                .collect(Collectors.toList());

        return courses;
    }

    public CourseResponse searchById(@NotNull @Positive Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return converter.convertToResponse(course);
    }

    public CourseResponse create(@Valid @NotNull CourseRequest request) {
        Course course = converter.convertToEntity(request);

        return converter.convertToResponse(courseRepository.save(course));

    }

    public CourseResponse update(@NotNull @Positive Long id, CourseRequest request) {

        Course courseUpdated = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        courseUpdated.setName(request.name());
        courseUpdated.setCategory(converter.convertToCategory(request.category()));

        return converter.convertToResponse(courseRepository.save(courseUpdated));
    }

    public void destroy(Long id) {
        // Verifica se o curso existe antes de tentar excluí-lo
        // Se não existir, lança uma exceção personalizada
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id)));
    }
}
