package com.chambre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;

import com.client.ClientInterface;
import com.client.NouveauClient;

public class ChambreInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale, paneHaut, paneHaut2;
	private JLabel titre, chercherLabel, vide;
	private JComboBox<String> choisirType;
	private JButton bNouveau, bModifier, bSupprimer;
	public static JTable table;
	static String[] titreColonnes = { "N° de la chambre", "Type de la chambre", "Prix de la chambre",
			"Description de la chambre" };
	static Object donnees[][] = {};
	String[] lables = { "Chambre simple non reservee", "Chambre simple reservee", "Chambre double non reservee",
			"Chambre double reservee" };
	public static DefaultTableModel model = new DefaultTableModel(donnees,titreColonnes);
	int index;

	public ChambreInterface() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		titre = new JLabel("Liste des Chambres :");
		titre.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		chercherLabel = new JLabel("Chercher une chambre :");
		chercherLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		choisirType = new JComboBox<String>(lables);
		choisirType.setSelectedIndex(-1);
		vide = new JLabel();
		vide.setPreferredSize(new Dimension(380, 0));
		bNouveau = new JButton("Nouveau");
		bNouveau.setPreferredSize(new Dimension(120, 40));
		bModifier = new JButton("Modifier");
		bModifier.setPreferredSize(new Dimension(120, 40));
		bSupprimer = new JButton("Supprimer");
		bSupprimer.setPreferredSize(new Dimension(120, 40));
		table = new JTable(model);
		table.setRowHeight(30);
		table.setBackground(new Color(210, 210, 210));

		panePrincipale = new JPanel(new BorderLayout());
		paneHaut = new JPanel(new GridLayout(2, 1, 0, 7));
		paneHaut2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paneHaut2.add(chercherLabel);
		paneHaut2.add(choisirType);
		paneHaut2.add(vide);
		paneHaut2.add(bNouveau);
		paneHaut2.add(bModifier);
		paneHaut2.add(bSupprimer);
		paneHaut.add(titre);
		paneHaut.add(paneHaut2);
		panePrincipale.add(paneHaut, BorderLayout.NORTH);
		panePrincipale.add(new JScrollPane(table));
		panePrincipale.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setContentPane(panePrincipale);
		
		//-------------------les boutons-----------------------
		
		bNouveau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new NouveauChambre().getContentPane();
			}
		});
		bModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()==-1)
                    JOptionPane.showMessageDialog(null,"Veuillez selectioner un chambre.");
				else {
				index = table.getSelectedRow();
				NouveauChambre fenetre = new NouveauChambre();
				fenetre.label0.setText("Modifier une chambre");
				fenetre.n_chambre.setText((String) table.getValueAt(index, 0));
				fenetre.prix_chambre.setText((String) table.getValueAt(index, 2));
				fenetre.description.setText((String) table.getValueAt(index, 3));
				fenetre.ajouter.setVisible(false);
				fenetre.update.setVisible(true);
				
			}
			}
		});
		bSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()==-1)
                    JOptionPane.showMessageDialog(null,"Veuillez selectioner un chambre.");
				else {
					supprimer();
				}
			}
		});
		choisirType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				int c = choisirType.getSelectedIndex();
				String choix = choisirType.getItemAt(c);
				switch (c) {
				case 0:
					chercherchamber("Simple",0);
					break;
				case 1:
					chercherchamber("Simple",1);
					break;
				case 2:
					chercherchamber("Double",0);
					break;
				case 3:
					chercherchamber("Double",1);
					break;
				default:
					break;
				}
				
			}
		});
	}
	
	public void chercherchamber(String choix,int reserver){
		String sqlChercher = "SELECT * FROM chambre WHERE  type_chambre= '"+choix+"'AND reserver='"+reserver+"';";
		try {
			Connection cnx = NouveauClient.connecterDB();
			int n = model.getRowCount();
			Statement stm = cnx.createStatement();
			ResultSet rs = stm.executeQuery(sqlChercher);
			if(!rs.wasNull()){
				for (int i=n-1 ; i>=0 ; i--) model.removeRow(i);
				}
			while(rs.next()){
				model.addRow(new String[] { rs.getString("n_chambre"), rs.getString("type_chambre"), rs.getString("prix"), rs.getString("description")});
			}
			if (rs.first()==false) {
				for (int i=n-1 ; i>=0 ; i--) model.removeRow(i);
				JOptionPane.showMessageDialog(null, "ANCUN RESERVATION EXIXTE !!");
				}
			cnx.close();
			stm.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}
	}
	
	public void supprimer() {
	String n_chambre = (String) table.getValueAt(table.getSelectedRow(),0);
	String sqlSupprimer="DELETE FROM chambre WHERE n_chambre = '"+n_chambre+"';";
	int answer = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce chambre ?", null, JOptionPane.YES_NO_OPTION);
	if(answer==0) {
	try {
		Connection cnx = NouveauClient.connecterDB();
		Statement stm = cnx.createStatement();
		stm.executeUpdate(sqlSupprimer);
		JOptionPane.showMessageDialog(null, "La suppression a été éffectuer avec succée !");
		stm.close();
		cnx.close();
		model.removeRow(table.getSelectedRow());
	} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
		e.printStackTrace();
		}
	 }
  }
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		ChambreInterface CHI = new ChambreInterface();
		CHI.setVisible(true);

	}
}