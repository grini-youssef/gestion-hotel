package com.chambre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.client.NouveauClient;

public class NouveauChambre extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale,paneHaut,paneCentre,paneBas;
	public JLabel label0,label1,label2,label3,label4;
	public JTextField n_chambre,prix_chambre;
	public JTextArea description;
	public JComboBox type_chambre;
	JButton ajouter,update;
	String [] type = {"Simple","Double"};
	
	public NouveauChambre() {
		this.setTitle("Gestion d'hotel");
		this.setSize(500, 500);
		//this.setSize(580, 660);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		label0 = new JLabel("Ajouter une Chambre");
		label0.setFont(new Font(Font.SERIF,Font.BOLD,30));
		
		label1 = new JLabel("N° Chambre : ");
		label2 = new JLabel("Type de la Chambre : ");
		label3 = new JLabel("Prix de la chambre :");
		label4 = new JLabel("Description de la chambre : ");
		
		n_chambre = new JTextField();
		prix_chambre = new JTextField();
		description = new JTextArea();
		type_chambre = new JComboBox(type);
		
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
		paneCentre = new JPanel(new GridLayout(4,2,0,40));
		paneCentre.add(label1);
		paneCentre.add(n_chambre);
		paneCentre.add(label2);
		paneCentre.add(type_chambre);
		paneCentre.add(label3);
		paneCentre.add(prix_chambre);
		paneCentre.add(label4);
		paneCentre.add(description);
		paneBas.setBorder(new EmptyBorder(0, 0, 40, 0));
		paneCentre.setBorder(new EmptyBorder(30, 30, 30, 30));
		panePrincipale.add(paneHaut,BorderLayout.NORTH);
		panePrincipale.add(paneCentre,BorderLayout.CENTER);
		panePrincipale.add(paneBas,BorderLayout.SOUTH);
		
		this.setContentPane(panePrincipale);
		this.setVisible(true);
		
//-----------------------les boutons---------------------------
		ajouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sqlAjouter = "INSERT INTO chambre (n_chambre,type_chambre,prix,description,reserver) VALUES ('" + n_chambre.getText().toString() + "','" + type_chambre.getSelectedItem().toString() + "','" + prix_chambre.getText() + "','"
				+ description.getText().toString() + "','0');";
				ajouter(sqlAjouter);
			}
		});
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String n_chambre = (String) ChambreInterface.table.getValueAt(ChambreInterface.table.getSelectedRow(),0);
				String sqlUpdate = "UPDATE chambre SET type_chambre = '"+type_chambre.getSelectedItem().toString()+ "', prix = '" + prix_chambre.getText() +"',description = '" + description.getText().toString() +"' WHERE n_chambre = '" + n_chambre + "';";
				ChambreInterface.model.removeRow(ChambreInterface.table.getSelectedRow());
				ajouter(sqlUpdate);
			}
		});
		
	}
	
	private void ajouter(String sql) {
		if(n_chambre.getText().equals("")|type_chambre.getSelectedItem().toString().equals("")|prix_chambre.getText().equals("")|description.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Veuillez entrer tous les informations correctement!");
		}else {
			try {
				Connection connection = NouveauClient.connecterDB();
				Statement statement;
				statement = connection.createStatement();
				statement.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "L'opération a été éffectuer avec succée !");
				ChambreInterface.model.addRow(new String[] {n_chambre.getText(),type_chambre.getSelectedItem().toString(),prix_chambre.getText(),description.getText()});
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
		NouveauChambre f = new NouveauChambre();
		f.setVisible(true);
	}

}