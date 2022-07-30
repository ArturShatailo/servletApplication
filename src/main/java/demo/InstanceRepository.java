package demo;

import demo.employee.EmployeeRepository;

public interface InstanceRepository {
    EmployeeRepository employeeRepository = new EmployeeRepository();
}
