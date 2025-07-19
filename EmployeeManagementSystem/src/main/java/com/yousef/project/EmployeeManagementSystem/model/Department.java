package com.yousef.project.EmployeeManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dept_id;
    private String name;
    private String creationDate;
    @OneToOne
    @JoinColumn(name ="id")
    private Employee hod;
}
