package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule scheduleEmployeeAndPetIdsList = scheduleService.getScheduleLists(scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        Schedule schedule = Mapper.convertScheduleDTOToSchedule(scheduleDTO, scheduleEmployeeAndPetIdsList.getEmployees(),
                scheduleEmployeeAndPetIdsList.getPets());
        return Mapper.convertScheduleToScheduleDTO(scheduleService.saveSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return Mapper.convertScheduleToScheduleDTO(scheduleService.findAllSchedule());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return Mapper.convertScheduleToScheduleDTO(scheduleService.findByPetId(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return  Mapper.convertScheduleToScheduleDTO(scheduleService.findByEmployeeId(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return  Mapper.convertScheduleToScheduleDTO(scheduleService.getSchedulesForCustomer(customerId));
    }
}
