package com.yousef.project.EmployeeManagementSystem.repository;

import com.yousef.project.EmployeeManagementSystem.dto.EmployeeResponse;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query("SELECT  e.id,e.name from Employee e" )
    public EmployeeResponse getLookUpData();

    Optional<List<Employee>> findAllByDept(int dept);

    void deleteByDept(int dept);

//    List<Employee> id(int id);
}
