package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@Controller
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

    model.addAttribute("studentList",
        converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping ("/studentsCourseList")
  public ResponseEntity<List<StudentsCourses>> getStudentsCourseList() {
    List<StudentsCourses> studentsCourse = service.searchStudentsCourseList();
    return ResponseEntity.ok(studentsCourse);
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    model.addAttribute("courseList", service.getAllCourses());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail StudentDetail,
      BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    //新規受講生登録情報を登録する処理を実装する。
    Student student = StudentDetail.getStudent();
    String id = service.generateRandomId();
    student.setId(id);
    service.registerStudent(student);
    //コース情報も一緒に登録できるように実装する。コースは単体でいい。
    StudentsCourses sc = StudentDetail.getStudentsCourses().get(0);
    sc.setStudentsId(id);
    service.registerStudentsCourses(sc);
    return "redirect:/studentList";
  }

}

