package raisetech.student.management;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentCourseController {
  private final StudentCourseService service;

  public StudentCourseController(StudentCourseService service) {
    this.service = service;
  }

  @GetMapping ("/courses")
  public ResponseEntity<List<StudentCourse>> getStudentCourseList() {
    List<StudentCourse> studentCourses = service.getAllStudentsCourses();
    return ResponseEntity.ok(studentCourses);
  }
}
