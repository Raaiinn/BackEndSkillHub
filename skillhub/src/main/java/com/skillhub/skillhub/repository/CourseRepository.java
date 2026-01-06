package com.skillhub.skillhub.repository;

import com.skillhub.skillhub.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByModule(String module);
}
