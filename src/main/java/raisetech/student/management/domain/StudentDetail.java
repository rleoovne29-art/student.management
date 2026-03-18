package raisetech.student.management.domain;

import java.util.List;

import lombok.*;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  private Student student;
  private List<StudentCourses> studentCourses;

}
