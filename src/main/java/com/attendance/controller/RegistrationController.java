package com.attendance.controller;

import com.attendance.model.Employee;
import com.attendance.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final EmployeeService employeeService;

    public RegistrationController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
        @Valid @ModelAttribute("employee") Employee employee,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            return "register";
        }
        
        if (employeeService.emailExists(employee.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        employeeService.registerEmployee(employee);
        return "redirect:/login?registered";
    }
}