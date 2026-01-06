package com.skillhub.skillhub.controller;

import com.skillhub.skillhub.domain.Course;
import com.skillhub.skillhub.domain.Progress;
import com.skillhub.skillhub.domain.User;
import com.skillhub.skillhub.domain.responses.ErrorResponse;
import com.skillhub.skillhub.service.CourseService;
import com.skillhub.skillhub.service.ProgressService;
import com.skillhub.skillhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="${request-mapping.controller.client}")
@RestController
@RequestMapping("${request-mapping.controller.progress}")
public class ProgressController {

    private final ProgressService progressService;
    private final UserService userService;
    private final CourseService courseService;

    public ProgressController(ProgressService progressService, UserService userService, CourseService courseService) {
        this.progressService = progressService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @PostMapping("start")
    public ResponseEntity<?> create(@RequestBody Map<String, Integer> body) {
        Integer courseId = body.get("courseId");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User aux = userService.findByEmail(email).orElse(null);
        Course course = courseService.getCourseById(courseId);
        Progress progress = new Progress();
        progress.setCourse(course);
        progress.setUser(aux);
        if(progress == null | progress.getUser() == null | progress.getCourse() == null) {
            return new ResponseEntity<>(new ErrorResponse("No es posible procesar la solicitud, por favor, verifique los datos e intente nuevamente", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(progressService.createProgress(progress), HttpStatus.CREATED);
        }
    }

    @PutMapping("complete")
    public ResponseEntity<?> complete(@RequestBody Map<String, Integer> body) {
        Integer courseId = body.get("courseId");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        Course course = courseService.getCourseById(courseId);
        Progress progress = progressService.findByUserAndCourse(user, course);
        Integer id = progress.getId();
        if(id == null || progress == null) {
            return new ResponseEntity<>(new ErrorResponse("La solicitud no ha podido ser procesada, por favor, revise los datos e intente nuevamente", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Progress aux = progressService.updateProgress(id, progress);
        if(aux == null) {
            return new ResponseEntity<>(new ErrorResponse("No se ha podido actualizar el progreso, por favor, verifique los datos e intente nuevamente", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> list(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User aux = userService.findByEmail(email).orElse(null);
        Integer userId = aux.getId();
        if(aux == null | userId == null) {
            return new ResponseEntity<>(new ErrorResponse("Usuario no disponible o no indicado, revise los datos e intente nuevamente", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }else{
            List<Progress> progress = progressService.listProgress(userId);
            return new ResponseEntity<>(progress, HttpStatus.OK);
        }
    }
}
