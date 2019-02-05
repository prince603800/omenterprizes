package shop;

import java.io.IOException;
import java.io.PrintWriter;

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
		double totalDebitAmount = Database.totalCashDepo(name);
		out.print(name);
		if(totalDebitAmount<totalCreditAmount){
			out.print("<h1>"+name+"has been credited by amount "+(totalCreditAmount-totalDebitAmount)+"</h1>");
		}
		else{
			out.print("<h1>"+name+"has paid amount "+(totalDebitAmount-totalCreditAmount)+"more to OmEnterprizes");
		}
		
	}

}
