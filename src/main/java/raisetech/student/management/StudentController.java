package raisetech.student.management;

import java.util.List;
import java.util.Optional;
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
    return ResponseEntity.ok(students);
  }

  @PostMapping
  public ResponseEntity<String> registerStudent(
      @RequestParam String name,
      @RequestParam int age,
      @RequestParam String job
  ) {
    boolean result = service.registerStudent(name, age, job);
    if (result){
      return ResponseEntity.ok("登録成功。ID=" + service.getLastInsertedId());
    }else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("登録できませんでした。");
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<String> updateStudentName(
      @PathVariable Integer id,
      @RequestParam String name,
      @RequestParam int age,
      @RequestParam String job
  ) {
    boolean updated = service.updateStudent(id, name, age, job);
    if (updated) {
      return ResponseEntity.ok(name + " さんを更新しました。");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("見つかりませんでした。");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteStudent(@PathVariable String id) {
    boolean deleted = service.deleteStudent(id);
    if (deleted) {
      return ResponseEntity.ok(id + "さんを削除しました");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(id + "さんは、見つかりませんでした。");
    }
  }
}

