package raisetech.student.management.date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

  private String id;

  @NotBlank(message = "名前は必須です")
  private String name;

  private String kana;

  @Min(value = 1, message = "年齢は1以上で入力してください")
  private int age;

  private String nickname;

  private String email;

  private String region;

  private String gender;

  private String remark;

  private boolean deleted;

  public Student(){
  }

}
