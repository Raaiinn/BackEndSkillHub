package com.skillhub.skillhub.repository;

import com.skillhub.skillhub.domain.Course;
import com.skillhub.skillhub.domain.Progress;
import com.skillhub.skillhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    boolean existsByUserIdAndCourseId(Integer userId, Integer courseId);
    List<Progress> findAllByUser_Id(Integer userId);

    Optional<Progress> findByUserAndCourse(User user, Course course);
}
