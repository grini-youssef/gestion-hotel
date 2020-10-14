package com.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class NouveauClient extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale, paneHaut, paneCentre, paneBas;
	public JLabel label0, label1, label2, label3, label4, label5, label6;
	protected JTextField nomComplet, cin, telephone, adresse, pays;
	protected JComboBox sexe;
	String[] typeSexe = { "Masculin", "Féménin" };
	JButton ajouter, update;
	public static java.util.Date date;
	public static java.sql.Date sqlDateMoment;

	public NouveauClient() {
		this.setTitle("Gestion d'hotel");
		this.setSize(580, 660);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);

		label0 = new JLabel("Ajouter un client");
		label0.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		label0.setForeground(new Color(240,0,0));
		label1 = new JLabel("CIN Client : ");
		label2 = new JLabel("Nom Complet : ");
		label3 = new JLabel("Sexe :");
		label4 = new JLabel("Téléphone : ");
		label5 = new JLabel("Adresse : ");
		label6 = new JLabel("Pays :");

		cin = new JTextField();
		nomComplet = new JTextField();
		sexe = new JComboBox(typeSexe);
		telephone = new JTextField();
		adresse = new JTextField();
		pays = new JTextField();

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
		paneCentre.add(label2);
		paneCentre.add(nomComplet);
		paneCentre.add(label3);
		paneCentre.add(sexe);
		paneCentre.add(label4);
		paneCentre.add(telephone);
		paneCentre.add(label5);
		paneCentre.add(adresse);
		paneCentre.add(label6);
		paneCentre.add(pays);
		paneBas.setBorder(new EmptyBorder(0, 0, 40, 0));
		paneCentre.setBorder(new EmptyBorder(30, 30, 30, 30));
		panePrincipale.add(paneHaut, BorderLayout.NORTH);
		panePrincipale.add(paneCentre, BorderLayout.CENTER);
		panePrincipale.add(paneBas, BorderLayout.SOUTH);

		this.setContentPane(panePrincipale);
		this.setVisible(true);

//-----------------------les boutons---------------------------
		ajouter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				date = new java.util.Date();
				sqlDateMoment = new java.sql.Date(date.getTime());
				String sqlAjouter = "INSERT INTO client VALUES ('" + cin.getText() + "','" + nomComplet.getText()
						+ "','" + sexe.getSelectedItem() + "'," + telephone.getText() + ",'" + adresse.getText() + "','"
						+ pays.getText() + "','" + sqlDateMoment + "');";
				ajouter(sqlAjouter);

			}
		});
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = (String) ClientInterface.table.getValueAt(ClientInterface.table.getSelectedRow(),0);
				String sqlUpdate = "UPDATE client SET CIN = '" + cin.getText() + "', Nom_Complet = '"
						+ nomComplet.getText() + "', Sexe = '" + sexe.getSelectedItem() + "', Téléphone = '"
						+ telephone.getText() + "',Adresse = '" + adresse.getText() + "', Pays = '" + pays.getText()
						+ "' WHERE CIN = '" + id + "';";
				ClientInterface.model.removeRow(ClientInterface.table.getSelectedRow());
				ajouter(sqlUpdate);
			}
		});
	}

	public static Connection connecterDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://remotemysql.com:3306/GOLFiWX3lb";
			Connection cnx = DriverManager.getConnection(url, "GOLFiWX3lb", "BdMPcCjDf3");
			return cnx;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void ajouter(String sql) {
		if (cin.getText().equals("") | nomComplet.getText().equals("") | sexe.getSelectedItem().toString().equals("")
				| telephone.getText().equals("") | adresse.getText().equals("") | pays.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Veuillez entrer tous les informations correctement!");
		} else {
			try {
				Connection connection = connecterDB();
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "L'opération a été éffectuer avec succée !");
				ClientInterface.model.addRow(new String[] { cin.getText(), nomComplet.getText(),
						sexe.getSelectedItem().toString(), telephone.getText(), adresse.getText(), pays.getText() });
				statement.close();
				connection.close();
				dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "IL YA UN ERREUR !!!");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		NouveauClient f = new NouveauClient();
		f.setVisible(true);
	}
}
