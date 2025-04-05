package com.attendance.service;

import com.attendance.model.Attendance;
import com.attendance.model.Employee;
import com.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance checkIn(Employee employee) {
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setStatus("PRESENT");
        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(Attendance attendance) {
        attendance.setCheckOut(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getEmployeeAttendance(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    public List<Attendance> getEmployeeAttendanceBetweenDates(
        Long employeeId, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    ) {
        return attendanceRepository.findByEmployeeIdAndCheckInBetween(
            employeeId, startDate, endDate
        );
    }
}