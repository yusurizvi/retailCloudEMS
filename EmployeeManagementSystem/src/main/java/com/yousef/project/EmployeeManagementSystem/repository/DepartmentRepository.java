package com.yousef.project.EmployeeManagementSystem.repository;

import com.yousef.project.EmployeeManagementSystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
