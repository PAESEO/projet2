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

public class CalculDistance extends HttpServlet {
	
	public static final String VUE = "/WEB-INF/calculDistance.jsp";
	
	public String getVille(String code) throws SQLException{
		String myDriver = "com.mysql.cj.jdbc.Driver";
	    String myUrl = "jdbc:mysql://localhost/projet?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	    
	    Connection conn = null;
	    
	    String query = "SELECT coordonnees_gps FROM villes WHERE code_commune = '" + code + "';";
	    String result = "";

		try {
			
			Class.forName(myDriver);
			
			conn = DriverManager.getConnection(myUrl, "root", "root");
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
		      	{
					result = rs.getString("coordonnees_gps");
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
		return result;
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Récupération des champs du formulaire. */
        String ville1 = request.getParameter("ville1");
        String ville2 = request.getParameter("ville2");
        
        String coordVille1 = "";
		String coordVille2 = "";
        
        try {
        	coordVille1 = getVille(ville1);
			coordVille2 = getVille(ville2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String[] coord1 = coordVille1.split(", ");
        String[] coord2 = coordVille2.split(", ");
        
        float lat1 = (float) (Float.valueOf(coord1[0])*Math.PI/180);
        float lon1 = (float) (Float.valueOf(coord1[1])*Math.PI/180);
        float lat2 = (float) (Float.valueOf(coord2[0])*Math.PI/180);
        float lon2 = (float) (Float.valueOf(coord2[1])*Math.PI/180);
        /*
        float DeltaY = lat1-lat2;
        float DeltaX = (float) ((lon1-lon2)*Math.cos((lat1+lat2)/2));
        float Distance= (float) Math.sqrt(DeltaX*DeltaX+DeltaY*DeltaY);
        */
        String valeur = lat1 + "  " + lon1;
        
        float Distance = (float) (6378.137*Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2)));
   
        
        
        //DeltaY = latA-latB et deltaX = (lngA-lngB).Math.cos((latA+latB)/2).
        	//	Distance= Mat.sqrt(DeltaX*DeltaX+DeltaY*DelatY)

        //request.setAttribute( ATT_ERREURS, erreurs );
        request.setAttribute( "valeur", Distance );

        /* Transmission de la paire d'objets request/response à notre JSP */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
