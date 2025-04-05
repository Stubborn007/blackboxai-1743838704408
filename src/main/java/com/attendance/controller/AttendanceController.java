package com.attendance.controller;

import com.attendance.model.Attendance;
import com.attendance.model.Employee;
import com.attendance.service.AttendanceService;
import com.attendance.service.EmployeeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    public AttendanceController(
        AttendanceService attendanceService,
        EmployeeService employeeService
    ) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    @PostMapping("/check-in")
    public String checkIn(@AuthenticationPrincipal Employee employee) {
        attendanceService.checkIn(employee);
        return "redirect:/dashboard";
    }

    @PostMapping("/check-out")
    public String checkOut(@AuthenticationPrincipal Employee employee) {
        List<Attendance> activeAttendance = attendanceService
            .getEmployeeAttendance(employee.getId())
            .stream()
            .filter(a -> a.getCheckOut() == null)
            .toList();
        
        if (!activeAttendance.isEmpty()) {
            attendanceService.checkOut(activeAttendance.get(0));
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/history")
    public String attendanceHistory(
        @AuthenticationPrincipal Employee employee,
        @RequestParam(required = false) Integer month,
        @RequestParam(required = false) Integer year,
        Model model
    ) {
        LocalDate now = LocalDate.now();
        int selectedYear = (year != null) ? year : now.getYear();
        int selectedMonth = (month != null) ? month : now.getMonthValue();
        
        LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        
        model.addAttribute("attendanceRecords", 
            attendanceService.getEmployeeAttendanceBetweenDates(
                employee.getId(),
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
            ));
            
        model.addAttribute("months", java.time.Month.values());
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("currentYear", now.getYear());
        
        return "attendance/history";
    }
}