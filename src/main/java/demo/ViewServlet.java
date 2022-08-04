package demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/viewServlet")
public class ViewServlet extends HttpServlet implements InstanceRepository {

    /**
     * calls getAll method of employeeRepository object and prints all received
     * objects in returned List from getAll method.
     *
     * @param request HttpServletRequest request received by servlet
     * @param response HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //Create new collection with objects from collection received from
        //getAll Crudable interface method of EmployeeRepository class
        List<Employee> list = employeeRepository.getAll();

        //Print objects from collection 'list'
        for (Employee employee : list) {
            out.print(employee);
        }
        out.close();
    }
}
