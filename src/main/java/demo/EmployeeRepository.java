package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Crudable<Employee> {

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/employees";
        String user = "postgres";
        String password = "Postgresql";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println("SQL exception please read the error message: ");
            sqlException.printStackTrace();
        }
        return connection;
    }

    @Override
    public int save(Employee employee) {
        int status = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into employees(name, email,country) values (?,?,?)");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return status;
    }

    @Override
    public int update(Employee employee) {

        int status = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("update employees set name=?,email=?,country=? where id=?");
            ps.setString(1, employee.getName());
            ps.setString(3, employee.getEmail());
            ps.setString(2, employee.getCountry());
            ps.setInt(4, employee.getId());

            status = ps.executeUpdate();

            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    @Override
    public int delete(int id) {

        int status = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from employees where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    @Override
    public Employee getById(int id) {

        Employee employee = new Employee();

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from employees where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(4));
                employee.setCountry(rs.getString(3));
            }
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> getAll() {

        List<Employee> listEmployees = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from employees");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(4));
                employee.setCountry(rs.getString(3));

                listEmployees.add(employee);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmployees;
    }
}
