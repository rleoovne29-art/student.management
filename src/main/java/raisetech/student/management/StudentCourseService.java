package raisetech.student.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseService {

  private static final Logger log = LoggerFactory.getLogger(StudentCourseService.class);

  private final StudentCourseRepository repository;

  public StudentCourseService(StudentCourseRepository repository) {
    this.repository = repository;
  }

  public List<StudentCourse> getAllStudentsCourses() {
    return repository.search();
  }

}
