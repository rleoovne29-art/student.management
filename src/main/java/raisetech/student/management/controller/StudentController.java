package raisetech.student.management.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.response.ErrorResponse;
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
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。",
          tags = {"Student"},
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "受講生一覧の取得に成功しました。",
                          content = @Content(
                                  mediaType = "application/json",
                                  array = @ArraySchema(schema = @Schema(implementation = StudentDetail.class))
                          )
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "サーバー内部エラーが発生しました。"
                  )
          })
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Operation(summary = "受講生検索", description = "IDに紐づく受講生を検索します。",
          tags = {"Student"},
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "正常に取得できました"),
                  @ApiResponse(
                          responseCode = "404",
                          description = "指定された受講生が見つかりません"
                  )
          })
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
  @Operation(summary = "例外処理のエラー発生テスト",
          description = "必ず TestException を発生させる練習用の API です。",
          tags = {"ErrorTest"},
          responses = {
                  @ApiResponse(
                          responseCode = "400",
                          description = "練習用のエラーが発生しました。",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = ErrorResponse.class)
                          )
                  )
          })
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
  @Operation(summary = "受講生登録", description = "受講生登録をします。",
          tags = {"Student"},
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "受講生の登録に成功しました。",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = StudentDetail.class)
                          )
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "入力値が不正です。バリデーションエラーが発生しました。"
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "サーバー内部エラーが発生しました。"
                  )
          })
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
  @Operation(summary = "受講生詳細更新", description = "受講生詳細の更新をします。",
          tags = {"Student"},
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "更新処理が成功しました。",
                          content = @Content(
                                  mediaType = "text/plain",
                                  schema = @Schema(type = "string", example = "更新処理が成功しました。")
                          )
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "入力値が不正です。バリデーションエラーが発生しました。"
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "指定された受講生が存在しません。"
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "サーバー内部エラーが発生しました。"
                  )
          })
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudentDetail(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

}

