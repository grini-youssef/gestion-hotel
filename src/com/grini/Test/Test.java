package com.grini.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class Test {

	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://remotemysql.com:3306/ZxC2Z44y1i";
			Connection cnx = DriverManager.getConnection(url, "ZxC2Z44y1i", "gTd1r7uuDn");
			System.out.println("SUCCESS !!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
