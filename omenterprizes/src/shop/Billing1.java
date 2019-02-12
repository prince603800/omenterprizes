package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Billing1
 */
@WebServlet("/Billing1")
public class Billing1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Billing1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		response.setContentType("text/html");
		
		ServletContext context = getServletContext();
		String name = (String)context.getAttribute("name");
		
		context.setAttribute(name, "name");
		float totalpack,totalCost,sum=0;
		String item = request.getParameter("item");
		float cost =Integer.parseInt(request.getParameter("cost")) ;
		int size =Integer.parseInt(request.getParameter("size")) ;
		float pack = Integer.parseInt(request.getParameter("pack"));
		float unit = Integer.parseInt(request.getParameter("unit"));
		
		out.print(name);
	    if(size == 180){
	    	
	    			sum = unit/48;
	    			totalpack = pack + sum;
	    }
	    else if(size == 375){
	    	sum = unit/24;
	    	totalpack = pack + sum;
	    }
	    else{
	    	sum = unit/12;
	    	totalpack = pack + sum;
	    }
	    out.print(totalpack+"  " +sum);
		totalCost = cost*totalpack;
		
		
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("insert into billing(date,name ,itemName ,cost ,size ,totalpack ,totalcost )values(curdate(),?,?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, item);
			ps.setFloat(3, cost);
			ps.setInt(4, size);
			ps.setFloat(5, totalpack);
			ps.setFloat(6, totalCost);
			
			int i = 0;
			i = ps.executeUpdate();
			if(i>0){
				RequestDispatcher rd = request.getRequestDispatcher("billingcheck.html");
				rd.include(request, response);
			}
			else{
				
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}

}
