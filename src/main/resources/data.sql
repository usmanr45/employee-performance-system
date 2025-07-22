-- DEPARTMENTS
INSERT INTO department (id, name, budget) VALUES (1, 'Engineering', 1000000);
INSERT INTO department (id, name, budget) VALUES (2, 'HR', 500000);
INSERT INTO department (id, name, budget) VALUES (3, 'Marketing', 750000);

-- EMPLOYEES
INSERT INTO employee (id, name, email, department_id, date_of_joining, salary, manager_id)
VALUES
  (1, 'Alice', 'alice@example.com', 1, '2019-04-01', 90000, NULL),
  (2, 'Bob', 'bob@example.com', 1, '2020-07-15', 75000, 1),
  (3, 'Carol', 'carol@example.com', 2, '2021-01-20', 60000, 1);

-- PROJECTS
INSERT INTO project (id, name, start_date, end_date, department_id)
VALUES
  (1, 'Project Atlas', '2022-01-01', '2022-12-31', 1),
  (2, 'HR Revamp', '2022-05-01', '2022-10-01', 2);

-- EMPLOYEE_PROJECT (JOIN TABLE)
INSERT INTO employee_project (employee_id, project_id, assigned_date, role)
VALUES
  (1, 1, '2022-01-05', 'Lead'),
  (2, 1, '2022-02-01', 'Developer'),
  (3, 2, '2022-06-01', 'Coordinator');

-- PERFORMANCE REVIEWS
INSERT INTO performance_review (id, employee_id, review_date, score, review_comments)
VALUES
  (1, 1, '2023-01-15', 4.5, 'Excellent work'),
  (2, 1, '2023-07-10', 4.2, 'Consistently strong'),
  (3, 1, '2024-01-15', 4.8, 'Outstanding'),
  (4, 2, '2023-01-15', 3.5, 'Good'),
  (5, 3, '2023-07-10', 4.0, 'Very good');
