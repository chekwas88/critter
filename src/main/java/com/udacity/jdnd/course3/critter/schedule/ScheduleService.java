package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    private ScheduleRepo scheduleRepo;
    private EmployeeRepo employeeRepo;
    private CustomerRepo customerRepo;
    private PetRepo petRepo;
    public  ScheduleService(ScheduleRepo scheduleRepo, EmployeeRepo employeeRepo, PetRepo petRepo, CustomerRepo customerRepo){
        this.scheduleRepo = scheduleRepo;
        this.employeeRepo = employeeRepo;
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
    }

    public Schedule saveSchedule(Schedule schedule){
       return scheduleRepo.save(schedule);
    }

    public List<Schedule> findAllSchedule(){
        return scheduleRepo.findAll();
    }

    public List<Schedule> findByEmployeeId(Long employeeId){
        return scheduleRepo.findByEmployeesId(employeeId);
    }

    public List<Schedule> findByPetId(Long petId){
        return scheduleRepo.findByPetsId(petId);
    }
    public Schedule getScheduleLists(List<Long> employeesIds, List<Long> petsIds) {

        List<Employee> employees = employeeRepo.findAllByIdIn(employeesIds);
        List<Pet> pets = petRepo.findAllByIdIn(petsIds);

        Schedule schedule = new Schedule();
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return schedule;
    }

    public List<Schedule> getSchedulesForCustomer(Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new EntityNotFoundException(customerId, Customer.class));

        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new ArrayList<>();

        for (Pet pet : pets) {
            schedules.addAll(scheduleRepo.findByPetsId(pet.getId()));
        }

        return schedules;
    }
}
