package com.yousef.project.EmployeeManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private  String name;
    private  int salary;
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department dept;
    private String address;
    private String dateOfBirth;
    private String joiningDate;
    private String role;
    @ManyToOne
    @JoinColumn(name = "reportingManager")
    private Employee reportingManager;
    private String bonusPercentage;

}
