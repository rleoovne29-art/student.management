package raisetech.student.management.date;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
  private String name;
  private LocalDateTime startDate;
  private LocalDateTime expectedEndDate;

}
