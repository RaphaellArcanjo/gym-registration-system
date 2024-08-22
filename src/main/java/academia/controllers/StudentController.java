package academia.controllers;

import academia.domain.student.Student;
import academia.domain.student.StudentRepository;
import academia.domain.student.StudentRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @GetMapping
    public ResponseEntity getAllStudents(){
        var allStudents = repository.findAll();
        return ResponseEntity.ok(allStudents);
    }

    @PostMapping
    public ResponseEntity registerStudent(@RequestBody @Validated StudentRequest data){
        Student newStudent = new Student(data);
        repository.save(newStudent);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateStudent(@RequestBody @Validated StudentRequest data){
        Optional<Student> optionalStudent= repository.findById(data.id());
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(data.name());
            student.setPhone_number(data.phone_number());
            student.setSex(data.sex());
            student.setDate_of_birth(data.date_of_birth());
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteStudent(@RequestBody @Validated StudentRequest data) {
        Optional<Student> optionalStudent = repository.findById(data.id());
        if (optionalStudent.isPresent()) {
            repository.deleteById(data.id());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam("name") String name) {
        List<Student> students = repository.findByNameContaining(name);
        return ResponseEntity.ok(students);
    }
}
