package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.date.StudentCourse;

@Mapper
public interface StudentCourseRepository {

  @Select("SELECT * FROM students_courses")

  @Results(id = "StudentCourseMap", value = {
      @Result(property = "id", column = "id"),
      @Result(property = "studentsId", column = "students_id"),
      @Result(property = "courseName", column = "course_name"),
      @Result(property = "startDate", column = "start_date"),
      @Result(property = "expectedEndDate", column = "expected_end_date")
  })

  List<StudentCourse> search();

  @Select("SELECT * FROM Students_courses WHERE course_name = #{courseName}")

  @ResultMap("StudentCourseMap")
  List<StudentCourse> searchByCourseName(String courseName);
}
