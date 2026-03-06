package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  @Results(id = "StudentMap", value = {
      @Result(property = "id", column = "id"),
      @Result(property = "name", column = "name"),
      @Result(property = "kana", column = "kana"),
      @Result(property = "age", column = "age"),
      @Result(property = "nickname", column = "nickname"),
      @Result(property = "email", column = "email"),
      @Result(property = "region", column = "region"),
      @Result(property = "gender", column = "gender")
  })

  List<Student> search();

  @Select("SELECT * FROM students_courses")

  @Results(id = "StudentCourseMap", value = {
      @Result(property = "id", column = "id"),
      @Result(property = "studentsId", column = "students_id"),
      @Result(property = "courseName", column = "course_name"),
      @Result(property = "startDate", column = "start_date"),
      @Result(property = "expectedEndDate", column = "expected_end_date")
  })

  List<StudentsCourses> searchStudentsCourses();

  @Insert(
      "INSERT INTO students (id, name, kana, age, nickname, email, region, gender, remark, isDeleted) " +
      "VALUES (#{id}, #{name}, #{kana}, #{age}, #{nickname}, #{email}, #{region}, #{gender}, #{remark}, #{isDeleted})")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses (id, students_id, course_name) "
      + "VALUES (#{id}, #{studentsId}, #{courseName}) ")
  void insertStudentsCourses(StudentsCourses sc);

}
