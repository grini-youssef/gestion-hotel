package com.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;

import com.client.NouveauClient;
import com.toedter.calendar.JDateChooser;

public class ReserverInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale,paneHaut,paneHaut2;
	private JLabel titre,dateDu,au,vide;
	private JButton bChercher,bNouveau,bModifier,bSupprimer;
	private JDateChooser dateDebut,dateFin;
	public static JTable table;
	static String[] titreColonnes = {"id reservation","CIN client","Type chambre","N° Chambre", "Date Debut","Date Fin", "Prix payé","type de payement"}; 
	static Object donnees [][]= {};
	public static DefaultTableModel model = new DefaultTableModel(donnees,titreColonnes);
	int index;
	
	public ReserverInterface() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		titre = new JLabel("Liste des reservations :");
		titre.setFont(new Font(Font.SERIF,Font.BOLD,30));
		titre.setForeground(new Color(240,0,0));
		dateDu = new JLabel("Date du :  ");
		dateDu.setFont(new Font(Font.SERIF,Font.BOLD,20));
		au = new JLabel("AU :  ");
		au.setFont(new Font(Font.SERIF,Font.BOLD,20));
		vide = new JLabel();
		vide.setPreferredSize(new Dimension(270,0));
		
		dateDebut = new JDateChooser();
		dateDebut.setPreferredSize(new Dimension(120,30));
		dateFin = new JDateChooser();
		dateFin.setPreferredSize(new Dimension(120,30));
		
		bChercher = new JButton("Chercher");
		bChercher.setPreferredSize(new Dimension(120,40));
		bNouveau = new JButton("Nouveau");
		bNouveau.setPreferredSize(new Dimension(120,40));
		bModifier = new JButton("Modifier");
		bModifier.setPreferredSize(new Dimension(120,40));
		bSupprimer = new JButton("Supprimer");
		bSupprimer.setPreferredSize(new Dimension(120,40));
		
		//getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
		table = new JTable(model);
		table.setRowHeight(30);
		table.setBackground(new Color(225, 225, 225));
		
		panePrincipale = new JPanel(new BorderLayout());
		paneHaut = new JPanel(new GridLayout(2,1,0,7));
		paneHaut2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paneHaut2.add(dateDu);
		paneHaut2.add(dateDebut);
		paneHaut2.add(au);
		paneHaut2.add(dateFin);
		paneHaut2.add(bChercher);
		paneHaut2.add(vide);
		paneHaut2.add(bNouveau);
		paneHaut2.add(bModifier);
		paneHaut2.add(bSupprimer);
		paneHaut.add(titre);
		paneHaut.add(paneHaut2);
		panePrincipale.add(paneHaut,BorderLayout.NORTH);
		panePrincipale.add(new JScrollPane(table));
		panePrincipale.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setContentPane(panePrincipale);
		//this.setVisible(true);
		
		bNouveau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new NouveuReservation();
			}
		});
		bModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modifierReservation();
				
			}
		});
		
		bSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
		            JOptionPane.showMessageDialog(null,"Veuillez selectioner une reservation.");
				else {
					supprimer();
				}
				
			}
		});
		
		bChercher.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chercher();
				
			}
		});
	}
	
//-----------------programmtion la methode modifier reservation ---------------------
	public void modifierReservation() {
		if(table.getSelectedRow()==-1)
            JOptionPane.showMessageDialog(null,"Veuillez selectioner une reservation.");
		else {
		index = table.getSelectedRow();
		NouveuReservation fenetre = new NouveuReservation();
		fenetre.label0.setText("Modifier une reservation");
		fenetre.cin.setText((String) table.getValueAt(index, 1));
		fenetre.ajouter.setVisible(false);
		fenetre.update.setVisible(true);
		}
	}

//-------------------programmation de la methode supprimer-------------------
	
	public void supprimer() {
		int num =(int) table.getValueAt(table.getSelectedRow(),0);
		String n_chmbre = (String) table.getValueAt(table.getSelectedRow(),3);
		String sqldelete = "DELETE FROM reservation WHERE id_reservation = '"+num+"';";
		String sqlchmre = "UPDATE chambre SET reserver= '0' WHERE n_chambre = '" + n_chmbre + "';";
		int answer = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce reservation de la base de données ?", null, JOptionPane.YES_NO_OPTION);
		if(answer==0) {
		try {
			Connection connection = NouveauClient.connecterDB();
			Statement stm1 = connection.createStatement();
			Statement stm2 = connection.createStatement();
			stm1.executeUpdate(sqldelete);
			stm2.executeUpdate(sqlchmre);
			model.removeRow(table.getSelectedRow());
			JOptionPane.showMessageDialog(null, "La suppression a été éffectuer avec succée !");
			stm1.close();
			stm2.close();
			connection.close();
			}
			catch(SQLException e) {
				JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
				e.printStackTrace();
			}
		}
	}
	
//---------------la methode chercher------------------------------
	
	public void chercher() {
		java.util.Date utilDateDebut = dateDebut.getDate();
		java.sql.Date sqlDateDebut = new java.sql.Date(utilDateDebut.getTime());
		
		java.util.Date utilDateFin = dateFin.getDate();
		java.sql.Date sqlDateFin = new java.sql.Date(utilDateFin.getTime());
		String sqlChercher = "SELECT * FROM reservation WHERE date_debut >= '"+sqlDateDebut+"' AND date_fin <= '"+sqlDateFin+"';";
		try {
			int n = model.getRowCount();
			Connection cnx = NouveauClient.connecterDB();
			Statement stm = cnx.createStatement();
			ResultSet rs = stm.executeQuery(sqlChercher);
			if(!rs.wasNull()){
				for (int i=n-1 ; i>=0 ; i--) model.removeRow(i);
				}
			while(rs.next()){
			model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),rs.getDate(6),rs.getFloat(7),rs.getString(8)});
			}
			if (rs.first()==false) {
				for (int i=n-1 ; i>=0 ; i--) model.removeRow(i);
				JOptionPane.showMessageDialog(null, "ANCUN RESERVATION EXIXTE !!");
				}
			stm.close();
			cnx.close();
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		ReserverInterface r = new ReserverInterface();
		r.setVisible(true);
	}
}
