package demo;

import demo.employee.Employee;

import java.util.List;

public interface Crudable<T> {

    int save(Employee employee);

    int update(Employee employee);

    int delete(int id);

    T getById(int id);

    List<T> getAll();

}
