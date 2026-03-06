package raisetech.student.management.date;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonPropertyOrder({
    "id",
    "studentId",
    "courseName",
    "startDate",
    "expectedEndDate"
})

public class StudentsCourses {

  private String id;
  private String studentId;
  private String courseName;
  private LocalDate startDate;
  private LocalDate expectedEndDate;

  public StudentsCourses(){
  }

}
