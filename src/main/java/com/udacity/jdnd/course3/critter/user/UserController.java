package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.Mapper;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    private final PetService petService;
    public  UserController(CustomerService customerService, EmployeeService employeeService, PetService petService){
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = Mapper.convertCustomerDTOToCustomer(customerDTO, petService);
        return  Mapper.convertCustomerToCustomerDTO(customerService.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
       return Mapper.convertCustomersToCustomerDTOs(customerService.findAllCustomers());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return Mapper.convertCustomerToCustomerDTO(customerService.getPetOwner(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = Mapper.convertEmployeeDTOToEmployee(employeeDTO);
        return Mapper.convertEmployeeToEmployeeDTO(employeeService.saveEmployee(employee));
    }


    @GetMapping ("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return Mapper.convertEmployeeToEmployeeDTO(employeeService.getEmployee(employeeId));
    }


    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setDaysAvailable(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
       return Mapper.convertEmployeesToEmployeeDTOs(employeeService.findEmployeesForService(employeeDTO));
    }

}
