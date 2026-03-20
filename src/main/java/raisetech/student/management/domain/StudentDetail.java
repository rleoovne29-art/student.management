package raisetech.student.management.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourses> studentCourses;

  public StudentDetail(Student student, List<StudentCourses> courses) {
    this.student = student != null ? student : new Student();
    this.studentCourses = courses != null ? courses : new ArrayList<>();
  }

}
