package com.example.registerservlet;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.InvalidClassException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class RegisterServlet
 */
@SuppressWarnings("unused")
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:mysql://localhost:3306/my_database?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "Fortuner@98";

	private static Object conn;

	private Object connection;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM users";
			ResultSet resultSet = statement.executeQuery(sql);
			request.setAttribute("resultSet", resultSet);
			 out.println("<table border='1'>");
	            out.println("<tr><th>ID</th><th>Username</th><th>Email</th></tr>");
	            while (resultSet.next()) {
	                out.println("<tr>");
	                out.println("<td>" + resultSet.getString("id") + "</td>");
	                out.println("<td>" + resultSet.getString("username") + "</td>");
	                out.println("<td>" + resultSet.getString("email") + "</td>");
	                out.println("</tr>");
	            }
	            out.println("</table>");
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String n = request.getParameter("id");
		String p = request.getParameter("username");
		String e = request.getParameter("email");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_database", "root",
					"Fortuner@98");

			PreparedStatement ps = con.prepareStatement("insert into users(id,username,email) values(?,?,?)");

			ps.setString(1, n);
			ps.setString(2, p);
			ps.setString(3, e);

			int i = ps.executeUpdate();
			if (i > 0)
				out.print("user details successfully registerd...<br>");

			try {
				PreparedStatement selectPs = con.prepareStatement("select * from users");
				ResultSet rs = selectPs.executeQuery();

				ResultSet rs1 = selectPs.executeQuery();

	            // Generate HTML table to display user data
	            out.println("<table border='1'>");
	            out.println("<tr><th>ID</th><th>Username</th><th>Email</th></tr>");
	            while (rs1.next()) {
	                out.println("<tr>");
	                out.println("<td>" + rs1.getString("id") + "</td>");
	                out.println("<td>" + rs1.getString("username") + "</td>");
	                out.println("<td>" + rs1.getString("email") + "</td>");
	                out.println("</tr>");
	            }
	            out.println("</table>");
	        }finally {
	        }
	        
	        con.close();
	    } catch (Exception ex) {
	        out.println("Error: " + ex.getMessage());
	        ex.printStackTrace(out);
	    }
	    }

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

}
