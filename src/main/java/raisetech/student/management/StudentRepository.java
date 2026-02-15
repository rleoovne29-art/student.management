package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student")
  List<Student> findAll();

  @Insert("INSERT INTO student (name, age, job) values(#{name},#{age},#{job})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int registerStudent(Student student);

  @Update("UPDATE student SET name = #{name}, age = #{age} , job = #{job} WHERE id = #{id}")
  int updateStudent(
      @Param("id") int id,
      @Param("name") String name,
      @Param("age") int age,
      @Param("job") String job
  );

  @Delete("DELETE FROM student WHERE id = #{id}")
  int deleteStudent(String id);

}
