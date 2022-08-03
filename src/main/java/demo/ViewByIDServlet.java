package demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet implements InstanceRepository, IdIterable {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //get employee object according to received id
        try{
            //get id according to the request
            int id = getID(request);
            Employee employee = employeeRepository.getById(id);
            if (employee == null){
                throw new NullPointerException();
            }
            out.print(employee);
        } catch (NullPointerException npe){
            out.print("Sorry, can't find this object in data base");
        } finally {
            out.close();
        }

    }
}
