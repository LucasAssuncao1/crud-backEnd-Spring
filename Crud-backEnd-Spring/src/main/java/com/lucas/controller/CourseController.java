package com.lucas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.model.Course;
import com.lucas.service.CourseService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> list() {
        return courseService.list();
    }

    public Optional<Course> search(Long id){
        return courseService.search(id);
    }
    

    
}
