package com.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


import com.client.ClientInterface;
import com.client.NouveauClient;
import com.toedter.calendar.JDateChooser;

public class NouveuReservation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale, paneHaut, paneCentre, paneBas;
	public JLabel label0, label1, label2, label3, label4, label5, label6, label7, label_prix;
	public JTextField nomComplet, cin;
	public JComboBox<String> typeChambre, type_payement;
	public JComboBox nChambre;
	public JDateChooser dateDebut, dateFin;
	JButton ajouter, update;
	String[] chambreType = { "Simple", "Double" };
	String[] label = { "Espece", "Chéque", "Carte banquaire" };
	java.util.Date utilDateDebut;
	java.sql.Date sqlDateDebut;
	java.util.Date utilDateFin;
	java.sql.Date sqlDateFin;
	int key = 0;

	public NouveuReservation() {
		this.setTitle("Gestion d'hotel");
		this.setSize(580, 660);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);

		label0 = new JLabel("Ajouter une reservation ");
		label0.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		label0.setForeground(new Color(240,0,0));
		label1 = new JLabel("CIN Client : ");
		label3 = new JLabel("Type chambre :");
		label4 = new JLabel("N° chambre : ");
		label5 = new JLabel("Date debut : ");
		label6 = new JLabel("Date fin :");
		label2 = new JLabel("Prix payé :");
		label7 = new JLabel("Type payement");

		cin = new JTextField();
		dateDebut = new JDateChooser();
		dateFin = new JDateChooser();
		typeChambre = new JComboBox<String>(chambreType);
		typeChambre.setSelectedIndex(-1);
		nChambre = new JComboBox();
		label_prix = new JLabel();
		type_payement = new JComboBox<String>(label);
		type_payement.setSelectedIndex(-1);
		ajouter = new JButton("Valider");
		ajouter.setPreferredSize(new Dimension(150, 40));
		update = new JButton("Modifier");
		update.setPreferredSize(new Dimension(150, 40));
		update.setVisible(false);

		paneHaut = new JPanel();
		paneBas = new JPanel();
		paneBas.add(ajouter);
		paneBas.add(update);
		panePrincipale = new JPanel(new BorderLayout());
		paneHaut.add(label0);
		paneCentre = new JPanel(new GridLayout(7, 2, 0, 30));
		paneCentre.add(label1);
		paneCentre.add(cin);
		// paneCentre.add(nomComplet);
		paneCentre.add(label3);
		paneCentre.add(typeChambre);
		paneCentre.add(label5);
		paneCentre.add(dateDebut);
		paneCentre.add(label6);
		paneCentre.add(dateFin);
		paneCentre.add(label4);
		paneCentre.add(nChambre);
		paneCentre.add(label2);
		paneCentre.add(label_prix);
		paneCentre.add(label7);
		paneCentre.add(type_payement);
		paneBas.setBorder(new EmptyBorder(0, 0, 40, 0));
		paneCentre.setBorder(new EmptyBorder(30, 30, 30, 30));
		panePrincipale.add(paneHaut, BorderLayout.NORTH);
		panePrincipale.add(paneCentre, BorderLayout.CENTER);
		panePrincipale.add(paneBas, BorderLayout.SOUTH);

		this.setContentPane(panePrincipale);
		this.setVisible(true);

		typeChambre.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				int c = typeChambre.getSelectedIndex();
				String choix = typeChambre.getItemAt(c);
				switch (c) {
				case 0:
					listChambre(choix);
					nChambre.setSelectedIndex(-1);
					break;
				case 1:
					listChambre(choix);
					nChambre.setSelectedIndex(-1);
					break;
				default:
					break;
				}

			}
		});

		nChambre.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				prix();

			}
		});

//-------------------les boutons-----------------------
		ajouter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ajouterReservation();
			}
		});

		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				avantModifier();
				modifier();
			}
		});

	}

