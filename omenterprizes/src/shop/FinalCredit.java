package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FinalCredit
 */
@WebServlet("/FinalCredit")
public class FinalCredit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalCredit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String name = request.getParameter("name");
		double totalCreditAmount = Database.totalDebitAmount(name);
		double totalCashDepo = Database.totalCashDepo(name);
		double totalReturnAmount = Database.totalReturnAmount(name);
		double totalDebitAmount = totalReturnAmount + totalCashDepo;
		out.print("<head>");
		out.print("<style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}");
				out.print("h1{margin: 1em 0 0.5em 0;font-weight: 600;font-family: 'Titillium Web', sans-serif;position: relative;  font-size: 36px;line-height: 40px;padding: 15px 15px 15px 15%;color: #355681;box-shadow: inset 0 0 0 1px rgba(53,86,129, 0.4), inset 0 0 5px rgba(53,86,129, 0.5),inset -285px 0 35px white;border-radius: 0 10px 0 10px;background: #fff u   rl(../images/bartoszkosowski.jpg) no-repeat center left;}");
		out.print("h2{margin:2px;font-weight: normal;position: relative;text-shadow: 0 -1px rgba(0,0,0,0.6);font-size: 28px;line-height: 40px;background: #355681;background: rgba(53,86,129, 0.8);border: 1px solid #fff;padding: 5px 15px;color: white;border-radius: 0 10px 0 10 px;box-shadow: inset 0 0 5px rgba(53,86,129, 0.5);font-family: 'Muli', sans-serif;}");
		out.println("</style>");
		out.print("</head>");
		out.print("<body>");
		out.print("<h1 style = 'text-align:center'>Om Enterprizes</h1>");
		out.print("<h2 style = 'text-align:center'>"+name+"<h2>");
		out.print("<h2>Total amount credited is :"+totalCreditAmount+"</h2>");
		out.print("<h2>Total Cash Deposited is: " + totalDebitAmount + "</h2>");
		if(totalDebitAmount<totalCreditAmount){
			out.print("<h2>"+name+"has been credited by amount "+(totalCreditAmount-totalDebitAmount)+"</h2>");
		}
		else{
			out.print("<h2>"+name+" has paid amount: "+(totalDebitAmount-totalCreditAmount)+"more to OmEnterprizes");
		}
		
	
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("select *from cashDeposit where name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			out.print("<table><tr><th>Date</th><th>Cash Deposit</th></tr>");
			while(rs.next()){
				out.print("<tr><td>"+ rs.getDate("date") + "</td><td>" + rs.getString("cash"));
			}
			out.print("<tr><td>Total</td><td>" + totalDebitAmount+"</td></tr></table>" );
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
