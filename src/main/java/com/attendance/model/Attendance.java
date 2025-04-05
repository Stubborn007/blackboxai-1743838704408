package com.attendance.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Employee employee;
    
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String status; // PRESENT, LATE, ABSENT
    
    private String notes;
}