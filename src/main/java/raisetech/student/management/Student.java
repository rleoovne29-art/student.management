package raisetech.student.management;

public class Student {

  private Integer id;
  private String name;
  private int age;
  private String job;

  public Student(){
  }

  public Student(String name, int age, String job) {
    this.name = name;
    this.age = age;
    this.job = job;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getAge(){
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }
}
