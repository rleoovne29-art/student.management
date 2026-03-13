package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE is_deleted = 0 ")
  @Results(id = "StudentMap", value = {
      @Result(property = "id", column = "id"),
      @Result(property = "name", column = "name"),
      @Result(property = "kana", column = "kana"),
      @Result(property = "age", column = "age"),
      @Result(property = "nickname", column = "nickname"),
      @Result(property = "email", column = "email"),
      @Result(property = "region", column = "region"),
      @Result(property = "gender", column = "gender"),
      @Result(property = "remark", column = "remark"),
      @Result(property = "deleted", column = "is_deleted")
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

  @Select("SELECT * FROM students WHERE id = #{id}")

  Student findStudentById(String id);

  @Insert(
      "INSERT INTO students (id, name, kana, age, nickname, email, region, gender, remark, is_deleted) " +
      "VALUES (#{id}, #{name}, #{kana}, #{age}, #{nickname}, #{email}, #{region}, #{gender}, #{remark}, false)")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses ("
      + "id, students_id, course_name, start_date, expected_end_date) "
      + "VALUES (#{id}, #{studentsId}, #{courseName}, #{startDate}, #{expectedEndDate}) ")
  void insertStudentsCourses(StudentsCourses sc);

  @Update("UPDATE students SET "
      + "name = #{name}, kana = #{kana}, age = #{age}, nickname = #{nickname}, "
      + "email = #{email},region = #{region}, gender = #{gender}, "
      + "remark = #{remark}, is_deleted = #{deleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET " +
          "students_id = #{studentsId}, course_name = #{courseName}, " +
          "start_date = #{startDate}, expected_end_date = #{expectedEndDate}WHERE id = #{id} ")
  void updateStudentsCourses(StudentsCourses sc);


}
