package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.service.StudentCourseService;

@RestController
@RequestMapping("/students")
public class StudentCourseController {
  private final StudentCourseService service;

  @Autowired
  public StudentCourseController(StudentCourseService service) {
    this.service = service;
  }

  @GetMapping ("/courses")
  public ResponseEntity<List<StudentCourse>> getStudentCourseList(String courseName) {
    List<StudentCourse> studentCourses = service.getAllStudentsCourses(courseName);
    return ResponseEntity.ok(studentCourses);
  }
}