//----------methode pour lister les chambre pas encore reserver---------------------

	protected void listChambre(String choix) {
		String sql = "SELECT n_chambre FROM chambre WHERE type_chambre = '" + choix + "' AND reserver = 0 ;";
		try {
			Connection connection = NouveauClient.connecterDB();
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			nChambre.removeAllItems();
			while (rs.next()) {
				nChambre.addItem(rs.getInt("n_chambre"));
			}
			if (rs.first() == false)
				JOptionPane.showMessageDialog(null, "TOUT LES CHAMBRES " + choix + " SONT RESERVE !!");
			stm.close();
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}

	}

	protected void ajouterReservation() {
		if (cin.getText().equals("") | typeChambre.getSelectedIndex() == -1 | type_payement.getSelectedIndex() == -1
				| nChambre.getSelectedIndex() == -1 | dateDebut.getDate() == null | dateFin.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Veuillez entrer tous les informations correctement!");
		} else {
			try {
				utilDateDebut = dateDebut.getDate();
				sqlDateDebut = new java.sql.Date(utilDateDebut.getTime());

				utilDateFin = dateFin.getDate();
				sqlDateFin = new java.sql.Date(utilDateFin.getTime());
				String sql1 = "INSERT INTO reservation (CIN, type_chambre, n_chambre, date_debut, date_fin, prix_paye, type_payement)\r\n"
						+ " VALUES ('" + cin.getText() + "', '" + typeChambre.getSelectedItem() + "', '"
						+ nChambre.getSelectedItem() + "', '" + sqlDateDebut + "', '" + sqlDateFin + "', '"
						+ label_prix.getText() + "', '" + type_payement.getSelectedItem() + "');";
				String sqlChambre = "UPDATE chambre SET reserver= '1' WHERE n_chambre = '" + nChambre.getSelectedItem()
						+ "';";

				Connection connection = NouveauClient.connecterDB();
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql1, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = statement.getGeneratedKeys();
				if (rs != null && rs.next()) {
					key = rs.getInt(1);
				}
				// les deux ligne suivantes pour modifier la reservation de la chambre
				Statement stm = connection.createStatement();
				stm.executeUpdate(sqlChambre);
				JOptionPane.showMessageDialog(null, "L'opération a été éffectuer avec succée !");
				ReserverInterface.model
						.addRow(new Object[] { key, cin.getText(), typeChambre.getSelectedItem().toString(),
								nChambre.getSelectedItem().toString(), sqlDateDebut.toString(), sqlDateFin.toString(),
								label_prix.getText(), type_payement.getSelectedItem().toString() });
				statement.close();
				stm.close();
				connection.close();
				dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
				e.printStackTrace();
			}
		}
	}

//-------------------la methode modifier------------------------------

	public void avantModifier() {
		String numero = (String) ReserverInterface.table.getValueAt(ReserverInterface.table.getSelectedRow(), 3);
		String s = "UPDATE chambre SET reserver= '0' WHERE n_chambre = '" + numero + "';";
		try {
			Connection connection = NouveauClient.connecterDB();
			Statement statement = connection.createStatement();
			statement.executeUpdate(s);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
			e.printStackTrace();
		}
	}

	public void modifier() {
		if (cin.getText().equals("") | typeChambre.getSelectedIndex() == -1 | type_payement.getSelectedIndex() == -1
				| nChambre.getSelectedIndex() == -1 | dateDebut.getDate() == null | dateFin.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Veuillez entrer tous les informations correctement!");
		} else {
			try {
				utilDateDebut = dateDebut.getDate();
				sqlDateDebut = new java.sql.Date(utilDateDebut.getTime());
				utilDateFin = dateFin.getDate();
				sqlDateFin = new java.sql.Date(utilDateFin.getTime());
				int num = (int) ReserverInterface.table.getValueAt(ReserverInterface.table.getSelectedRow(), 0);
				;
				String modifier = "UPDATE reservation SET CIN = '" + cin.getText() + "', type_chambre = '" + typeChambre.getSelectedItem()
						+ "', n_chambre = '" + nChambre.getSelectedItem() + "',date_debut = '" + sqlDateDebut
						+ "', date_fin = '" + sqlDateFin + "', prix_paye='"+label_prix.getText()+"', type_payement='"+type_payement.getSelectedItem()+"' WHERE id_reservation = '" + num + "';";
				String sqlChambre = "UPDATE chambre SET reserver= '1' WHERE n_chambre = '" + nChambre.getSelectedItem()
						+ "';";

				Connection connection = NouveauClient.connecterDB();
				Statement statement = connection.createStatement();
				statement.executeUpdate(modifier);
				// les deux ligne suivantes pour modifier la reservation de la chambre
				Statement stm = connection.createStatement();
				stm.executeUpdate(sqlChambre);
				JOptionPane.showMessageDialog(null, "L'opération a été éffectuer avec succée !");
				ReserverInterface.model
				.addRow(new Object[] { key, cin.getText(), typeChambre.getSelectedItem().toString(),
						nChambre.getSelectedItem().toString(), sqlDateDebut.toString(), sqlDateFin.toString(),
						label_prix.getText(), type_payement.getSelectedItem().toString() });
				ReserverInterface.model.removeRow(ReserverInterface.table.getSelectedRow());
				statement.close();
				stm.close();
				connection.close();
				dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
				e.printStackTrace();
			}

		}
	}

	public void prix() {
		if (dateDebut.getDate() != null && dateFin.getDate() != null) {
			utilDateDebut = dateDebut.getDate();
			utilDateFin = dateFin.getDate();
			long j = utilDateFin.getTime() - utilDateDebut.getTime();
			int jour = (int) ((j / (1000 * 60 * 60 * 24)) + 1);
			String sql = "SELECT prix FROM chambre WHERE n_chambre = '" + nChambre.getSelectedItem() +"';";
			try {
				int total = 0;
				Connection connection = NouveauClient.connecterDB();
				Statement stm = connection.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					// nChambre.addItem(rs.getInt("n_chambre"));
					int taman = rs.getInt("prix");
					total = taman * jour;
				}
				label_prix.setText(total+"");
				stm.close();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		NouveuReservation f = new NouveuReservation();
		f.setVisible(true);
	}

}
