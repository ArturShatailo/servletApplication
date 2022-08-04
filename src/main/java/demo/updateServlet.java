package demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateServlet")
public class updateServlet extends HttpServlet implements InstanceRepository, IdIterable {

    /**
     * In try-catch-finally structure, calls getID method and using getByID method of employeeRepository object
     * finds Employee object needed to be updated. In case of no NumberFormatException during getID method
     * sets new values from request to found Employee object. In case of exception or status > 0, prints error message.
     *
     * @param request HttpServletRequest request received by servlet
     * @param response HttpServletResponse response sent by servlet
     * @throws ServletException Exception in case of servlet error, ErrorHandler servlet should be called
     * but is not created
     * @throws IOException can be thrown in case of PrintWriter failure.
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        //Gets string values that is requested in request object.
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        try{

            //calls method that returns "id" value from 'request' HttpServletRequest object
            int id = getID(request);

            //get new requested object by id
            Employee employee = employeeRepository.getById(id);

            //set values for employee object
            employee.setName(name);
            employee.setEmail(email);
            employee.setCountry(country);

            //calls method update of EmployeeRepository class and sends 'employee' object as a parameter.
            int status = employeeRepository.update(employee);

            if (status > 0) {
                out.print("Record saved successfully!");
            } else {
                out.println("Sorry! unable to save record");
            }
        } catch (NumberFormatException npe) {
            out.print("Unable to save record");
        } finally {
            out.close();
        }
    }
}
