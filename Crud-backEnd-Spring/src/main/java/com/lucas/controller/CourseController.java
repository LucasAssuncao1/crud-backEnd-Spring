package com.lucas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.request.CourseRequest;
import com.lucas.response.CoursePageResponse;
import com.lucas.response.CourseResponse;
import com.lucas.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private  CourseService courseService;

    // public CourseController(CourseService courseService) {
    //     this.courseService = courseService;
    // }

    @GetMapping
    public CoursePageResponse list (@PageableDefault(page = 0, size = 10) Pageable paginacao) {
        if (paginacao.getPageSize() > 100) {
            throw new IllegalArgumentException("Page size must not exceed 100");
            
        }

        return courseService.list(paginacao);
    }

    // @GetMapping
    // public List<CourseResponse> list() {
    //     return courseService.list();
    // }

    @GetMapping("/{id}")
    public CourseResponse search(@PathVariable @NotNull @Positive Long id) {
        return courseService.searchById(id);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseResponse create(@RequestBody @Valid CourseRequest request) {
        return courseService.create(request);
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull CourseRequest request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        courseService.destroy(id);
    }
     
    

}
