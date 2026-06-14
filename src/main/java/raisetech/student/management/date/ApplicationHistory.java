package raisetech.student.management.date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "受講生申込履歴")
@Getter
@Setter

@JsonPropertyOrder(value = {
        "id",
        "applicationId",
        "status",
        "changedAt",
        "changedBy",
        "remark"
})

public class ApplicationHistory {
    private String id;
    private String applicationId;
    private String status;
    private LocalDateTime changedAt;
    private String changedBy;
    private String remark;
}
