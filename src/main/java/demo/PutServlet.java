package demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet implements InstanceRepository{

    /**
     * creates String values according to request (name, email, country) and creates new Employee object,
     * using created String variables as parameters of constructor.
     * Calls save() method (from Crudable interface) of employeeRepository variable
     * (that is EmployeeRepository object implemented from InstanceRepository interface).
     * The return value of save() method is set to 'status' int. Then, 'status' variable is checked if it's
     * more than 0. In case of true redirects to viewServlet. In case of false, prints error.
     *
     * @param request HttpServletRequest request received by servlet
     * @param response HttpServletResponse response sent by servlet
     * @throws ServletException Exception in case of servlet error, ErrorHandler servlet should be called
     * but is not created
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //get string values that is requested in request object.
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        //create new Employee object
        Employee employee = new Employee(name, email, country);

        //calls method save of EmployeeRepository class and sends 'employee' object as a parameter.
        int status = employeeRepository.save(employee);

        if (status > 0) {
            response.sendRedirect("viewServlet");
        } else {
            out.println("Sorry! unable to update record");
        }
        out.close();
    }
}
