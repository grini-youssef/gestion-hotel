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

public class ClientInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale,paneHaut,paneHaut2;
	private JLabel titre,chercherLabel,vide;
	private JTextField chercherPane;
	private JButton chercherClient,bNouveau,bModifier,bSupprimer;
	//JTable table = new JTable(model);
	public static JTable table;
	static String[] titreColonnes = {"CIN","Nom Complet","Sexe","Téléphone","Adresse","Pays"};
	static String donnees [][]= {};
	public static DefaultTableModel model = new DefaultTableModel(donnees,titreColonnes);
	int index;
	
	public ClientInterface() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		titre = new JLabel("Liste des Clients :");
		titre.setFont(new Font(Font.SERIF,Font.BOLD,30));
		titre.setForeground(new Color(255,0,0));
		chercherLabel = new JLabel("Chercher un client :");
		chercherLabel.setFont(new Font(Font.SERIF,Font.BOLD,20));
		chercherPane = new JTextField();
		chercherPane.setPreferredSize(new Dimension(100,38));
		chercherClient = new JButton("Chercher");
		chercherClient.setPreferredSize(new Dimension(120,40));
		vide = new JLabel();
		vide.setPreferredSize(new Dimension(390,0));
		bNouveau = new JButton("Nouveau");
		bNouveau.setPreferredSize(new Dimension(120,40));
		bModifier = new JButton("Modifier");
		bModifier.setPreferredSize(new Dimension(120,40));
		bSupprimer = new JButton("Supprimer");
		bSupprimer.setPreferredSize(new Dimension(120,40));
		table = new JTable(model);
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
		
		bNouveau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new NouveauClient().getContentPane();
				
			}
		});
		
		
		bModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()==-1)
                    JOptionPane.showMessageDialog(null,"Veuillez selectioner un client.");
				else {
				index = table.getSelectedRow();
				NouveauClient fenetre = new NouveauClient();
				fenetre.label0.setText("Modifier un client");
				fenetre.cin.setText((String) table.getValueAt(index, 0));
				fenetre.nomComplet.setText((String) table.getValueAt(index, 1));
				fenetre.telephone.setText((String) table.getValueAt(index, 3));
				fenetre.adresse.setText((String) table.getValueAt(index, 4));
				fenetre.pays.setText((String) table.getValueAt(index, 5));
				fenetre.ajouter.setVisible(false);
				fenetre.update.setVisible(true);
				}
			}
		});
		
		bSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1)
                    JOptionPane.showMessageDialog(null,"Veuillez selectioner un client.");
				else {
					supprimer();
				}
				
			}
		});
		
		chercherClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chercher();
				
			}
		});
	}
	
	
	
	public void supprimer() {
		String id = (String) table.getValueAt(table.getSelectedRow(),0);
		String sqlSupprimer="DELETE FROM client WHERE CIN = '"+id+"';";
		int answer = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce client ?", null, JOptionPane.YES_NO_OPTION);
		if(answer==0) {
		try {
			Connection cnx = NouveauClient.connecterDB();
			Statement stm = cnx.createStatement();
			stm.executeUpdate(sqlSupprimer);
			JOptionPane.showMessageDialog(null, "La suppression a été éffectuer avec succée !");
			model.removeRow(table.getSelectedRow());
			stm.close();
			cnx.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}
	}
}
	
	public void chercher() {
		String sqlChercher = "SELECT * FROM client WHERE CIN = '"+chercherPane.getText()+"';";
		try {
			Connection cnx = NouveauClient.connecterDB();
			Statement stm = cnx.createStatement();
			ResultSet rs = stm.executeQuery(sqlChercher);
			while(rs.next()){
			if(model.getRowCount()!=0)
				model.removeRow(0);
			model.addRow(new String[] { rs.getString("CIN"), rs.getString("Nom_Complet"), rs.getString("Sexe"), rs.getString("Téléphone"), rs.getString("Adresse"),rs.getString("Pays")});
			}
			if (rs.first()==false) {
				JOptionPane.showMessageDialog(null, "CE CLIENT N'EXISTE PAS !!");
				if(model.getRowCount()!=0)
					model.removeRow(0);
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
		ClientInterface CI = new ClientInterface();
		CI.setVisible(true);

	}
}