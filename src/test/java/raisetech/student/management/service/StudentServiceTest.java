package raisetech.student.management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.SomeException;
import raisetech.student.management.repository.StudentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    @Spy
    @InjectMocks
    private StudentService sut;


    @Test
    void ランダムID生成_8文字で構成され許可された文字のみで構成されること() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        String id = sut.generateRandomId();

        assertNotNull(id);
        assertFalse(id.isEmpty());
        assertEquals(8, id.length());

        for (char c : id.toCharArray()) {
            assertTrue(chars.contains(String.valueOf(c)));
        }
    }



    @Test
    void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること(){
        List<Student> studentList = new ArrayList<>();
        List<StudentCourses> studentCourseList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourses()).thenReturn(studentCourseList);

        sut.searchStudentList();

        verify(repository,times(1)).search();
        verify(repository,times(1)).searchStudentCourses();
        verify(converter,times(1)).convertStudentDetails(studentList,studentCourseList);
    }

    @Test
    void 受講生検索_受講生IDを指定して学生情報を1件取得すること(){
        Student expected = new Student();
        when(repository.searchStudentById(anyString())).thenReturn(expected);
        Student actual = sut.searchStudentById("1");
        verify(repository,times(1)).searchStudentById("1");
        assertSame(expected, actual);
    }

    @Test
    void 受講生検索_IDがnullの場合はnullを返すこと() {
        when(repository.searchStudentById(null)).thenReturn(null);
        Student actual = sut.searchStudentById(null);
        verify(repository, times(1)).searchStudentById(null);
        Assertions.assertNull(actual);
    }

    @Test
    void 受講生検索_受講生IDから受講生情報と受講コース情報を取得してまとめて返すこと(){
        Student student = new Student();
        List<StudentCourses> courses = List.of(new StudentCourses());
        StudentDetail expectedDetail = new StudentDetail();
        when(sut.searchStudentById("1")).thenReturn(student);
        when(sut.getCoursesByStudentId("1")).thenReturn(courses);
        when(sut.buildStudentDetail(student, courses)).thenReturn(expectedDetail);
        StudentDetail actual = sut.getStudentDetail("1");
        assertSame(expectedDetail, actual);

        verify(sut, times(1)).searchStudentById("1");
        verify(sut, times(1)).getCoursesByStudentId("1");
        verify(sut, times(1)).buildStudentDetail(student, courses);
    }

    @Test
    void 受講生検索_受講生が存在しない場合は学生情報と受講コース情報を返さずにnullを返すこと(){
        when(sut.searchStudentById("1")).thenReturn(null);
        StudentDetail actual = sut.getStudentDetail("1");
        Assertions.assertNull(actual);
        verify(sut, times(1)).searchStudentById("1");
        verify(sut, never()).getCoursesByStudentId("1");
        verify(sut, never()).buildStudentDetail(any(), any());
    }

    @Test
    void 受講生検索_受講生が存在するが受講生コースが存在しない場合は例外を返すこと() {
        Student student = new Student();
        when(sut.searchStudentById("1")).thenReturn(student);
        when(sut.getCoursesByStudentId("1"))
                .thenThrow(new SomeException("コース情報が取得できません"));

        assertThrows(SomeException.class, () -> sut.getStudentDetail("1"));

        verify(sut, times(1)).searchStudentById("1");
        verify(sut, times(1)).getCoursesByStudentId("1");
        verify(sut, never()).buildStudentDetail(any(), any());
    }

    @Test
    void 受講生検索_指定IDのコースだけが返ること() {
        StudentCourses c1 = new StudentCourses();
        c1.setStudentsId("1");
        c1.setCourseName(" ");

        StudentCourses c2 = new StudentCourses();
        c2.setStudentsId("2");
        c2.setCourseName(" ");

        List<StudentCourses> allCourses = List.of(c1, c2);
        when(repository.searchStudentCourses()).thenReturn(allCourses);
        List<StudentCourses> result = sut.getCoursesByStudentId("1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getStudentsId());
        verify(repository, times(1)).searchStudentCourses();
    }

    @Test
    void 受講生検索_渡した受講生詳細と受講コースがそのまま入ること(){
        Student student = new Student();
        List<StudentCourses> courses = List.of(new StudentCourses());

        StudentDetail result = sut.buildStudentDetail(student, courses);

        assertSame(student, result.getStudent());
        assertSame(courses, result.getStudentCourses());
    }

    @Test
    void 受講生登録_受講生詳細を受け取りリポジトリが1回呼ばれること(){
        Student student = new Student();
        sut.registerStudent(student);
        verify(repository, times(1)).insertStudent(student);
    }

    @Test
    void 受講生登録_受講生詳細とコースが登録されること(){
        Student student = new Student();
        StudentCourses sc = new StudentCourses();
        StudentDetail input = new StudentDetail(student, List.of(sc));
        when(sut.generateRandomId()).thenReturn("XP001");

        StudentDetail result = sut.registerStudentDetail(input);

        assertEquals("XP001", student.getId());
        assertEquals("XP001", sc.getStudentsId());
        assertSame(student, result.getStudent());
        assertSame(sc, result.getStudentCourses().get(0));

        verify(sut, times(1)).registerStudent(student);
        verify(sut, times(1)).registerStudentCourses(sc);
    }

    @Test
    void 受講生登録_受講コース詳細を受け取りリポジトリが1回呼ばれること(){
        StudentCourses sc = new StudentCourses();
        when(sut.generateRandomId()).thenReturn("XP001");
        sut.registerStudentCourses(sc);

        assertEquals("XP001", sc.getId());
        assertEquals(LocalDate.now(), sc.getStartDate());
        assertEquals(LocalDate.now().plusYears(1), sc.getExpectedEndDate());

        verify(repository, times(1)).insertStudentCourses(sc);

    }

    @Test
    void 受講生詳細更新_更新対象の受講生詳細を受け取りリポジトリが1回呼ばれること(){
        Student student = new Student();
        sut.updateStudent(student);
        verify(repository, times(1)).updateStudent(student);
    }

    @Test
    void 受講生詳細更新_更新対象の受講生コース情報を受け取りリポジトリが1回呼ばれること(){
        StudentCourses sc = new StudentCourses();
        sut.updateStudentCourses(sc);
        verify(repository, times(1)).updateStudentCourses(sc);
    }

    @Test
    void 受講生詳細更新_更新対象の受講生詳細とコース情報を受け取り更新されること(){
        Student student = new Student();
        StudentCourses sc = new StudentCourses();
        StudentDetail input = new StudentDetail(student, List.of(sc));

        sut.updateStudentDetail(input);

        verify(sut, times(1)).updateStudent(student);
        verify(sut, times(1)).updateStudentCourses(sc);
    }

}