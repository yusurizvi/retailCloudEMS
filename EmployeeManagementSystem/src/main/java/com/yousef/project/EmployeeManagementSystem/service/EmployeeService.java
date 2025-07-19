package com.yousef.project.EmployeeManagementSystem.service;

import com.yousef.project.EmployeeManagementSystem.dto.DepartmentExpandResponse;
import com.yousef.project.EmployeeManagementSystem.dto.DepartmentRequest;
import com.yousef.project.EmployeeManagementSystem.dto.EmployeeRequest;
import com.yousef.project.EmployeeManagementSystem.dto.EmployeeResponse;
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

    public List<Employee> getEmployeeDetails(boolean lookup) {

        if(lookup)
        {
            empRepository.getLookUpData();
        }
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

    public List<Department> getAllDepartments() {
    return deptRepository.findAll();
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
        if(expand.equals("Employee"))
        {
            Optional<List<Employee>> employeeList = empRepository.findAllByDept(dept.get().getDept_id());
            if(employeeList.isEmpty()) {
                response.setEmployees(null);
            }
            response.setEmployees(employeeList.get());
        }
        response.setDepartment(dept.get());
        return response;
    }

    public String deleteDepartment(int id) {
        try{
            empRepository.deleteByDept(id);
            deptRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Department deleted successfully";
    }
}
