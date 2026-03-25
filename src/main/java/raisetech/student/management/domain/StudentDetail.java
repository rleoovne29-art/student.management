package raisetech.student.management.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.*;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;
  @Valid
  private List<StudentCourses> studentCourses = new ArrayList<>();

}
