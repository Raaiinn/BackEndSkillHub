package com.skillhub.skillhub.service;

import com.skillhub.skillhub.domain.Course;
import com.skillhub.skillhub.domain.Progress;
import com.skillhub.skillhub.domain.User;
import com.skillhub.skillhub.domain.enums.ProgressStatus;
import com.skillhub.skillhub.repository.ProgressRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public Progress createProgress(Progress progress){
        progress.setStatus(ProgressStatus.STARTED);
        progress.setStartDate(new Date(System.currentTimeMillis()));
        return progressRepository.save(progress);
    }

    public Progress updateProgress(Integer id, Progress progress){
        if(progressRepository.findById(id).orElse(null) == null){
            return null;
        }
        progress.setStatus(ProgressStatus.COMPLETED);
        progress.setEndDate(new Date(System.currentTimeMillis()));
        return progressRepository.save(progress);
    }

    public List<Progress> listProgress(Integer userId) {
        return progressRepository.findAllByUser_Id(userId);
    }

    public List<Progress> listAll(){
        return progressRepository.findAll();
    }

    public Progress findById(Integer id) {
        return progressRepository.findById(id).orElse(null);
    }

    public Progress findByUserAndCourse(User user, Course course) {
        return progressRepository.findByUserAndCourse(user, course).orElse(null);
    }
}
