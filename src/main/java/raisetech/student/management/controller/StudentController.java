package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.date.Student;
import raisetech.student.management.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Student>> getStudentList(@RequestParam int minAge ,@RequestParam int maxAge) {
    List<Student> students = service.getAllStudents(minAge, maxAge);
    return ResponseEntity.ok(students);
  }
}

