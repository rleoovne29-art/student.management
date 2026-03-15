package raisetech.student.management.domain;

import java.util.List;

import lombok.*;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentsCourses;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;

}
