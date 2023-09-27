package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

//    Map customers
    public static Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO, PetService petService) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if (customerDTO.getPetIds() != null) {
            customer.setPets(customerDTO.getPetIds().stream().map(id -> petService.getPet(id)).collect(Collectors.toList()));
        }

        return customer;
    }

    public static CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        if (customer.getPets() != null) {
            customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        }
        return customerDTO;
    }

    public static List<CustomerDTO> convertCustomersToCustomerDTOs(List<Customer> customers) {
        return customers.stream().map(Mapper::convertCustomerToCustomerDTO).collect(Collectors.toList());
    }
//Map Employees
    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return employee;
    }

    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public static List<EmployeeDTO> convertEmployeesToEmployeeDTOs(List<Employee> employees) {
        return employees.stream().map(Mapper::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }

//    Map Pets
public static Pet convertPetDTOToPet(PetDTO petDTO, CustomerService customerService) {
    Pet pet = new Pet();
    BeanUtils.copyProperties(petDTO, pet);
    Customer customer = customerService.getCustomer(petDTO.getOwnerId());
    pet.setCustomer(customer);
    return pet;
}

    public static PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }

    public static List<PetDTO> convertPetsToPetDTOs(List<Pet> pet) {
        return pet.stream().map(Mapper::convertPetToPetDTO).collect(Collectors.toList());
    }

    public static Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO, List<Employee> employees, List<Pet> pets) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return schedule;
    }

// Map Schedule
    public static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Employee> employees = schedule.getEmployees();
        List<Long> employeesIds = new LinkedList<>();
        if (employees != null) {
            employeesIds = employees.stream().map(Employee::getId).collect(Collectors.toList());
        }

        scheduleDTO.setEmployeeIds(employeesIds);

        List<Pet> pets = schedule.getPets();
        List<Long> petsIds = new LinkedList<>();
        if (pets != null) {
            petsIds = pets.stream().map(Pet::getId).collect(Collectors.toList());
        }

        scheduleDTO.setPetIds(petsIds);

        return scheduleDTO;
    }

    public static List<ScheduleDTO> convertScheduleToScheduleDTO(List<Schedule> schedules) {
        return schedules.stream().map(Mapper::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }
}
