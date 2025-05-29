package com.lucas.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.lucas.model.Course;
import com.lucas.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Course searchById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404),
                        "Course não encontrado com o id: " + id));
    }

    public Course create(Course course) {
        Course newCourse = new Course();
        newCourse.setName(course.getName());
        newCourse.setCategory(course.getCategory());

        return courseRepository.save(newCourse);
    }

    public Course update(Long id, Course course) {

        Course courseUpdated = this.searchById(id);

        courseUpdated.setName(course.getName());
        courseUpdated.setCategory(course.getCategory());

        return courseRepository.save(courseUpdated);
    }

    public void destroy(Long id) {
        // Verifica se o curso existe antes de tentar excluí-lo
        // Se não existir, lança uma exceção
        Course course = this.searchById(id);
        if (course != null) {
            courseRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Course não encontrado com o id: " + id);
        }
    }

}
