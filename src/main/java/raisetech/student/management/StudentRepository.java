package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student")
  List<Student> findAll();

  @Insert("INSERT INTO student (name, age, job) values(#{name},#{age},#{job})")
  int registerStudent(
      @Param("name") String name,
      @Param("age") int age,
      @Param("job") String job
  );

  @Update("UPDATE student SET age = #{age} , job = #{job} WHERE name = #{name}")
  int updateStudent(
      @Param("name") String name,
      @Param("age") int age,
      @Param("job") String job
  );

  @Delete("DELETE FROM student WHERE name = #{name}")
  int deleteStudent(String name);

}
