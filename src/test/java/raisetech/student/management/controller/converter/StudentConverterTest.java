package raisetech.student.management.controller.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domain.StudentDetail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class StudentConverterTest {

    @DisplayName("受講生1人に紐づくコース情報だけがセットされること")
    @Test
    void convertStudentDetails_shouldAssignCoursesOnlyToMatchingStudents_whenSomeMatch(){
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

        StudentCourses course1 = new StudentCourses();
        course1.setId("1");
        course1.setStudentsId("1");
        course1.setCourseName("Java");
        course1.setStartDate(LocalDate.from(LocalDateTime.now()));
        course1.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        StudentCourses course2 = new StudentCourses();
        course2.setId("2");
        course2.setStudentsId("2");
        course2.setCourseName("Python");
        course2.setStartDate(LocalDate.from(LocalDateTime.now()));
        course2.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        List<Student> students = List.of(student);
        List<StudentCourses> courses = List.of(course1, course2);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(1);

        StudentDetail detail = result.get(0);
        assertThat(detail.getStudent().getId()).isEqualTo("1");
        assertThat(detail.getStudentCourses()).hasSize(1);

        assertThat(detail.getStudentCourses()
                .get(0).getCourseName())
                .isEqualTo("Java");
    }

    @DisplayName("複数の受講生に対して正しいコース情報が紐づくこと")
    @Test
    void shouldAssignCoursesOnlyToMatchingStudents() {
        Student student1 = new Student();
        student1.setId("1");
        student1.setName("無馬かな");
        student1.setKana("なしまかな");
        student1.setAge(25);
        student1.setNickname("なしま");
        student1.setEmail("nasima@example.com");
        student1.setRegion("東京");
        student1.setGender("male");
        student1.setRemark("");
        student1.setDeleted(false);

        Student student2 = new Student();
        student2.setId("2");
        student2.setName("鬼野ねね");
        student2.setKana("おにのねね");
        student2.setAge(28);
        student2.setNickname("ねね");
        student2.setEmail("nene@example.com");
        student2.setRegion("東京");
        student2.setGender("male");
        student2.setRemark("");
        student2.setDeleted(false);

        StudentCourses courseA1 = new StudentCourses();
        courseA1.setStudentsId("1");
        courseA1.setCourseName("Java");
        courseA1.setStartDate(LocalDate.from(LocalDateTime.now()));
        courseA1.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        StudentCourses courseA2 = new StudentCourses();
        courseA2.setStudentsId("1");
        courseA2.setCourseName("Spring");
        courseA2.setStartDate(LocalDate.from(LocalDateTime.now()));
        courseA2.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        StudentCourses courseB1 = new StudentCourses();
        courseB1.setStudentsId("2");
        courseB1.setCourseName("Python");
        courseB1.setStartDate(LocalDate.from(LocalDateTime.now()));
        courseB1.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        List<Student> students = List.of(student1, student2);
        List<StudentCourses> courses = List.of(courseA1, courseA2, courseB1);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(2);

        StudentDetail detail1 = result.stream()
                .filter(d -> d.getStudent().getId().equals("1"))
                .findFirst()
                .orElseThrow();

        assertThat(detail1.getStudentCourses()).hasSize(2);
        assertThat(detail1.getStudentCourses())
                .extracting(StudentCourses::getCourseName)
                .containsExactlyInAnyOrder("Java", "Spring");

        StudentDetail detail2 = result.stream()
                .filter(d -> d.getStudent().getId().equals("2"))
                .findFirst()
                .orElseThrow();

        assertThat(detail2.getStudentCourses()).hasSize(1);
        assertThat(detail2.getStudentCourses()
                .get(0).getCourseName())
                .isEqualTo("Python");
    }

    @DisplayName("コース情報が0件のときは空リストがセットされること")
    @Test
    void convertStudentDetails_shouldAssignEmptyCourseList_whenCoursesIsEmpty() {
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

        List<Student> students = List.of(student);
        List<StudentCourses> courses = List.of();

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(1);

        StudentDetail detail = result.get(0);
        assertThat(detail.getStudent().getId()).isEqualTo("1");
        assertThat(detail.getStudentCourses()).isEmpty();
    }

    @DisplayName("受講生IDが一致しないときは空リストがセットされること")
    @Test
    void convertStudentDetails_shouldAssignEmptyCourseList_whenStudentIdDoesNotMatch() {
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

        StudentCourses course = new StudentCourses();
        course.setId("10");
        course.setStudentsId("999");
        course.setCourseName("Java");
        course.setStartDate(LocalDate.now());
        course.setExpectedEndDate(LocalDate.now().plusYears(1));

        List<Student> students = List.of(student);
        List<StudentCourses> courses = List.of(course);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(1);

        StudentDetail detail = result.get(0);
        assertThat(detail.getStudent().getId()).isEqualTo("1");
        assertThat(detail.getStudentCourses()).isEmpty();
    }

    @DisplayName("複数の受講生で全てのコース情報が一致しないとき全員空リストになること")
    @Test
    void convertStudentDetails_shouldReturnEmptyCoursesForAll_whenNoMatches() {
        Student student1 = new Student();
        student1.setId("1");
        student1.setName("無馬かな");
        student1.setKana("なしまかな");
        student1.setAge(25);
        student1.setNickname("なしま");
        student1.setEmail("nasima@example.com");
        student1.setRegion("東京");
        student1.setGender("male");
        student1.setRemark("");
        student1.setDeleted(false);

        Student student2 = new Student();
        student2.setId("2");
        student2.setName("鬼野ねね");
        student2.setKana("おにのねね");
        student2.setAge(28);
        student2.setNickname("ねね");
        student2.setEmail("nene@example.com");
        student2.setRegion("東京");
        student2.setGender("male");
        student2.setRemark("");
        student2.setDeleted(false);

        StudentCourses course1 = new StudentCourses();
        course1.setId("10");
        course1.setStudentsId("999");
        course1.setCourseName("Java");
        course1.setStartDate(LocalDate.from(LocalDateTime.now()));
        course1.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        List<Student> students = List.of(student1, student2);
        List<StudentCourses> courses = List.of(course1);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(2);

        assertThat(result.get(0).getStudentCourses()).isEmpty();
        assertThat(result.get(1).getStudentCourses()).isEmpty();
    }

    @DisplayName("複数受講生で一部だけコース情報が一致する場合正しく振り分けられること")
    @Test
    void convertStudentDetails_shouldAssignCoursesToMatchingStudents_whenSomeMatch() {
        Student student1 = new Student();
        student1.setId("1");
        student1.setName("無馬かな");
        student1.setKana("なしまかな");
        student1.setAge(25);
        student1.setNickname("なしま");
        student1.setEmail("nasima@example.com");
        student1.setRegion("東京");
        student1.setGender("male");
        student1.setRemark("");
        student1.setDeleted(false);

        Student student2 = new Student();
        student2.setId("2");
        student2.setName("鬼野ねね");
        student2.setKana("おにのねね");
        student2.setAge(28);
        student2.setNickname("ねね");
        student2.setEmail("nene@example.com");
        student2.setRegion("東京");
        student2.setGender("male");
        student2.setRemark("");
        student2.setDeleted(false);

        StudentCourses course1 = new StudentCourses();
        course1.setId("1");
        course1.setStudentsId("1");
        course1.setCourseName("Java");
        course1.setStartDate(LocalDate.from(LocalDateTime.now()));
        course1.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        StudentCourses course2 = new StudentCourses();
        course2.setId("3");
        course2.setStudentsId("999");
        course2.setCourseName("AWS");
        course2.setStartDate(LocalDate.from(LocalDateTime.now()));
        course2.setExpectedEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));

        List<Student> students = List.of(student1, student2);
        List<StudentCourses> courses = List.of(course1, course2);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(2);

        assertThat(result.get(0).getStudent().getId()).isEqualTo("1");
        assertThat(result.get(0).getStudentCourses()).hasSize(1);
        assertThat(result.get(0).getStudentCourses().get(0).getCourseName())
                .isEqualTo("Java");

        assertThat(result.get(1).getStudent().getId()).isEqualTo("2");
        assertThat(result.get(1).getStudentCourses()).isEmpty();
    }

    @DisplayName("受講生がnullのときは空リストが返ること")
    @Test
    void convertStudentDetails_shouldReturnEmptyList_whenStudentsIsNull() {
        List<Student> students = null;
        List<StudentCourses> courses = List.of();

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).isEmpty();
    }


    @DisplayName("受講生のコースがnullのときは全員のコースリストが空になること")
    @Test
    void convertStudentDetails_shouldReturnStudentsWithEmptyCourses_whenCoursesIsNull() {
        Student s1 = new Student();
        s1.setId("1");
        s1.setName("Aさん");

        Student s2 = new Student();
        s2.setId("2");
        s2.setName("Bさん");

        List<Student> students = List.of(s1,s2);
        List<StudentCourses> courses = null;

        StudentConverter converter = new StudentConverter();

        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStudentCourses()).isEmpty();
        assertThat(result.get(1).getStudentCourses()).isEmpty();
    }

    @DisplayName("受講生が0件のときは空リストが返ること")
    @Test
    void convertStudentDetails_shouldReturnEmptyList_whenStudentsIsEmpty() {
        List<Student> students = List.of();
        List<StudentCourses> courses = List.of();

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).isEmpty();
    }

}
