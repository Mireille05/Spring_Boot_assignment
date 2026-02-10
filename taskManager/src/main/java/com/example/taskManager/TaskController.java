package com.example.taskManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @PostConstruct
    public void init() {
        tasks.add(new Task(nextId++, "Complete project report", "Write and submit the final project report", false, "HIGH", "2026-02-15"));
        tasks.add(new Task(nextId++, "Buy groceries", "Milk, eggs, bread, and vegetables", false, "MEDIUM", "2026-02-11"));
        tasks.add(new Task(nextId++, "Schedule dentist appointment", "Annual dental checkup", false, "LOW", "2026-02-20"));
        tasks.add(new Task(nextId++, "Fix login bug", "Resolve the authentication issue on the login page", false, "HIGH", "2026-02-12"));
        tasks.add(new Task(nextId++, "Read Java book", "Finish reading Effective Java by Joshua Bloch", true, "MEDIUM", "2026-02-10"));
        tasks.add(new Task(nextId++, "Clean workspace", "Organize desk and clean monitor", true, "LOW", "2026-02-09"));
        tasks.add(new Task(nextId++, "Prepare presentation", "Create slides for the team meeting", false, "HIGH", "2026-02-14"));
        tasks.add(new Task(nextId++, "Update resume", "Add recent projects and skills", false, "MEDIUM", "2026-02-18"));
        tasks.add(new Task(nextId++, "Pay electricity bill", "Online payment before due date", true, "HIGH", "2026-02-08"));
        tasks.add(new Task(nextId++, "Plan weekend trip", "Research destinations and book accommodation", false, "LOW", "2026-02-22"));
    }

    // GET /api/tasks - Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    // GET /api/tasks/{taskId} - Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/tasks/status?completed={true/false} - Get tasks by completion status
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam boolean completed) {
        List<Task> filtered = tasks.stream()
                .filter(t -> t.isCompleted() == completed)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    // GET /api/tasks/priority/{priority} - Get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        List<Task> filtered = tasks.stream()
                .filter(t -> t.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    // POST /api/tasks - Create new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setTaskId(nextId++);
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    // PUT /api/tasks/{taskId} - Update task
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().equals(taskId)) {
                updatedTask.setTaskId(taskId);
                tasks.set(i, updatedTask);
                return ResponseEntity.ok(updatedTask);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH /api/tasks/{taskId}/complete - Mark task as completed
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .map(task -> {
                    task.setCompleted(true);
                    return ResponseEntity.ok(task);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tasks/{taskId} - Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(t -> t.getTaskId().equals(taskId));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
