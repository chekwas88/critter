package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployeesId(Long employeeId);
    List<Schedule> findByPetsId(Long petId);
}
