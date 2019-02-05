package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddItems
 */
@WebServlet("/AddItems")
public class AddItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		int unitCost;
		String itemName = request.getParameter("itemName");
		int itemTotalCost = Integer.parseInt(request.getParameter("itemTotalCost"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		if(quantity == 180){
			unitCost = itemTotalCost/48;
		}
		else if(quantity == 375){
			unitCost = itemTotalCost/24;
		}
		else{
			unitCost = itemTotalCost/12;
		}
		
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("insert into iteminfo(itemName,quantity,itemTotalCost,unitCost)values(?,?,?,?)");
			ps.setString(1, itemName);
			ps.setInt(2, quantity);
			ps.setInt(3, itemTotalCost);
			ps.setInt(4, unitCost);
			
		    int i = ps.executeUpdate();
		    if(i>0){
		    	out.print("Recorded Successfully");
		    }
		    
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
