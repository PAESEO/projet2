package com.projet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Recuperation extends HttpServlet {
	
	public List<String[]> getData() throws SQLException{
		String myDriver = "com.mysql.cj.jdbc.Driver";
	    String myUrl = "jdbc:mysql://localhost/projet?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	    
	    Connection conn = null;
	    
	    String query = "SELECT * FROM villes;";
	    
	    List<String[]> donnees = new ArrayList<String[]>();

		try {
			
			Class.forName(myDriver);
			
			conn = DriverManager.getConnection(myUrl, "root", "root");
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			
			while (rs.next())
		      	{
					String[] result = new String[7];
					result[0] = ""+rs.getInt("id");
					result[1] = rs.getString("code_commune");
					result[2] = rs.getString("nom_commune");
					result[3] = rs.getString("code_postal");
					result[4] = rs.getString("libelle_acheminement");
					result[5] = rs.getString("ligne_5");
					result[6] = rs.getString("coordonnees_gps");
			        donnees.add(result);
		      	}
			
			stmt.close();
		} catch (Exception e) {
			System.err.println("got an exception");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("tes");
			conn.close();
			System.out.println("test");
		}
		return donnees;
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		List<String[]> donnees = new ArrayList<String[]>();
		try {
			donnees = getData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    request.setAttribute( "donnees", donnees );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/projet.jsp" ).forward( request, response );
	}
}
