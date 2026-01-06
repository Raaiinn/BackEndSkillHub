package com.skillhub.skillhub.controller;

import com.skillhub.skillhub.domain.Course;
import com.skillhub.skillhub.domain.responses.ErrorResponse;
import com.skillhub.skillhub.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins="${request-mapping.controller.client}")
@RestController
@RequestMapping("${request-mapping.controller.course}")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("list")
    public List<Course> list() {
        return courseService.listCourses();
    }
    @GetMapping("list/module")
    public ResponseEntity<?> get(@RequestParam String module) {
        if(module == null || module.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Modulo no indicado o vacio, por favor, revisa e intenta de nuevo", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }else{
            List<Course> aux = courseService.listCoursesByModule(module);
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
    }

    @GetMapping("get")
    public ResponseEntity<?> get(@RequestParam Integer id) {
        if(id == null){
            return new ResponseEntity<>(new ErrorResponse("Id no indicado o vacio, por favor, revisa e intenta de nuevo", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }else{
            Course aux = courseService.getCourseById(id);
            if(aux == null){
                return new ResponseEntity<>(new ErrorResponse("Curso no encontrado, por favor, revisa e intenta de nuevo", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(aux, HttpStatus.OK);
            }
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody Course course) {
        if(course == null){
            return new ResponseEntity<>(new ErrorResponse("El curso ingresado no es valido, por favor, revisa e intenta nuevamente", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<Course>(courseService.createCourse(course), HttpStatus.CREATED);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam Integer id, @RequestBody Course course) {
        if(id == null | course == null){
            return new ResponseEntity<>(new ErrorResponse("La solicitud no ha podido ser procesada, por favor, revise los datos e intente nuevamente", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Course aux = courseService.updateCourse(id, course);
        if (aux == null) {
            return new ResponseEntity<>(new ErrorResponse("No se ha encontrado el curso, por favor, verifique los datos e intente nuevamente", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        if (id == null) {
            return new ResponseEntity<>(new ErrorResponse("El curso a borrar no ha sido proporcionado, por favor, revise los datos e intente nuevamente", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        if(courseService.deleteCourse(id) == null){
            return new ResponseEntity<>(new ErrorResponse("El curso que desea borrar no existe, por favor, revise los datos e intente nuevamente", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
