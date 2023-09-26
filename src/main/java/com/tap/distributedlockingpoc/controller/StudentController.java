package com.tap.distributedlockingpoc.controller;

import com.tap.distributedlockingpoc.model.Student;
import com.tap.distributedlockingpoc.service.StudentService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    @GetMapping("/hello")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/student")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            return ResponseEntity.ok(studentService.createStudent(student));

        } catch (Exception e) {

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/student/{id}/add-money/{amount}")
    public ResponseEntity<?> addMoney(@PathVariable Long id, @PathVariable BigDecimal amount) {
        try {
            return ResponseEntity.ok(studentService.addMoney(id, amount, false));

        } catch (Exception e) {

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/student/{id}/add-money-sleep/{amount}")
    public ResponseEntity<?> addMoneySleep(@PathVariable Long id, @PathVariable BigDecimal amount) {
        try {
            return ResponseEntity.ok(studentService.addMoney(id, amount, true));

        } catch (Exception e) {

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
