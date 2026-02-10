package com.example.Student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();
    private Long nextId = 1L;

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        student.setStudentId(nextId++);
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        if (students.isEmpty()) {
            return ResponseEntity.ok("No students registered in the system.");
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable Long studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return ResponseEntity.ok(student);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found with id: " + studentId);
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> results = new ArrayList<>();
        for (Student student : students) {
            if (student.getMajor().equalsIgnoreCase(major)) {
                results.add(student);
            }
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByGpa(@RequestParam("gpa") Double minGpa) {
        List<Student> results = new ArrayList<>();
        for (Student student : students) {
            if (student.getGpa() >= minGpa) {
                results.add(student);
            }
        }
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                updatedStudent.setStudentId(studentId);
                students.set(i, updatedStudent);
                return ResponseEntity.ok(updatedStudent);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found with id: " + studentId);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                students.remove(i);
                return ResponseEntity.ok("Student deleted successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found with id: " + studentId);
    }
}
