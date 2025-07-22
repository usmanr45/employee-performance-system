package com.company.employee.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDetailDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfJoining;
    private Double salary;
    private String department;
    private List<ProjectDTO> projects;
    private List<PerformanceReviewDTO> last3Reviews;
}
