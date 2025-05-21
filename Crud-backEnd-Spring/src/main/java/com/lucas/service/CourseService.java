package com.lucas.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.lucas.model.Course;
import com.lucas.repository.CourseRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Optional<Course> search(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        Course newCourse = new Course();
        newCourse.setName(course.getName());
        newCourse.setCategory(course.getCategory());

        return courseRepository.save(newCourse);

    }
}
