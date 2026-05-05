package raisetech.student.management.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "student",
        "studentCourses"
})
public class StudentDetail {

  @Valid
  private Student student;
  @Valid
  private List<StudentCourses> studentCourses = new ArrayList<>();

}
