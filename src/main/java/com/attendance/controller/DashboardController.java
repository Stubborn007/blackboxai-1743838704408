package com.attendance.controller;

import com.attendance.model.Attendance;
import com.attendance.model.Employee;
import com.attendance.service.AttendanceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.util.List;

@Controller
public class DashboardController {
    private final AttendanceService attendanceService;

    public DashboardController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(
        @AuthenticationPrincipal Employee employee,
        Model model
    ) {
        LocalDate today = LocalDate.now();
        List<Attendance> todayAttendance = attendanceService
            .getEmployeeAttendanceBetweenDates(
                employee.getId(),
                today.atStartOfDay(),
                today.atTime(23, 59, 59)
            );
        
        // Get current active attendance (if not checked out)
        Attendance currentAttendance = todayAttendance.stream()
            .filter(a -> a.getCheckOut() == null)
            .findFirst()
            .orElse(null);
        
        model.addAttribute("currentAttendance", currentAttendance);
        model.addAttribute("todayAttendance", todayAttendance);
        return "dashboard";
    }
}