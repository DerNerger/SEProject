package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlDatabase implements IDataBase{

	private Connection con;
	
	public MySqlDatabase(String url, String name, String pw)
			throws ClassNotFoundException, SQLException{
		//load CDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//initialize connection
		con = DriverManager.getConnection(url, name, pw);
	}
	
	@Override
	public boolean isNameFree(String name) throws SQLException {
		Statement stmt =null;
		ResultSet rst =  null;		
		stmt = con.createStatement();
		String query = "SELECT * FROM `User` WHERE Name= '"+name+"';";
		rst = stmt.executeQuery(query);
		
		if(rst.next())
			return false;
		else
			return true;	
	}

	@Override
	public void register(String name, int passWd) throws SQLException {
		Statement stmt =null;
		stmt = con.createStatement();
		String query = "INSERT INTO `User` (`Name`,`PassWd`) VALUES ('"+name+"', '"+passWd+"');";
		stmt.executeUpdate(query);
	}

	@Override
	public boolean loginOk(String name, int passWd) throws SQLException {
		Statement stmt =null;
		ResultSet rst =  null;		
		stmt = con.createStatement();
		String query = "SELECT * FROM `User` WHERE Name= '"+name+"' AND PassWd='"+passWd+"';";
		rst = stmt.executeQuery(query);
		
		if(rst.next())
			return true;
		else
			return false;	
	}
}
