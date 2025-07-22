package com.company.employee.service.impl;

import com.company.employee.dto.*;
import com.company.employee.entity.*;
import com.company.employee.exception.ResourceNotFoundException;
import com.company.employee.repository.*;
import com.company.employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeProjectRepository employeeProjectRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<EmployeeSummaryDTO> getFilteredEmployees(
            Double score,
            LocalDate reviewDate,
            List<String> departmentNames,
            List<String> projectNames
    ) {
        logger.info("Filtering employees with score={}, reviewDate={}, departments={}, projects={}",
                score, reviewDate, departmentNames, projectNames);

        List<Employee> allEmployees = employeeRepository.findAll();

        logger.debug("Total employees in DB: {}", allEmployees.size());

        List<EmployeeSummaryDTO> filteredList = allEmployees.stream()
                .filter(emp -> {
                    boolean match = true;

                    // Filter by performance review
                    if (score != null && reviewDate != null) {
                        match = performanceReviewRepository.findAll().stream()
                                .anyMatch(pr -> pr.getEmployee().getId().equals(emp.getId()) &&
                                        pr.getReviewDate().equals(reviewDate) &&
                                        pr.getScore().equals(score));
                    }

                    // Filter by department name
                    if (match && departmentNames != null && !departmentNames.isEmpty()) {
                        match = emp.getDepartment() != null &&
                                departmentNames.stream()
                                        .anyMatch(name -> emp.getDepartment().getName().toLowerCase().contains(name.toLowerCase()));
                    }

                    // Filter by project name
                    if (match && projectNames != null && !projectNames.isEmpty()) {
                        List<EmployeeProject> projects = employeeProjectRepository.findAll().stream()
                                .filter(ep -> ep.getEmployee().getId().equals(emp.getId()))
                                .toList();
                        match = projects.stream()
                                .anyMatch(ep -> projectNames.stream()
                                        .anyMatch(pname -> ep.getProject().getName().toLowerCase().contains(pname.toLowerCase())));
                    }

                    return match;
                })
                .map(this::mapToSummaryDTO)
                .collect(Collectors.toList());
        logger.info("Filtered result count: {}", filteredList.size());
        return filteredList;
    }

    @Override
    @Transactional
    public EmployeeDetailDTO getEmployeeDetailsById(Long id) {
        logger.info("Fetching employee details for ID: {}", id);

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with ID: " + id);
                });

        List<EmployeeProject> epList = employeeProjectRepository.findAll().stream()
                .filter(ep -> ep.getEmployee().getId().equals(emp.getId()))
                .toList();

        List<ProjectDTO> projectDTOs = epList.stream()
                .map(ep -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setId(ep.getProject().getId());
                    dto.setName(ep.getProject().getName());
                    dto.setRole(ep.getRole());
                    return dto;
                })
                .collect(Collectors.toList());

        List<PerformanceReviewDTO> reviewDTOs = performanceReviewRepository
                .findTop3ByEmployeeIdOrderByReviewDateDesc(id)
                .stream()
                .map(pr -> {
                    PerformanceReviewDTO dto = new PerformanceReviewDTO();
                    dto.setReviewDate(pr.getReviewDate());
                    dto.setScore(pr.getScore());
                    dto.setReviewComments(pr.getReviewComments());
                    return dto;
                }).toList();

        EmployeeDetailDTO dto = new EmployeeDetailDTO();
        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setDateOfJoining(emp.getDateOfJoining());
        dto.setSalary(emp.getSalary());
        dto.setDepartment(emp.getDepartment() != null ? emp.getDepartment().getName() : null);
        dto.setProjects(projectDTOs);
        dto.setLast3Reviews(reviewDTOs);

        logger.info("Returning details for employee '{}'", emp.getName());
        return dto;
    }

    private EmployeeSummaryDTO mapToSummaryDTO(Employee emp) {
        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());

        dto.setDepartment(emp.getDepartment() != null ? emp.getDepartment().getName() : null);

        List<String> projects = employeeProjectRepository.findAll().stream()
                .filter(ep -> ep.getEmployee().getId().equals(emp.getId()))
                .map(ep -> ep.getProject().getName())
                .distinct()
                .toList();

        dto.setProjects(projects);

        List<PerformanceReview> reviews = performanceReviewRepository.findAll().stream()
                .filter(pr -> pr.getEmployee().getId().equals(emp.getId()))
                .sorted(Comparator.comparing(PerformanceReview::getReviewDate).reversed())
                .toList();

        if (!reviews.isEmpty()) {
            dto.setLatestScore(reviews.get(0).getScore());
        }

        return dto;
    }
}
