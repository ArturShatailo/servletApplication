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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        //get id of the employee has to be deleted
        int id = getID(request);

        //Call delete method of EmployeeRepository class object.
        //Method is implemented from Crudable.
        int status = employeeRepository.delete(id);

        if (status > 0) {
            out.print("Record removed successfully!");
            response.sendRedirect("viewServlet");
        } else {
            out.println("Sorry! unable to remove record");
        }
        out.close();
    }
}
