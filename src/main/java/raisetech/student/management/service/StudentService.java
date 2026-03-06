package raisetech.student.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.date.Course;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public String generateRandomId() {
    int length = 8; String chars =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      sb.append(chars.charAt(random.nextInt(chars.length()))); }
    return sb.toString();
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }

  public void registerStudent(Student student) {
    repository.insertStudent(student);
  }

  public void registerStudentsCourses(StudentsCourses sc) {
    sc.setId(generateRandomId());
    repository.insertStudentsCourses(sc);
  }

  public List<Course> getAllCourses() {
    List<Course> list = new ArrayList<>();
    Course c1 = new Course();
    c1.setName("Java基礎");
    Course c2 = new Course();
    c2.setName("Spring入門");
    list.add(c1);
    list.add(c2);
    return list;
  }

}

