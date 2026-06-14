package raisetech.student.management.date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Schema (description = "受講生申込の最新状態")
@Getter
@Setter

@JsonPropertyOrder(value = {
        "id",
        "studentsCoursesId",
        "status",
        "createdAt",
        "updatedAt",
        "deletedAt"
})

public class Application {
    private  String id;
    private String studentsCoursesId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
