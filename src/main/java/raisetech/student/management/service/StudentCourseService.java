package raisetech.student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.repository.StudentCourseRepository;

@Service
public class StudentCourseService {

  private final StudentCourseRepository repository;

  @Autowired
  public StudentCourseService(StudentCourseRepository repository) {
    this.repository = repository;
  }

  public List<StudentCourse> getAllStudentsCourses(String courseName) {
    repository.search();
    //絞り込み検索で「Javaコース」のコース情報のみを抽出する。
    //抽出したリストをコントローラーに返す。
    return repository.searchByCourseName(courseName);
  }

}
