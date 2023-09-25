package com.tap.distributedlockingpoc.service;

import com.tap.distributedlockingpoc.model.Student;
import com.tap.distributedlockingpoc.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentRepository studentRepository;

    //crud funtions
    public Student createStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new RuntimeException("Student already exists");
        }
        return studentRepository.save(student);
    }

    private Student updateStudent(Long id, Student student) {
        Student studentToUpdate = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        studentToUpdate.setName(student.getName());
        studentToUpdate.setEmail(student.getEmail());
        studentToUpdate.setPhone(student.getPhone());
        studentToUpdate.setWalletBalance(student.getWalletBalance());
        return studentRepository.save(studentToUpdate);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
