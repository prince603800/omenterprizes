package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAKey;
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
 * Servlet implementation class CashDepo
 */
@WebServlet("/CashDepo")
public class CashDepo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CashDepo() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String name = request.getParameter("name");
		double cash = Integer.parseInt(request.getParameter("cash"));
		
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("insert into cashDeposit(date,name ,cash) values(curdate(),?,?)");
			ps.setString(1, name);
			ps.setDouble(2, cash);
		   int i =0;
		   i = ps.executeUpdate();
		   if(i>0){
			   
			   con.close();
			   out.print("<h2>Cash Deposited Successfully</h2>");
			   RequestDispatcher rd = request.getRequestDispatcher("index.html");
			   rd.forward(request, response);
		   }
		   else{
			   out.print("Unable to deposit Please try again");
		   }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
