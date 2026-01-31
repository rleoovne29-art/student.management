package raisetech.Student.Management;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private String name = "Kana";
	private String age = "26";
	private final Map<String,String> student = new HashMap<>();

	public Application() {
		student.put("Nasima", "25");
		student.put("Komine", "32");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudent(){
		return name + "　" + age + "歳";
	}

	@GetMapping("/students")
	public Map<String,String> getStudents(){
		return student;
	}

	@GetMapping("/student/{name}")
	public String getStudent(@PathVariable String name){
		return student.get(name);
	}

	@PostMapping("/studentInfo")
	public void setStudent(String name,String age){
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public void updateStudentName(String name){
		this.name = name;
	}

	@PostMapping("/studentAge")
	public void updateStudentAge(String age) {
		this.age = age;
	}

	@PostMapping("/student/add")
	public String addStudent(@RequestParam String name,@RequestParam String age){
		student.put(name,age);
		return "added";
	}
}
