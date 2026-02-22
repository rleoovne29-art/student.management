package raisetech.student.management;

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
    "gender"
})

public class Student {

  private String id;
  private String name;
  private String kana;
  private int age;
  private String nickname;
  private String email;
  private String region;
  private String gender;

  public Student(){
  }

}
