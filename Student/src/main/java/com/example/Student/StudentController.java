package com.example.Student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();
    private Long nextId = 6L;

    // Initialize with 5 sample students
    public StudentController() {
        students.add(new Student(1L, "Alice", "Johnson", "alice.johnson@university.edu", "Computer Science", 3.8));
        students.add(new Student(2L, "Bob", "Smith", "bob.smith@university.edu", "Mathematics", 3.5));
        students.add(new Student(3L, "Carol", "Williams", "carol.williams@university.edu", "Computer Science", 3.9));
        students.add(new Student(4L, "David", "Brown", "david.brown@university.edu", "Physics", 3.2));
        students.add(new Student(5L, "Eve", "Davis", "eve.davis@university.edu", "Computer Science", 3.6));
    }

    // GET /api/students - Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    // GET /api/students/{studentId} - Get student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/students/major/{major} - Get all students by major (path variable)
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> result = students.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // GET /api/students/filter?gpa={minGpa} - Filter students with GPA >= minGpa
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByGpa(@RequestParam("gpa") Double minGpa) {
        List<Student> result = students.stream()
                .filter(s -> s.getGpa() >= minGpa)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // POST /api/students - Register a new student
    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        student.setStudentId(nextId++);
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    // PUT /api/students/{studentId} - Update student information
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                updatedStudent.setStudentId(studentId);
                students.set(i, updatedStudent);
                return ResponseEntity.ok(updatedStudent);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
