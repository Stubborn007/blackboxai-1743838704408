package com.attendance.service;

import com.attendance.model.Employee;
import com.attendance.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
        EmployeeRepository employeeRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee registerEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        if (employee.getRoles() == null || employee.getRoles().isEmpty()) {
            employee.setRoles(Set.of(Employee.Role.EMPLOYEE));
        }
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public Employee createAdmin(Employee admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRoles(Set.of(Employee.Role.ADMIN));
        return employeeRepository.save(admin);
    }
}