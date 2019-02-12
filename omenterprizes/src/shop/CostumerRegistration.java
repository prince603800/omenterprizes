package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CostumerRegistration
 */
@WebServlet("/CostumerRegistration")
public class CostumerRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CostumerRegistration() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String firmName= request.getParameter("firmName");
		String pName = request.getParameter("pName");
		String localAddr= request.getParameter("localAddr");
		String perAddr= request.getParameter("perAddr");
		String phneno = request.getParameter("pNum");
		boolean answer = Database.dublicateCostumer(pName, phneno);
		out.print(answer);
		
		//if(answer){
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("insert into costumerinfo(firmName,pName,localAddr,perAddr,phneno)values(?,?,?,?,?)");
			ps.setString(1, firmName);
			ps.setString(2, pName);
			ps.setString(3, localAddr);
			ps.setString(4, perAddr);
			ps.setString(5, phneno);
			int i = 0;
			i = ps.executeUpdate();
			
			if(i>0){
				con.close();
				out.print("Successfully registered");
				//int id =  Database.ReturnCostumerId(pName, phneno);
				//out.print("The costumer id of " + pName +" with phone no " + phneno+ "is " + id);
				RequestDispatcher rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}
			else{
				out.print("Registration Failed");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}//else{
			//out.print("The costumer already exists");
		//}
		
	}

	
	


