package shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	public static Connection conn() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/omenterprizes","root","root");
		return con;
		
	}
	public static void ledger(String name, Double creditamount, Double debitamount){
		
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("insert into ledger(date,name,creditAmount,debitAmount ) values(curdate(),?,?,?)");
			ps.setString(1, name);
			ps.setDouble(2, creditamount);
			ps.setDouble(3, debitamount);
			int i = 0;
			i = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static double totalCashDepo(String name){
		double totalCashDepo=0;
		
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("select *from cashDeposit where name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				totalCashDepo+= rs.getDouble("cash");
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalCashDepo;
	}
	public static double totalDebitAmount(String name){
		
		double totalDebitAmount=0;
		try {
			Connection con = Database.conn();
			PreparedStatement ps = con.prepareStatement("select *from billing where name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				totalDebitAmount+= rs.getDouble("totalcost");
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalDebitAmount;
	}
	
}
