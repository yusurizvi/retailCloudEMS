package com.yousef.project.EmployeeManagementSystem.service;

import com.yousef.project.EmployeeManagementSystem.dto.*;
import com.yousef.project.EmployeeManagementSystem.model.Department;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import com.yousef.project.EmployeeManagementSystem.repository.DepartmentRepository;
import com.yousef.project.EmployeeManagementSystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository empRepository;
    private final DepartmentRepository deptRepository;

    public boolean addDepartment(DepartmentRequest request) {
        Optional<Employee> emp = Optional.empty();
        if(request.getHod()!=null){
            emp = empRepository.findById(Integer.valueOf(request.getHod()));
            if(emp.isEmpty())
                return false;
        }
        Department dept = Department.builder().name(request.getName()).creationDate(request.getCreationDate())
                .hod(emp.orElse(null)).build();
        deptRepository.save(dept);
        return true;
    }

    public List<EmployeeResponse> getEmployeeDetails(boolean lookup) {

//        if(lookup)
//        {
//            return empRepository.getLookUpData();
//        }
        return empRepository.findAll().stream().map(employee -> {
            return EmployeeResponse.builder().name(employee.getName()).role(employee.getRole())
                    .salary(String.valueOf(employee.getSalary())).address(employee.getAddress()).
                    joiningDate(String.valueOf(employee.getJoiningDate())).
                    reportingManager(employee.getReportingManager()!=null?employee.getReportingManager().getName():null).dateOfBirth(String.valueOf(employee.getDateOfBirth()))
                    .bonusPercentage(String.valueOf(employee.getBonusPercentage()))
                    .dept(employee.getDept()!=null?employee.getDept().getName():null)
            .build();
        }).toList();
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

    public List<DepartmentResponse> getAllDepartments() {
    return deptRepository.findAll().stream().map(department -> {
        return DepartmentResponse.builder().name(department.getName()).creationDate(department.getCreationDate())
                .hod(department.getHod()!=null?String.valueOf(department.getHod().getId()):null).build();
    }).toList();
    }

    public String updateEmployee(EmployeeRequest request, String id) {

        Optional<Employee> reportingManager = empRepository.findById(Integer.valueOf(request.getReportingManager()));
        if(reportingManager.isEmpty())
            return "ReportingManager does not exist";
        Optional<Department> dept = deptRepository.findById(Integer.valueOf(request.getDept()));
        if(dept.isEmpty())
            return "Department does not exist";
        return empRepository.findById(Integer.valueOf(id))
                .map(emp -> {
                    emp.setName(request.getName());
                    emp.setAddress(request.getAddress());
                    emp.setDateOfBirth(request.getDateOfBirth());
                    emp.setBonusPercentage(request.getBonusPercentage());
                    emp.setSalary(Integer.parseInt(request.getSalary()));
                    emp.setJoiningDate(request.getJoiningDate());
                    emp.setRole(request.getRole());
                    emp.setReportingManager(reportingManager.get());
                    emp.setDept(dept.get());
                    empRepository.save(emp);
                    return "Employee updated successfully";
                }).orElse("Department does not exist");
    }

    public String updateDepartment(DepartmentRequest request, String id) {
        Optional<Employee> hod = empRepository.findById(Integer.valueOf(request.getHod()));
        if(hod.isEmpty())
            return "Hod does not exist";
        return deptRepository.findById(Integer.valueOf(id)).map(department -> {
            department.setName(request.getName());
            department.setCreationDate(request.getCreationDate());
            department.setHod(hod.get());
            deptRepository.save(department);
            return "Employee updated successfully";
        }).orElse("Department does not exist");
    }
    public String updateDepartmentId(int id, int deptId) {
        Optional<Department> dept = deptRepository.findById(deptId);
        if (dept.isEmpty())
            return "Department does not exist";
        Optional<Employee> employee = empRepository.findById(id);
        if (employee.isPresent()){
            Employee emp = employee.get();
            empRepository.save(emp);
        }
        else
            return "Employee does not exist";
        return  "Department updated successfully";
    }

    public DepartmentExpandResponse getDepartmentEmployeeList(String id, String expand) throws Exception {
        Optional<Department> dept = deptRepository.findById(Integer.valueOf(id));
        DepartmentExpandResponse response = new DepartmentExpandResponse();
        if(dept.isEmpty())
            throw new Exception("Department does not exist");
        if(expand.equals("Employee")) {
            Optional<List<Employee>> employeeList = empRepository.findAllByDept(dept.get());
            if (employeeList.isEmpty()) {
                response.setEmployees(null);
            }
            List<EmployeeResponse> employeeResponseList = employeeList.get().stream().map(employee -> {
                return EmployeeResponse.builder().name(employee.getName())
                        .dept(String.valueOf(employee.getDept().getDept_id()))
                        .role(employee.getRole()).joiningDate(String.valueOf(employee.getJoiningDate()))
                        .salary(String.valueOf(employee.getSalary())).address(String.valueOf(employee.getAddress()))
                        .dateOfBirth(String.valueOf(employee.getDateOfBirth())).bonusPercentage(String.valueOf(employee.getBonusPercentage())).
                        reportingManager(employee.getReportingManager()!=null?String.valueOf(employee.getReportingManager().getId()):null)
                        .build();
            }).toList();
            response.setEmployees(employeeResponseList);
        }
        response.setDepartment(
                DepartmentResponse.builder().name(dept.get().getName()).creationDate(dept.get().getCreationDate())
                        .hod(String.valueOf(dept.get().getHod()!=null?dept.get().getHod().getId():null)).build()
        );
        return response;
    }

    public String deleteDepartment(int id) {
            Optional<Department> dept = deptRepository.findById(Integer.valueOf(id));
            if(dept.isPresent())
            {
                empRepository.deleteByDept(dept.get());
                deptRepository.deleteById(id);
                return "Department deleted successfully";
            }
            else
                return "Department does not exist";

//        return "Department deleted successfully";
    }
}
