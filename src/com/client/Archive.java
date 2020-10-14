package com.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;

public class Archive extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale,paneHaut,paneHaut2;
	private JLabel titre,chercherLabel,vide;
	private JTextField chercherPane;
	private JButton chercherClient;
	public static JTable table;
	static String[] titreColonnes = {"id reservation","Type chambre","N° Chambre", "Date Debut","Date Fin", "Prix payé","type de payement"};
	static String donnees [][]= {};
	public static DefaultTableModel model = new DefaultTableModel(donnees,titreColonnes);
	int index;
	
	public Archive() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		titre = new JLabel("L'archive :");
		titre.setFont(new Font(Font.SERIF,Font.BOLD,30));
		titre.setForeground(new Color(240,0,0));
		chercherLabel = new JLabel("Chercher l'archive d'un client :");
		chercherLabel.setFont(new Font(Font.SERIF,Font.BOLD,20));
		chercherPane = new JTextField();
		chercherPane.setPreferredSize(new Dimension(100,38));
		chercherClient = new JButton("Chercher");
		chercherClient.setPreferredSize(new Dimension(120,40));
		chercherClient.setBackground(Color.PINK);
		vide = new JLabel();
		vide.setPreferredSize(new Dimension(672,0));
		table = new JTable(model);
		table.setRowHeight(30);
		table.setBackground(new Color(210, 210, 210));
		//table = new JTable(donnees,titreColonnes);
		
		panePrincipale = new JPanel(new BorderLayout());
		paneHaut = new JPanel(new GridLayout(2,1,0,7));
		paneHaut2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paneHaut2.add(chercherLabel);
		paneHaut2.add(chercherPane);
		paneHaut2.add(chercherClient);
		paneHaut2.add(vide);
		paneHaut.add(titre);
		paneHaut.add(paneHaut2);
		panePrincipale.add(paneHaut,BorderLayout.NORTH);
		panePrincipale.add(new JScrollPane(table));
		panePrincipale.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setContentPane(panePrincipale);
		
//---------------------programmation les boutons-------------------
		
		chercherPane.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(e.getSource()==chercherPane)
						chercherClient.doClick(0);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		chercherClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chercher();
				
			}
		});
	}
	
	
	
	public void chercher() {
		int n = model.getRowCount();
		String sqlChercher = "SELECT * FROM reservation WHERE CIN = '"+chercherPane.getText()+"';";
		try {
			Connection cnx = NouveauClient.connecterDB();
			Statement stm = cnx.createStatement();
			ResultSet rs = stm.executeQuery(sqlChercher);
			if(!rs.wasNull()){
				for (int i=n-1 ; i>=0 ; i--) model.removeRow(i);
				}
			while(rs.next()){
				model.addRow(new Object[] { rs.getInt(1), rs.getString(3), rs.getString(4), rs.getDate(5),rs.getDate(6),rs.getString(7),rs.getString(8)});}
			if (rs.first()==false) {
				JOptionPane.showMessageDialog(null, "CE PERSONNE A AUCUN ARCHIVE !!");
				}
			stm.close();
			cnx.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Archive CI = new Archive();
		CI.setVisible(true);

	}
}