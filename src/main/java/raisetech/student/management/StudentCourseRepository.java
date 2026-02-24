package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
}
