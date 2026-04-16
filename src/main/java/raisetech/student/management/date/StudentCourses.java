package raisetech.student.management.date;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter

@JsonPropertyOrder({
    "id",
    "studentsId",
    "courseName",
    "startDate",
    "expectedEndDate"
})

public class StudentCourses {

  private String id;
  private String studentsId;
  private String courseName;
  private LocalDate startDate;
  private LocalDate expectedEndDate;

  public StudentCourses(){
  }

}
