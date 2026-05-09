package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domain.StudentDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 受講生詳細を受講生と、受講生コース情報、もしくはその逆の変換を行うConverterです。
 */
@Component
public class StudentConverter {

  private static final Logger log = LoggerFactory.getLogger(StudentConverter.class);

  /**
   * 受講生IDごとに StudentCourses をグルーピングするためのメソッドです。
   * convertStudentDetails の処理を高速化する目的で、事前に Map へまとめます。
   *
   * @param studentCourses 受講生コース情報のリスト
   * @return key が studentsId、value がその受講生のコース一覧となる Map
   */
  private Map<String, List<StudentCourses>> toMap(List<StudentCourses> studentCourses) {
    if (studentCourses == null) {
      return Collections.emptyMap();
    }

    Map<String, List<StudentCourses>> map = new HashMap<>();
    for (StudentCourses sc : studentCourses) {
      if (sc == null) {
        log.warn("StudentCourses is null");
        continue;
      }
      if (sc.getStudentsId() == null) {
        log.warn("studentsId is null: {}", sc);
        continue;
      }
      String key = sc.getStudentsId();
      map.computeIfAbsent(key, k -> new ArrayList<>()).add(sc);
    }
    return map;
  }

  /**
   * 受講生一覧と受講生コース情報を組み合わせて、StudentDetail のリストを生成します。
   * 画面表示や API 応答で利用するために、受講生とそのコース情報を結合した形式を作成します。
   * 
   * @param students 受講生一覧
   * @param studentCourses 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourses> studentCourses) {
    List<Student> safeStudents =
            (students == null) ? Collections.emptyList() : students;
    List<StudentCourses> safeCourses =
            (studentCourses == null) ? Collections.emptyList() : studentCourses;

    List<StudentDetail> studentDetails = new ArrayList<>();
    safeStudents.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourses> convertStudentCourses = safeCourses.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentsId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
