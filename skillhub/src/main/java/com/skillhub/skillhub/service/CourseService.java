package com.skillhub.skillhub.service;

import com.skillhub.skillhub.domain.Course;
import com.skillhub.skillhub.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> listCourses(){
        return courseRepository.findAll();
    }

    public List<Course> listCoursesByModule(String module){
        return courseRepository.findAllByModule(module);
    }

    public Course getCourseById(int id){
        return courseRepository.findById(id).orElse(null);
    }

    public Boolean existsCourseById(int id){
        return courseRepository.existsById(id);
    }

    public Course createCourse(Course course){
        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id, Course course){
        if(courseRepository.findById(id).orElse(null) == null){
            return null;
        }
        course.setId(id);
        return courseRepository.save(course);
    }

    public Course deleteCourse(Integer id) {
        Course aux = courseRepository.findById(id).orElse(null);
        if(aux == null){
            return null;
        }
        courseRepository.delete(aux);
        return aux;
    }
}