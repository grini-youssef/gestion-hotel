package com.journee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.client.NouveauClient;


public class Journee extends JFrame {
	
	private JPanel panePrincipale,paneHaut,paneHaut2,paneCentre;
	private JLabel titre,pour,label1,label2,label3,label4,label5,vide;
	JComboBox<String> type_statistique;
	String [] label = {"Cette Journée","Ce mois"};
	public static java.util.Date date = new java.util.Date();
	public static java.sql.Date sqlDateMoment = new java.sql.Date(date.getTime());
	
	public Journee() {
		titre = new JLabel("Statistique :");
		titre.setFont(new Font(Font.SERIF,Font.BOLD,40));
		titre.setForeground(new Color(240,0,0));
		pour = new JLabel("Statistique pour :");
		pour.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		type_statistique = new JComboBox<String>(label);
		type_statistique.setSelectedIndex(-1);
		label1 = new JLabel("Nombre de clients ajoutés :      ");
		label1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		label2 = new JLabel("Nombre de reservations ajoutées :      ");
		label2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		label3 = new JLabel("         Simple :      ");
		label3.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
		label4 = new JLabel("         Double :      ");
		label4.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
		label5 = new JLabel("Nombre total d'argent :      ");
		label5.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		vide = new JLabel();
		vide.setPreferredSize(new Dimension(850, 0));
		
		panePrincipale = new JPanel(new BorderLayout());
		paneCentre = new JPanel(new GridLayout(5,1,0,60));
		paneCentre.setBorder(new EmptyBorder(40, 0, 0, 0));
		paneHaut = new JPanel(new GridLayout(2,1,0,20));
		paneHaut2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paneHaut2.add(pour);
		paneHaut2.add(type_statistique);
		paneHaut2.add(vide);
		paneHaut.add(titre);
		paneHaut.add(paneHaut2);
		paneCentre.add(label1);
		paneCentre.add(label2);
		paneCentre.add(label3);
		paneCentre.add(label4);
		paneCentre.add(label5);
		
		panePrincipale.add(paneHaut,BorderLayout.NORTH);
		panePrincipale.add(paneCentre);
		panePrincipale.setBorder(new EmptyBorder(0, 20, 0, 0));
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setContentPane(panePrincipale);
		
		
		type_statistique.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				int c = type_statistique.getSelectedIndex();
				switch (c) {
				case 0:
					client(1);
					reserver(1);
					break;
				case 1:
					client(2);
					reserver(2);
					break;

				default:
					break;
				}
				
			}
		});
		
	}
	
	public void client(int x) {
		int n=0;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int mois = (calendar.get(Calendar.MONTH))+1;
		String sql="";
		if(x==1)
			sql = "SELECT * FROM client WHERE date_dajouter = '"+sqlDateMoment+"';";
			
		else
			sql = "SELECT * FROM client WHERE 	date_dajouter <= '"+year+"-"+mois+"-31' AND date_dajouter >= '"+year+"-"+mois+"-00';";
		try {
			Connection cnx = NouveauClient.connecterDB();
			Statement stm = cnx.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				n+=1;
			}
			stm.close();
			cnx.close();
		}
		 catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UNE ERREUR !!!");
			e.printStackTrace();
		}
		label1.setText("Nombre de clients ajoutés :      "+n);
	}
	
	public void reserver(int x) {
		int s=0,d=0,total=0;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int mois = (calendar.get(Calendar.MONTH))+1;
		String sql="",sql2="";
		if(x==1) {
			sql = "SELECT prix_paye FROM reservation WHERE type_chambre = 'Simple' AND date_debut = '"+sqlDateMoment+"';";
			sql2 = "SELECT prix_paye FROM reservation WHERE type_chambre = 'Double' AND date_debut = '"+sqlDateMoment+"';";
		}
		else {
			sql = "SELECT prix_paye FROM reservation WHERE 	date_debut <= '"+year+"-"+mois+"-31' AND date_debut >= '"+year+"-"+mois+"-01' AND type_chambre = 'Simple';";
			sql2 = "SELECT prix_paye FROM reservation WHERE 	date_debut <= '"+year+"-"+mois+"-31' AND date_debut >= '"+year+"-"+mois+"-01' AND type_chambre = 'Double';";
			
		}try {
			Connection cnx = NouveauClient.connecterDB();
			Statement stm = cnx.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				s+=1;
				int y = Integer.parseInt(rs.getString("prix_paye"));
				total+=y;
			}
			Statement stm2 = cnx.createStatement();
			ResultSet rs2 = stm.executeQuery(sql2);
			while (rs2.next()) {
				d+=1;
				int y = Integer.parseInt(rs2.getString("prix_paye"));
				total+=y;
			}
			stm.close();
			stm2.close();
			cnx.close();
			}
		 catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}
		int somme = s+d;
		label2.setText("Nombre de reservations ajoutées :      "+somme);
		label3.setText("         Simple :      "+s);
		label4.setText("         Double :      "+d);
		label5.setText("Nombre total d'argent :      "+total);
		
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Journee r = new Journee();
		r.setVisible(true);

	}

}
