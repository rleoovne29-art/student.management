package raisetech.student.management;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

  private final StudentService service;

  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/students")
  public ResponseEntity<List<Student>> getAllStudents() {
    List<Student> students = service.getAllStudents();
    if (students.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(students);
    } else {
      return ResponseEntity.ok(students);
    }
  }

  @PostMapping
  public ResponseEntity<String> registerStudent(
      @RequestParam String name,
      @RequestParam int age,
      @RequestParam String job
  ) {
    service.registerStudent(name, age, job);
    return ResponseEntity.ok(name + "さんを登録しました。");
  }

  @PatchMapping
  public ResponseEntity<String> updateStudentName(
      @RequestParam String name,
      @RequestParam int age,
      @RequestParam String job
  ) {
    boolean updated = service.updateStudent(name, age, job);
    if (updated) {
      return ResponseEntity.ok(name + " さんを更新しました。");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("見つかりませんでした。");
    }
  }

  @DeleteMapping("/{name}")
  public ResponseEntity<String> deleteStudent(@PathVariable String name) {
    boolean deleted = service.deleteStudent(name);
    if (deleted) {
      return ResponseEntity.ok(name + "さんを削除しました");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(name + "さんは、見つかりませんでした。");
    }
  }
}

