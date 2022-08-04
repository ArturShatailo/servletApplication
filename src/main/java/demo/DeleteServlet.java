package demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet implements InstanceRepository, IdIterable {

    /**
     * Creates id int variable and starts (UPDATE: try-catch-finally) try - catch structure check.
     * Inside try block servlet tries to call method getID, implemented from IdItable interface to get id value
     * from request. In case of no NumberFormatException, calls delete() method of employeeRepository variable.
     * Then, checks status and in case of successful deleting, redirects to viewServlet.
     *
     * In case of NumberFormatException prints error message in catch block and closes 'out' PrintWriter in finally block.
     *
     * @param request HttpServletRequest request received by servlet
     * @param response HttpServletResponse response sent by servlet
     * @throws ServletException Exception in case of servlet error, ErrorHandler servlet should be called
     * but is not created
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Creates object to print response.
        PrintWriter out = response.getWriter();

        //int variable for further filling with id value of object that should be deleted
        int id = 0;

        try{
            //calls method that returns "id" value from 'request' HttpServletRequest object
            id = getID(request);

            //Call delete method of EmployeeRepository class object.
            //Method is implemented from Crudable.
            int status = employeeRepository.delete(id);

            if (status > 0) {
                out.print("Record removed successfully!");
                response.sendRedirect("viewServlet");
            } else {
                out.println("Sorry! unable to remove record");
            }

        } catch (NumberFormatException npe){
            out.println("Unable to remove record");
        } finally {
            out.close();
        }

    }
}
