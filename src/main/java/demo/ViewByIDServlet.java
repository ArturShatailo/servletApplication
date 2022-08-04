package demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet implements InstanceRepository, IdIterable {

    /**
     * try to get id value by getID() method and in case of no NullPointerException, calls getById() method of
     * employeeRepository object to get Employee needed to be displayed. In case of such id absence,
     * throws NullPointerException. In case of there is found Employee object in database with such 'id',
     * prints this Employee object.
     * Catches NumberFormatException or NullPointerException and prints error message, closes 'out' in finally block.
     *
     * @param request HttpServletRequest request received by servlet
     * @param response HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            //Calls method that returns "id" value from 'request' HttpServletRequest object
            int id = getID(request);

            //Gets employee object by method getById od EmployeeRepository class according to the received id in request
            Employee employee = employeeRepository.getById(id);

            //Checks if employee object is not null
            if (employee == null){
                throw new NullPointerException();
            }
            out.print(employee);

        } catch (NumberFormatException npe){
            out.print("Id is null");
        } catch (NullPointerException npe){
            out.print("Sorry, can't find this object in data base");
        } finally {
            out.close();
        }

    }
}
