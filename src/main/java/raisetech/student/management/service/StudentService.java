package raisetech.student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.date.Student;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> getAllStudents(int minAge , int maxAge) {
    repository.search();
    //絞り込みをする。年齢が30代の人のみを抽出する。
    //抽出したリストをコントローラーに返す。
    return repository.searchRange(minAge,maxAge);
  }

}

