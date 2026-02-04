package raisetech.student.management;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
  private final StudentService service;
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentInfo")
  public String getStudent(){
    return service.getStudent();
  }

  @GetMapping("/students")
  public Map<String, Student> getStudents() {
    return service.getStudents();
  }

  @GetMapping("/student/{name}")
  public String getStudentDetail(@PathVariable String name){
    Student result = service.getStudentByName(name);
    if (result == null){
      return ("見つかりませんでした。");
    }else{
      return result.getName() + "さんは" + result.getAge() + "歳です。";
    }
  }

  @PostMapping("/studentInfo")
  public void setStudent(@RequestParam String oldName,@RequestParam String age){
    service.setStudent(oldName, age);
  }

  @PostMapping("/studentUpdate")
  public ResponseEntity<String> updateStudent(
      @RequestParam String oldName,
      @RequestParam String newName,
      @RequestParam String newAge
  ){
    boolean isUpdated = service.updateStudentName(oldName, newName, newAge);
    if (isUpdated) {
      return ResponseEntity.ok(oldName + " さんを " + newName + " さんに更新しました。");
    }else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("見つかりませんでした。");
    }
  }

  @PostMapping("/studentDelete")
  public ResponseEntity<String> deleteStudent(@RequestParam String name){
    if(service.deleteStudent(name)){
      return ResponseEntity.ok(name + "さんを削除しました");
    }else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(name + "さんは、見つかりませんでした。");
    }
  }

  @PostMapping("/studentAge")
  public void updateStudentAge(@RequestParam String age) {
    service.updateStudentAge(age);
  }

  @PostMapping("/student/add")
  public String addStudent(@RequestParam String name,@RequestParam String age){
    service.addStudent(name, age);
    return "登録しました。";
  }
}
