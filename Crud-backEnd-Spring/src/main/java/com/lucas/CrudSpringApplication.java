package com.lucas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.lucas.enums.Category;
import com.lucas.model.Course;
import com.lucas.model.Lesson;
import com.lucas.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			Course course = new Course();
			course.setName("Angular com Spring");
			course.setCategory(Category.FRONT_END);

			Lesson lesson = new Lesson();
			lesson.setName("Introdução");
			lesson.setYoutubeUrl("watch?v=example");
			lesson.setCourse(course);

			course.getLessons().add(lesson);

			courseRepository.save(course);
		};
	}
}
