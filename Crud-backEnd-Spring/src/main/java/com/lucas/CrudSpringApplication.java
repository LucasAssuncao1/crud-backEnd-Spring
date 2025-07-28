package com.lucas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

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
	@Profile("dev")
	CommandLineRunner init(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			for(int i =1; i<21 ; i++) {

				
					Course course = new Course();
					course.setName("Angular com Spring " + i);
					course.setCategory(Category.FRONT_END);
					
					Lesson lesson = new Lesson();
					lesson.setName("Introdução");
					lesson.setYoutubeUrl("watch?v=example");
					lesson.setCourse(course);
					course.getLessons().add(lesson);
					
					Lesson lesson2 = new Lesson();
					lesson2.setName("Programação");
					lesson2.setYoutubeUrl("watch?v=example");
					lesson2.setCourse(course);
					course.getLessons().add(lesson2);
					
					courseRepository.save(course);
			}
		};
	}
}
