package raisetech.student.management.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるcontrollerです。
 */
@Validated
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧(全件)
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() throws  TestException {
    throw new TestException(
            "global",
            null,
            "現在のこのAPIは使用できません。URLは「studentList」ではなく、「students」を利用してください。"
    );
    //return service.searchStudentList();
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(
          @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.getStudentDetail(id);
  }

  /**
   * 例外処理の練習用です。
   * @return HTTP/1.1 400 Bad Request 練習用のエラーです
   * @throws TestException
   */
  @GetMapping("/test-error")
  public String testError() throws TestException {
    throw new TestException(
            null,
            null,
            "練習用のエラーです"
    );
  }

  /**
   * 受講生の詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail response = service.registerStudentDetail(studentDetail);
    return ResponseEntity.ok(response);
  }

  /**
   * 受講生詳細の更新を行います。
   * キャンセルフラグの更新もここで行います。(論理削除)
   *
   * @param studentDetail 受講生情報
   * @return 受講生情報
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudentDetail(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

}

