package com.company.employee.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeSummaryDTO {
    private Long id;
    private String name;
    private String email;
    private String department;
    private List<String> projects;
    private Double latestScore;
}
