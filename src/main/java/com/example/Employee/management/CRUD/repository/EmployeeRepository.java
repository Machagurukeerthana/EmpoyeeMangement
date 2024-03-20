package com.example.Employee.management.CRUD.repository;

import com.example.Employee.management.CRUD.entity.EmployeeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    @Transactional
    @Modifying
    @Query("delete from EmployeeEntity ee where ee.employeeId = :employeeId")
    void deleteEmployee(String employeeId);
}
