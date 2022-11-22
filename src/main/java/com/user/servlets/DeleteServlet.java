package com.user.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
      
        public void init() {
        	try {
        		connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "test");
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        	
        	
        }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
		String email = request.getParameter("email");
		
		try {
			statement = connection.createStatement();
			int result = statement.executeUpdate("delete from user where email='"+email+"'" );
			PrintWriter out = response.getWriter();
			if (result>0) {
			out.print("<H1>User deleted</H1>");
			}
			else {
				out.print("<H1>User not found in the database.</H1>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
