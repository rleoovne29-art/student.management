package raisetech.student.management.date;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
  private String name;
  private LocalDateTime startDate;
  private LocalDateTime expectedEndDate;

}
