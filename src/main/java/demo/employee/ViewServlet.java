package demo.employee;

import demo.InstanceRepository;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //Create new collection with objects from collection received from getAll Crudable method
        List<Employee> list = employeeRepository.getAll();

        //Print objects from collection 'list' above
        for (Employee employee : list) {
            out.print(employee);
        }
        out.close();
    }
}
