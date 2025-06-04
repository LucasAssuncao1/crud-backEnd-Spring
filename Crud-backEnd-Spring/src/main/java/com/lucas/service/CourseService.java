package com.lucas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lucas.converter.Converter;
import com.lucas.model.Course;
import com.lucas.repository.CourseRepository;
import com.lucas.request.CourseRequest;
import com.lucas.response.CourseResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {

    private final Converter converter;
    private CourseRepository courseRepository;

    public List<CourseResponse> list() {
        return converter.convertList(courseRepository.findAll(), CourseResponse.class);
    }

    public CourseResponse searchById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404),
                        "Course não encontrado com o id: " + id));

        return converter.convert(course, CourseResponse.class);
    }

    public CourseResponse create(CourseRequest request) {
        Course newCourse = new Course();
        newCourse.setName(request.getName());
        newCourse.setCategory(request.getCategory());

        return converter.convert(courseRepository.save(newCourse), CourseResponse.class);

    }

    public CourseResponse update(Long id, CourseRequest request) {

        Course courseUpdated = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404),
                        "Course não encontrado com o id: " + id));

        courseUpdated.setName(request.getName());
        courseUpdated.setCategory(request.getCategory());

        return converter.convert(courseRepository.save(courseUpdated), CourseResponse.class);
    }

    public void destroy(Long id) {
        // Verifica se o curso existe antes de tentar excluí-lo
        // Se não existir, lança uma exceção
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            courseRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Course não encontrado com o id: " + id);
        }
    }
}
