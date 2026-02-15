package raisetech.student.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private static final Logger log = LoggerFactory.getLogger(StudentService.class);

  private final StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> getAllStudents() {
    return repository.findAll();
  }

  private Integer lastInsertedId;

  public Integer getLastInsertedId() {
    return lastInsertedId;
  }

  public boolean registerStudent(String name, int age, String job) {
    Student student = new Student(name, age, job);
    int result = repository.registerStudent(student);
    if (result > 0){
      lastInsertedId = student.getId();
      log.info("登録された id: {}", student.getId());
    }
      return result > 0;
  }

  public boolean updateStudent(Integer id, String name, int age, String job) {
    int result = repository.updateStudent(id, name, age, job);
    return result > 0;
  }

  public boolean deleteStudent(Integer id) {
    return repository.deleteStudent(id) > 0;
  }
}

