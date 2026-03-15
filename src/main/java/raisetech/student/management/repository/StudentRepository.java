package raisetech.student.management.repository;

import java.util.List;

import org.apache.ibatis.annotations.*;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentsCourses;

/**
 * 受講生テーブルと受講生コース情報テーブルに紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生情報(全件)
   */
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

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生コース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  @Results(id = "StudentCourseMap", value = {
      @Result(property = "id", column = "id"),
      @Result(property = "studentsId", column = "students_id"),
      @Result(property = "courseName", column = "course_name"),
      @Result(property = "startDate", column = "start_date"),
      @Result(property = "expectedEndDate", column = "expected_end_date")
  })

  List<StudentsCourses> searchStudentsCourses();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  @ResultMap("StudentMap")
  Student searchStudentById(String id);

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
          "students_id = #{studentsId}, course_name = #{courseName} WHERE id = #{id} ")
  void updateStudentsCourses(StudentsCourses sc);

}
