package com.company.employee.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PerformanceReviewDTO {
    private LocalDate reviewDate;
    private Double score;
    private String reviewComments;
}
