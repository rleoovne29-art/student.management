package raisetech.student.management.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @DisplayName("受講生の全件検索が行えること")
    @Test
    void searchAllStudents_returnsAllRecords(){
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(5);
        assertThat(actual)
                .extracting(Student::getId)
                .contains("stu1", "stu2", "stu3", "stu4", "stu5");
        Student first = actual.stream()
                .filter(s -> s.getId().equals("stu1"))
                .findFirst()
                .orElseThrow();
        assertThat(first.getName()).isEqualTo("山田太郎");
    }

    @DisplayName("受講生コース情報の全件検索が行えること")
    @Test
    void searchAllStudentsCourses_returnsAllRecords(){
        List<StudentCourses> actual = sut.searchStudentCourses();
        assertThat(actual.size()).isEqualTo(5);
    }

    @DisplayName("受講生の検索が１件だけ返ること")
    @Test
    void searchStudentById_returnsOneRecord(){
        Student actual = sut.searchStudentById("stu1");
        assertThat(actual).isNotNull();
    }

    @DisplayName("受講生の登録が行えること")
    @Test
    void insertStudent_savesNewStudent(){
        Student student = new Student();
        student.setId("stu10");
        student.setName("無馬かな");
        student.setKana("なしまかな");
        student.setAge(25);
        student.setNickname("なしま");
        student.setEmail("nasima@example.com");
        student.setRegion("東京");
        student.setGender("male");
        student.setRemark("");
        student.setDeleted(false);

        sut.insertStudent(student);
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(6);
    }

    @DisplayName("受講生コース情報の登録が行えること")
    @Test
    void insertStudentCourses_savesNewCourse(){
        StudentCourses course = new StudentCourses();
        course.setId("crs6");
        course.setStudentsId("stu1");
        course.setCourseName("Java");
        course.setStartDate(LocalDate.now());
        course.setExpectedEndDate(LocalDate.now().plusYears(1));

        sut.insertStudentCourses(course);
        List<StudentCourses> actual = sut.searchStudentCourses();
        assertThat(actual.size()).isEqualTo(6);
    }

    @DisplayName("受講生情報の更新が行えること")
    @Test
    void updateStudent_updatesStudentInfo(){
        Student student = new Student();
        student.setId("stu1");
        student.setName("山田太郎");
        student.setKana("やまだたろう");
        student.setAge(30);
        student.setNickname("やまちゃん");
        student.setEmail("yama@example.com");
        student.setRegion("大阪");
        student.setGender("male");
        student.setRemark("特になし");
        student.setDeleted(false);

        sut.updateStudent(student);
        Student actual = sut.searchStudentById("stu1");
        assertThat(actual).isNotNull();
        assertThat(actual.getAge()).isEqualTo(30);
        assertThat(actual.getNickname()).isEqualTo("やまちゃん");
        assertThat(actual.getEmail()).isEqualTo("yama@example.com");
        assertThat(actual.getRegion()).isEqualTo("大阪");
    }

    @DisplayName("受講生コース情報の更新が行えること")
    @Test
    void updateStudentCourses_updatesCourseInfo(){
        StudentCourses course = new StudentCourses();
        course.setId("crs1");
        course.setStudentsId("stu1");
        course.setCourseName("AI");
        course.setStartDate(LocalDate.now());
        course.setExpectedEndDate(LocalDate.now().plusYears(1));

        sut.updateStudentCourses(course);
        List<StudentCourses> all = sut.searchStudentCourses();
        assertThat(all.size()).isEqualTo(5);
        StudentCourses actual = all.stream()
                .filter(c -> c.getId().equals("crs1"))
                .findFirst()
                .orElseThrow();
        assertThat(actual.getStudentsId()).isEqualTo("stu1");
        assertThat(actual.getCourseName()).isEqualTo("AI");
    }

}
