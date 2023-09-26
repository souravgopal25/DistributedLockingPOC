package com.tap.distributedlockingpoc.service;

import com.tap.distributedlockingpoc.model.Student;
import com.tap.distributedlockingpoc.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

import static java.lang.Thread.sleep;

@Slf4j
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


    @Retryable(value = {SQLException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Transactional
    public Student addMoney(Long userId, BigDecimal amountToAdd, Boolean sleep) throws InterruptedException {
        try {
            Student student = studentRepository.findById(userId).orElseThrow(() -> new RuntimeException("Student not found"));
            if (sleep) {
                log.info("Sleeping for 20 seconds");
                sleep(15000);
            }
            student.setWalletBalance(student.getWalletBalance().add(amountToAdd));
            return studentRepository.save(student);
        } catch (Exception e) {
            log.error("Error while adding money to student wallet", e);
            throw e;
        }
    }
}
