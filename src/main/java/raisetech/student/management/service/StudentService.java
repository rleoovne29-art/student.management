package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を釣り扱うサービスです。
 * 受講生の検索や登録、更新処理を行います。
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * ランダムな8桁の英数字で受講生IDを生成します。
   * IDは重複防止のため毎回ランダムに生成され、
   * 学生登録時に主キーとして利用されます。
   *
   * @return 生成されたランダムID（例: "aB3x9KpQ"）
   */
  public String generateRandomId() {
    int length = 8; String chars =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      sb.append(chars.charAt(random.nextInt(chars.length()))); }
    return sb.toString();
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCourses();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生IDを指定して学生情報を1件取得します。
   * このメソッドはリポジトリ層の検索処理を呼び出すだけのシンプルなラッパーで、
   * サービス層から学生情報を取得する際の共通メソッドとして利用されます。
   *
   * @param id 取得対象の受講生ID
   * @return 該当する Student オブジェクト（存在しない場合は null）
   */
  public Student searchStudentById(String id) {
    return repository.searchStudentById(id);
  }

  /**
   * 受講生IDから StudentDetail を取得します。
   * 学生情報と受講コース情報をまとめて返すサービスメソッドです。
   *
   * @param id 受講生ID
   * @return StudentDetail（学生情報 + コース一覧）
   */
  public StudentDetail getStudentDetail(String id) {
    Student student = searchStudentById(id);
    if (student == null) {
      return null;
    }
    List<StudentsCourses> courses = getCoursesByStudentId(id);

    return buildStudentDetail(student, courses);
  }

  /**
   * 指定した受講生IDに紐づく受講コース一覧を取得します。
   *
   * @param id 受講生ID
   * @return 受講コース一覧
   */
  private List<StudentsCourses> getCoursesByStudentId(String id) {
    return repository.searchStudentsCourses()
            .stream()
            .filter(c -> c.getStudentsId().equals(id))
            .toList();
  }

  /**
   * Student と Courses をまとめて StudentDetail に変換します。
   *
   * @param student 学生情報
   * @param courses 受講コース一覧
   * @return StudentDetail（学生 + コース一覧）
   */
  private StudentDetail buildStudentDetail(Student student, List<StudentsCourses> courses) {
    return new StudentDetail(student, courses);
  }

  /**
   * 学生情報を1件登録します。
   * このメソッドは Student エンティティを受け取り、
   * リポジトリ層へ登録処理を委譲します。
   *
   * @param student 登録対象の学生情報
   */
  public void registerStudent(Student student) {
    repository.insertStudent(student);
  }

  /**
   * StudentDetail を受け取り、学生情報と受講コース情報をまとめて登録します。
   *
   * 以下の処理を一括で行います:
   * 1. 学生IDの採番
   * 2. 学生情報の登録
   * 3. コース情報の登録
   * 4. 登録後の StudentDetail を組み立てて返却
   *
   * @param studentDetail 登録対象の学生情報 + コース情報
   * @return 登録後の StudentDetail（採番済みIDを含む）
   */
  public StudentDetail registerStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    String id = generateRandomId();
    student.setId(id);
    registerStudent(student);
    StudentsCourses sc = studentDetail.getStudentsCourses().get(0);
    sc.setStudentsId(id);
    registerStudentsCourses(sc);
    return new StudentDetail(student, List.of(sc));
  }

  /**
   * 受講コース情報を1件登録します。
   * コースIDの採番、開始日・終了予定日の自動設定を行い、
   * リポジトリ層へ登録処理を委譲します。
   *
   * @param sc 登録対象の受講コース情報
   */
  public void registerStudentsCourses(StudentsCourses sc) {
    sc.setId(generateRandomId());
    sc.setStartDate(LocalDateTime.now().toLocalDate());
    sc.setExpectedEndDate(LocalDateTime.now().plusYears(1).toLocalDate());
    repository.insertStudentsCourses(sc);
  }

  /**
   * 学生情報を更新します。
   * このメソッドは Student エンティティの更新処理を
   * リポジトリ層へ委譲するシンプルなラッパーです。
   *
   * @param student 更新対象の学生情報
   */
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

  /**
   * 受講コース情報を更新します。
   * このメソッドは StudentsCourses エンティティの更新処理を
   * リポジトリ層へ委譲するシンプルなラッパーです。
   *
   * @param sc 更新対象の受講コース情報
   */
  public void updateStudentsCourses(StudentsCourses sc) {
    repository.updateStudentsCourses(sc);
  }

  /**
   * StudentDetail を受け取り、学生情報と受講コース情報をまとめて更新します。
   *
   * @param studentDetail 更新対象の学生情報とコース情報
   */
  public void updateStudentDetail(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    updateStudent(student);
    for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
      updateStudentsCourses(sc);
    }
  }

}

