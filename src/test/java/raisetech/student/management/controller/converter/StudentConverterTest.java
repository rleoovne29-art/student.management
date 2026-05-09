package raisetech.student.management.controller.converter;

import org.junit.jupiter.api.Test;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domain.StudentDetail;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudentConverterTest {

    @Test
    void 受講生1人に紐づくコース情報だけがセットされること() {
        Student student = new Student();
        student.setId("1");
        student.setName("テスト太郎");

        StudentCourses course1 = new StudentCourses();
        course1.setStudentsId("1");
        course1.setCourseName("Java");

        StudentCourses course2 = new StudentCourses();
        course2.setStudentsId("2");
        course2.setCourseName("Python");

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

    @Test
    void 複数の受講生に対して正しいコース情報が紐づくこと() {
        Student student1 = new Student();
        student1.setId("1");
        student1.setName("テスト太郎");

        Student student2 = new Student();
        student2.setId("2");
        student2.setName("テスト花子");

        StudentCourses courseA1 = new StudentCourses();
        courseA1.setStudentsId("1");
        courseA1.setCourseName("Java");

        StudentCourses courseA2 = new StudentCourses();
        courseA2.setStudentsId("1");
        courseA2.setCourseName("Spring");

        StudentCourses courseB1 = new StudentCourses();
        courseB1.setStudentsId("2");
        courseB1.setCourseName("Python");

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

    @Test
    void コース情報が0件のときは空リストがセットされること() {
        Student student = new Student();
        student.setId("1");
        student.setName("テスト太郎");

        List<Student> students = List.of(student);
        List<StudentCourses> courses = List.of();

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(1);

        StudentDetail detail = result.get(0);
        assertThat(detail.getStudent().getId()).isEqualTo("1");
        assertThat(detail.getStudentCourses()).isEmpty();
    }

    @Test
    void 受講生IDが一致しないときは空リストがセットされること() {
        Student student = new Student();
        student.setId("1");
        student.setName("テスト太郎");

        StudentCourses course = new StudentCourses();
        course.setStudentsId("2");
        course.setCourseName("Java");

        List<Student> students = List.of(student);
        List<StudentCourses> courses = List.of(course);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(1);

        StudentDetail detail = result.get(0);
        assertThat(detail.getStudent().getId()).isEqualTo("1");
        assertThat(detail.getStudentCourses()).isEmpty();
    }

    @Test
    void 複数の受講生で全てのコース情報が一致しないとき全員空リストになる() {
        Student student1 = new Student();
        student1.setId("1");
        student1.setName("テスト太郎");

        Student student2 = new Student();
        student2.setId("2");
        student2.setName("テスト花子");

        StudentCourses course1 = new StudentCourses();
        course1.setStudentsId("3");
        course1.setCourseName("Java");

        StudentCourses course2 = new StudentCourses();
        course2.setStudentsId("3");
        course2.setCourseName("Python");

        List<Student> students = List.of(student1, student2);
        List<StudentCourses> courses = List.of(course1, course2);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(2);

        StudentDetail detail1 = result.stream()
                .filter(d -> d.getStudent().getId().equals("1"))
                .findFirst()
                .orElseThrow();
        assertThat(detail1.getStudentCourses()).isEmpty();

        StudentDetail detail2 = result.stream()
                .filter(d -> d.getStudent().getId().equals("2"))
                .findFirst()
                .orElseThrow();
        assertThat(detail2.getStudentCourses()).isEmpty();
    }

    @Test
    void 複数受講生で一部だけコース情報が一致する場合正しく振り分けられること() {
        Student student1 = new Student();
        student1.setId("1");
        student1.setName("テスト太郎");

        Student student2 = new Student();
        student2.setId("2");
        student2.setName("テスト花子");

        StudentCourses course1 = new StudentCourses();
        course1.setStudentsId("1");
        course1.setCourseName("Java");

        StudentCourses course2 = new StudentCourses();
        course2.setStudentsId("3");
        course2.setCourseName("Python");

        List<Student> students = List.of(student1, student2);
        List<StudentCourses> courses = List.of(course1, course2);

        StudentConverter converter = new StudentConverter();
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertThat(result).hasSize(2);

        StudentDetail detail1 = result.stream()
                .filter(d -> d.getStudent().getId().equals("1"))
                .findFirst()
                .orElseThrow();

        assertThat(detail1.getStudentCourses()).hasSize(1);
        assertThat(detail1.getStudentCourses().get(0).getCourseName()).isEqualTo("Java");

        StudentDetail detail2 = result.stream()
                .filter(d -> d.getStudent().getId().equals("2"))
                .findFirst()
                .orElseThrow();

        assertThat(detail2.getStudentCourses()).isEmpty();
    }

    @Test
    void 受講生がnullのときNPEが発生すること(){
        List<Student> students = null;
        List<StudentCourses> courses = List.of();

        StudentConverter converter = new StudentConverter();

        assertThatThrownBy(() -> converter.convertStudentDetails(students, courses))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void コース情報がnullのときはNPEが発生すること() {

        List<Student> students = List.of(new Student());
        List<StudentCourses> courses = null;

        StudentConverter converter = new StudentConverter();

        assertThatThrownBy(() -> converter.convertStudentDetails(students, courses))
                .isInstanceOf(NullPointerException.class);
    }

}