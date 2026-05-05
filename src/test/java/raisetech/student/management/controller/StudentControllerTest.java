package raisetech.student.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.date.Student;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService service;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        when(service.searchStudentList()).thenReturn(List.of());
        mockMvc.perform(get("/studentList"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service,times(1)).searchStudentList();
    }

    @Test
    void 受講生詳細の検索が実行できて実行先のIDに対応する詳細が返ってくること() throws Exception{

        Student student = new Student();
        student.setId("1");
        student.setName("無馬かな");
        student.setKana("なしまかな");
        student.setAge(25);
        student.setNickname("なしま");
        student.setEmail("nasima@example.com");
        student.setRegion("東京");
        student.setGender("male");
        student.setRemark("");
        student.setDeleted(false);

        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourses(List.of());

        when(service.getStudentDetail("1")).thenReturn(detail);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(detail)));

        verify(service).getStudentDetail("1");
    }

    @Test
    void 受講生検索をしたときに必ず例外が発生すること() throws Exception{
        mockMvc.perform(get("/test-error"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{ \"status\": 400, " +
                                "\"message\": \"Test error occurred\", " +
                                "\"errors\": [ { \"field\": \"global\", " +
                                "\"message\": \"練習用のエラーです\" } ] }"
                ));

    }

    @Test
    void 受講生登録が成功し登録された受講生詳細が返ること() throws Exception{
        Student student = new Student();
        student.setId(null);
        student.setName("無馬かな");
        student.setKana("なしまかな");
        student.setAge(25);
        student.setNickname("なしま");
        student.setEmail("nasima@example.com");
        student.setRegion("東京");
        student.setGender("male");
        student.setRemark("");
        student.setDeleted(false);

        StudentDetail requestDetail = new StudentDetail();
        requestDetail.setStudent(student);
        requestDetail.setStudentCourses(new ArrayList<>());

        Student responseStudent = new Student();
        responseStudent.setId("1");
        responseStudent.setName("無馬かな");
        responseStudent.setKana("なしまかな");
        responseStudent.setAge(25);
        responseStudent.setNickname("なしま");
        responseStudent.setEmail("nasima@example.com");
        responseStudent.setRegion("東京");
        responseStudent.setGender("male");
        responseStudent.setRemark("");
        responseStudent.setDeleted(false);

        StudentDetail responseDetail = new StudentDetail();
        responseDetail.setStudent(responseStudent);
        responseDetail.setStudentCourses(new ArrayList<>());

        when(service.registerStudentDetail(any())).thenReturn(responseDetail);


        mockMvc.perform(
                        post("/registerStudent")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDetail))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDetail)));

        verify(service).registerStudentDetail(any());
    }

    @Test
    void 受講生更新が成功し成功したことがわかる文字列が返ること() throws Exception{
        String requestBody = "{ \"student\": { \"id\": null, \"name\": \"test\" }, \"studentCourses\": [] }";

        mockMvc.perform(put("/updateStudent")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("更新処理が成功しました。"));

        verify(service).updateStudentDetail(any());

    }

    @Test
    void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックにかかること(){
        Student student = new Student();
        student.setId("abc");
        student.setName("無馬かな");
        student.setKana("なしまかな");
        student.setAge(25);
        student.setNickname("なしま");
        student.setEmail("nasima@example.com");
        student.setRegion("東京");
        student.setGender("male");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("数字のみ入力するようにしてください。");
    }

}