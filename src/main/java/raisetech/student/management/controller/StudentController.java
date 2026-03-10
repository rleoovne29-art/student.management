package raisetech.student.management.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    students.forEach(
        s -> System.out.println("Student: " + s.getId() + " / " + s.getName())
    );
    studentsCourses.forEach(
        c -> System.out.println("Course: " + c.getStudentId() + " / " + c.getCourseName())
    );

    studentsCourses.forEach(c ->
        System.out.println(
            "Course: " + c.getStudentId()
                + " / " + c.getCourseName()
                + " / start=" + c.getStartDate()
                + " / end=" + c.getExpectedEndDate()
        )
    );

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

  @GetMapping("/student/{id}")
  public String showStudent(@PathVariable String id, Model model) {
    Student student = service.searchStudentById(id);
    List<StudentsCourses> courses = service.searchStudentsCourseList()
        .stream()
        .filter(c -> c.getStudentId().equals(id))
        .toList();
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentsCourses(courses);

    for (StudentsCourses sc : courses) {
      System.out.println("GET時のコース: index=" + courses.indexOf(sc)
          + " / id=" + sc.getId()
          + " / studentId=" + sc.getStudentId()
          + " / courseName=" + sc.getCourseName());
    }

    model.addAttribute("studentDetail", detail);
    return "student";
  }

  @GetMapping("/updateStudent/{id}")
  public String showUpdateStudent(@PathVariable String id, Model model) {
    Student student = service.searchStudentById(id);
    List<StudentsCourses> courses = service.searchStudentsCourseList()
        .stream()
        .filter(c -> c.getStudentId().equals(id))
        .toList();
    for (StudentsCourses sc : courses) {
      sc.setStudentId(id);
    }
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentsCourses(courses);

    for (int i = 0; i < detail.getStudentsCourses().size(); i++) {
      StudentsCourses sc = detail.getStudentsCourses().get(i);
      System.out.println("Thymeleafに渡すコース: index=" + i
          + " / id=" + sc.getId()
          + " / studentId=" + sc.getStudentId()
          + " / courseName=" + sc.getCourseName());
    }

    for (StudentsCourses sc : courses) {
      System.out.println(
          "GET時のコース: id=" + sc.getId()
              + " / courseName=" + sc.getCourseName()
              + " / startDate=" + sc.getStartDate()
              + " / expectedEndDate=" + sc.getExpectedEndDate()
      );
    }


    model.addAttribute("studentDetail", detail);
    return "updateStudent";
  }


  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail,
      BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    //新規受講生登録情報を登録する処理を実装する。
    Student student = studentDetail.getStudent();
    String id = service.generateRandomId();
    student.setId(id);
    service.registerStudent(student);
    //コース情報も一緒に登録できるように実装する。コースは単体でいい。
    StudentsCourses sc = studentDetail.getStudentsCourses().get(0);
    sc.setStudentId(id);
    service.registerStudentsCourses(sc);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result) {

    System.out.println("POST受信 studentsCourses size=" + studentDetail.getStudentsCourses().size());
    for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
      System.out.println("POST受信: id=" + sc.getId()
          + " / studentId=" + sc.getStudentId()
          + " / courseName=" + sc.getCourseName()
          + " / startDate=" + sc.getStartDate()
          + " / expectedEndDate=" + sc.getExpectedEndDate());
    }

    if (result.hasErrors()) {
      return "updateStudent";
    }
    Student student = studentDetail.getStudent();
    service.updateStudent(student);
    for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
      service.updateStudentsCourses(sc);
    }
    return "redirect:/studentList";
  }
}

