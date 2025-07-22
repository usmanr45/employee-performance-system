package com.company.employee.service;

import com.company.employee.dto.EmployeeDetailDTO;
import com.company.employee.dto.EmployeeSummaryDTO;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    List<EmployeeSummaryDTO> getFilteredEmployees(
            Double score,
            LocalDate reviewDate,
            List<String> departmentNames,
            List<String> projectNames
    );

    EmployeeDetailDTO getEmployeeDetailsById(Long id);
}
