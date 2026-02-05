package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private Student latestStudent = new Student("Kana", "26");
  private final Map<String, Student> student = new ConcurrentHashMap<>();

  public StudentService() {
    Student nasima = new Student("Nasima", "25");
    Student komine = new Student("Komine", "32");

    student.put("Nasima", nasima);
    student.put("Komine", komine);

    this.latestStudent = komine;
  }

  public String getStudent() {
    return latestStudent.getName() + " " + latestStudent.getAge() + " ";
  }

  public Map<String, Student> getStudents() {
    return new HashMap<>(student);
  }

  public Student getStudentByName(String name) {
    return student.get(name);
  }

  public void setStudent(String name, String age) {
    this.latestStudent = new Student(name, age);
    student.put(name, this.latestStudent);
  }

  public void addStudent(String name, String age) {
    Student newStudent = new Student(name, age);
    student.put(name, newStudent);
    this.latestStudent = newStudent;
  }

  public boolean updateStudentName(String name,String newName,String newAge) {
    Student studentData = student.get(name);
    if (studentData != null) {
      studentData.setName(newName);
      studentData.setAge(newAge);
      student.remove(name);
      student.put(newName, studentData);
      this.latestStudent = studentData;
      return true;
    }
    return false;
  }

  public boolean updateStudentAge(String name,String newAge) {
    Student studentInMap = student.get(name);
    if (studentInMap != null) {
      studentInMap.setAge(newAge);
      this.latestStudent = studentInMap;
      return true;
    }
    return false;
  }

  public boolean deleteStudent(String name){
    if (student.containsKey(name)) {
      student.remove(name);
      return true;
    }
    return false;
  }
}

