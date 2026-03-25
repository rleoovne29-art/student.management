package raisetech.student.management.date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@JsonPropertyOrder({
    "id",
    "name",
    "kana",
    "age",
    "nickname",
    "email",
    "region",
    "gender",
    "remark",
    "deleted"
})

public class Student {

  @Pattern(regexp = "^\\d+$")
  private String id;

  @NotBlank(message = "名前は必須です")
  private String name;

  @NotBlank
  private String kana;

  @Min(value = 1, message = "年齢は1以上で入力してください")
  private int age;

  @NotBlank
  private String nickname;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String region;

  @NotBlank
  private String gender;

  private String remark;

  private boolean deleted;

  public Student(){
  }

}
