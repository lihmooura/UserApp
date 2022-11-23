package com.user.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
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
import java.util.Enumeration;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet(urlPatterns="/addServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
      
	 public void init(ServletConfig config) {
     	try {
     		ServletContext context = config.getServletContext();
     		
     		System.out.print("init()");
     		
     		Enumeration<String> parameterNames = context.getInitParameterNames();
     		
     		while(parameterNames.hasMoreElements()) {
     			String eachName = parameterNames.nextElement();
     			System.out.println("Context param name: " + eachName);
     			System.out.println("Context param element: " + context.getInitParameter(eachName));
     		}
     		
     		Class.forName("com.mysql.jdbc.Driver");
     		connection = DriverManager.getConnection(context.getInitParameter("dbUrl"), 
     					context.getInitParameter("dbUser"), 
     					context.getInitParameter("dbPassword"));
     	} catch (SQLException e) {
     	
     		e.printStackTrace();
     	} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	
     	
     }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			statement = connection.createStatement();
			int result = statement.executeUpdate("insert into user table values('"+firstName+"','"+lastName+"','"+email+"', "
					+ "'"+password+"')");
			PrintWriter out = response.getWriter();
			if (result>0) {
			out.print("<H1>USER CREATED</H1>");
			}
			else {
				out.print("<H1>ERROR CREATING USER</H1>");
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
