package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Total
 */
@WebServlet("/Total")
public class Total extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Total() {
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
		double grandTotal=0 ;
		
		out.print(name);
		out.print("<head>");
		out.print("<style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style>");
		out.print("</head>");
		out.print("<h1>"+"Costumer Name: " + name+"</h1>");
		out.print("<br>");
		
		out.print("<table>");
		out.print("<tr><th>Date</th><th>ItemName</th><th>Cost </th><th>Size</th><th>Quantity</th><th>TotalCost</th></tr>");
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("select * from billing where name = ? and date = curdate() ");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				out.print("<tr><td>"+rs.getString("date")+"</td><td>" +rs.getString("itemName")+" </td><td>"+rs.getString("cost")+"</td><td> "+rs.getString("size")+"</td><td> "+rs.getString("totalpack")+"</td><td> "+rs.getString("totalcost")+"</td></tr>");
				grandTotal += rs.getDouble("totalcost");
			}
			con.close();
			out.print("<tr><td>GrandTotal: </td><td>--</td><td>--</td><td>--</td><td>--</td><td>"+grandTotal+"</td></tr>");
			out.print("</table>");
			out.print("<button><a href='index.html'>HomePage</a></button>");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
