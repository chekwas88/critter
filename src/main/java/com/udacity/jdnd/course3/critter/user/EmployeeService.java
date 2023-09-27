package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    private EmployeeRepo employeeRepo;

    public  EmployeeService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    public  Employee getEmployee(Long id){
        return employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Employee.class));
    }

    public List<Employee> findAllEmployees(){
        return employeeRepo.findAll();
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable, Long id){
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Employee.class));
        employee.setDaysAvailable(daysAvailable);
        employeeRepo.save(employee);
    }

    public  List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO){
        List<Employee> employees = employeeRepo.findByDaysAvailable(employeeRequestDTO.getDate().getDayOfWeek());
        List<Employee> availableEmployees = new ArrayList<>();
        if(!employees.isEmpty()){
            for(Employee employee : employees){
                if(employee.getSkills().containsAll(employeeRequestDTO.getSkills())){
                    availableEmployees.add(employee);
                }
            }
        }
        return availableEmployees;
    }
}
