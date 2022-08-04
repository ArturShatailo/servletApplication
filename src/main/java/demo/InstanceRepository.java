package demo;

public interface InstanceRepository {

    // Instance of EmployeeRepository class for general usage
    EmployeeRepository employeeRepository = new EmployeeRepository();
}
