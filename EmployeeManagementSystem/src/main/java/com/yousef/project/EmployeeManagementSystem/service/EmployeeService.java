package com.yousef.project.EmployeeManagementSystem.service;

import com.yousef.project.EmployeeManagementSystem.dto.EmployeeRequest;
import com.yousef.project.EmployeeManagementSystem.model.Department;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import com.yousef.project.EmployeeManagementSystem.repository.DepartmentRepository;
import com.yousef.project.EmployeeManagementSystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository empRepository;
    private final DepartmentRepository deptRepository;

    public List<Employee> getEmployeeDetails() {

        return empRepository.findAll();
    }

    public String addEmployee(EmployeeRequest request) {

        Optional<Department> dept = deptRepository.findById(Integer.valueOf(request.getDept()));
        Optional<Employee> reportingMan = empRepository.findById(Integer.valueOf(request.getReportingManager()));
        if (dept.isPresent() && reportingMan.isPresent()) {
            Employee emp = Employee.builder().name(request.getName())
                    .dept(dept.get()).name(reportingMan.get().getName())
                    .role(request.getRole()).address(request.getAddress()).dateOfBirth(request.getDateOfBirth())
                    .bonusPercentage(request.getBonusPercentage()).salary(Integer.parseInt(request.getSalary()))
                    .joiningDate(request.getJoiningDate()).reportingManager(reportingMan.get())
                    .build();
            empRepository.save(emp);
            return "Employee added successfully";
        }
        else if (dept.isEmpty())
            return "Department does not exist";
        else
            return "Invalid reporting manager";
    }
}
