package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Billing
 */
@WebServlet("/Billing")
public class Billing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Billing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
		ArrayList<String> item1 = new ArrayList<String>();
		ArrayList<Integer> cost1 = new ArrayList<Integer>();
		ArrayList<Integer> quantity = new ArrayList<Integer>();
		ArrayList<Integer> totalCost1 = new ArrayList<Integer>();
		
		float totalpack,totalCost,sum=0;
		String name = request.getParameter("name");
		String item = request.getParameter("item");
		float cost =Integer.parseInt(request.getParameter("cost")) ;
		int size =Integer.parseInt(request.getParameter("size")) ;
		float pack = Integer.parseInt(request.getParameter("pack"));
		float unit = Integer.parseInt(request.getParameter("unit"));
		
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
		
		ServletContext context = getServletContext();
		context.setAttribute("name", name);
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
				RequestDispatcher rd = request.getRequestDispatcher("billing1.html");
				rd.include(request, response);
			}
			else{
				out.print("Not added Please try again");
				RequestDispatcher rd = request.getRequestDispatcher("billing.html");
				rd.include(request, response);
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		
	}

	
	
}
