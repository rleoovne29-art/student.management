package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;

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

  @Select("SELECT * FROM students WHERE age BETWEEN #{minAge} AND #{maxAge}")
  @ResultMap("StudentMap")
  List<Student> searchRange(int minAge , int maxAge);

}
