package com.attendance.repository;

import com.attendance.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeId(Long employeeId);
    List<Attendance> findByEmployeeIdAndCheckInBetween(Long employeeId, LocalDateTime start, LocalDateTime end);
}