package com.tap.distributedlockingpoc.repository;

import com.tap.distributedlockingpoc.model.Student;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Student> findById(Long id);

    Optional<Student> findByEmail(String email);
}
