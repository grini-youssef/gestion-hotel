package com.projet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.client.NouveauClient;

public class Actualiser {
	
	public static java.util.Date date;
	public static java.sql.Date sqlDateMoment;
	static ArrayList<Integer> listChambre;
	
	public static void actualiser() {
		listChambre = new ArrayList<Integer>();
		date = new java.util.Date();
		sqlDateMoment = new java.sql.Date(date.getTime());
		Connection connection = NouveauClient.connecterDB();
		String sql = "SELECT n_chambre FROM reservation WHERE date_fin < '"+sqlDateMoment+"';";
		
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				listChambre.add(rs.getInt("n_chambre"));
			}
			stm.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}
		
		for (Integer n : listChambre) {
			String sqlchmre = "UPDATE chambre SET reserver= '0' WHERE n_chambre = '" + n + "';";
			Statement stm2;
			try {
				stm2 = connection.createStatement();
				stm2.executeUpdate(sqlchmre);
				stm2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		java.util.Date date = new java.util.Date();
//		java.sql.Date sqlDateMoment = new java.sql.Date(date.getTime());
//		System.out.println(sqlDateMoment);
//		
//
//	}

}
