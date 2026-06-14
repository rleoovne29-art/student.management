package raisetech.student.management.date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "管理者")
@Getter
@Setter

@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "password",
        "role",
        "createdAt",
        "updatedAt"
})

public class Admin {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
