package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Crudable<Employee> {

    /**
     * Creates database connection constants and using DriverManager and method getConnection
     * connects to the clarified in constants database address with password and username.
     *
     * SQLException is caught in case of database connection is failed.
     * @return connection instance of class Connection
     */
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

    /**
     * <p>
     *     In try-catch calls getConnection method to connect the database.
     *     Creates PreparedStatement object 'ps'. 'ps' is set as return of method prepareStatement()
     *     of connection object, with SQL "insert into" request as a parameter sql.
     *     SQL request is filled out with values from fields of received in @param 'employee' object.</p>
     * <p>
     *     PreparedStatement object method executeUpdate is called to process SQL and return
     *     int value that is set as 'status' variable. Connection object 'connection' is closed here.
     * </p>
     * <p>
     *     In the catch block SQLException will be caught and printed.
     * </p>
     * @param employee Employee object that should be saved in database
     * @return status int variable
     */
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

    /**
     * <p>
     *     In try-catch calls getConnection method to connect the database.
     *     Creates PreparedStatement object 'ps'. 'ps' is set as return of method prepareStatement()
     *     of connection object, with SQL "update" request as a parameter sql.
     *     SQL request is filled out with values from fields of received in @param 'employee' object.</p>
     * <p>
     *     PreparedStatement object method executeUpdate is called to process SQL and return
     *     int value that is set as 'status' variable. Connection object 'connection' is closed here.
     * </p>
     * <p>
     *     In the catch block SQLException will be caught and printed.
     * </p>
     * @param employee Employee object that should be updated in database
     * @return status int variable
     */
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

    /**
     * <p>
     *     In try-catch section the method calls getConnection method to set a connection
     *     to database. Then, PreparedStatement object 'ps' will be created ans set as a return of connection
     *     object method prepareStatement, where SQL "delete" request sent as a parameter.
     *     Value of @param int 'id' is sent to SQL request parameter using executeUpdate method of 'ps' PreparedStatement
     *     object. Connection is closed here.
     * </p>
     * <p>
     *     In the catch block SQLException will be caught and printed.
     * </p>
     * @param id int value that is id field of Employee object
     * @return status int variable
     */
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

    /**
     * <p>
     *     The new Employee object 'employee' is created. In try-catch structure the connection object of class
     *     Connection is created using getConnection method.
     *     The object 'ps' of class PreparedStatement is created then and filled with return of method prepareStatement
     *     of connection object of class Connection. prepareStatement receives as a parameter 'sql' SQL "select" request.
     *     with filled @param 'id' value that is set by setInt method of 'ps' object of PreparedStatement class.
     * </p>
     * <p>
     *     SQL is processed using executeQuery method of 'ps' object of PreparedStatement class. The return of
     *     executeQuery method is set as resultSet object 'rs'.
     *     Using method next() of 'rs' object of class ResultSet, method checks the availability of element in ResultSet
     *     and is it's true, sets values from 'rs' object as fields of created Employee object 'employee'.
     *     Connection is closed here.
     * </p>
     * <p>
     *     SQLException will be caught by catch block.
     * </p>
     *
     * @param id int value that is 'id' field of Employee object in database that should be received
     * @return Employee object that is received from database by 'id' field value.
     */
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

    /**
     * <p>
     *     Creates List collection listEmployees of Employee objects.
     *     In try-catch section, calls getConnection method and creates PreparedStatement object using
     *     prepareStatement method of connection object, with SQL "select" request.
     *     ResultSet object is created by method executeQuery.
     * </p>
     * <p>
     *     Using next() method of ResultSet object and while loop, method iterates all received objects and creates
     *     new Employee object for each with filled fields as values of ResultSet each object.
     *     Adds created Employee objects into listEmployees collection.
     *     Connection is closed here.
     * </p>
     * <p>
     *     Catch block catches SQLException and prints it.
     * </p>
     *
     * @return List of Employee objects created with data from database.
     */
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
