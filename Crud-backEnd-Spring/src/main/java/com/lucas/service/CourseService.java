package com.lucas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucas.converter.Converter;
import com.lucas.exception.ResourceNotFoundException;
import com.lucas.model.Course;
import com.lucas.repository.CourseRepository;
import com.lucas.request.CourseRequest;
import com.lucas.response.CoursePageResponse;
import com.lucas.response.CourseResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class CourseService {

    @Autowired
    private  Converter converter;

    @Autowired
    private CourseRepository courseRepository;

    public CoursePageResponse list(Pageable paginacao) {
        Page<Course> coursesPage = courseRepository.findAll(paginacao);

        List<CourseResponse> courseResponses = coursesPage.getContent()
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());

        return new CoursePageResponse(courseResponses, coursesPage.getTotalPages(), coursesPage.getTotalElements());
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

    public CourseResponse update(@Valid @NotNull @Positive Long id, CourseRequest request) {

        Course courseUpdated = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        Course course = converter.convertToEntity(request);

        courseUpdated.getLessons().clear();
        course.getLessons().forEach(lesson ->   courseUpdated.getLessons().add(lesson));
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
