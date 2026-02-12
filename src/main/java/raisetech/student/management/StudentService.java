package raisetech.student.management;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private final StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> getAllStudents() {
    return repository.findAll();
  }

  public boolean registerStudent(String name, int age, String job) {
    return repository.registerStudent(name, age, job) > 0;
  }

  public boolean updateStudent(String name, int age, String job) {
    return repository.updateStudent(name, age, job) > 0;
  }

  public boolean deleteStudent(String name) {
    return repository.deleteStudent(name) > 0;
  }
}

