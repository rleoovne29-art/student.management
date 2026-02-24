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
    return repository.search();
  }

}

