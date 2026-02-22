package raisetech.student.management;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonPropertyOrder({
    "id",
    "studentsId",
    "courseName",
    "startDate",
    "expectedEndDate"
})

public class StudentCourse {

  private String id;
  private String studentsId;
  private String courseName;
  private LocalDate startDate;
  private LocalDate expectedEndDate;

  public StudentCourse(){
  }
}
