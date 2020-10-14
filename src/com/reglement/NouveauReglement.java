package com.reglement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class NouveauReglement extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale,paneHaut,paneCentre,paneBas;
	private JLabel label0,label1,label2,label3,label4;
	public JTextField nomComplet,cin,prixPaye;
	public JComboBox typePay;
	JButton ajouter;
	String [] type = {"Espece","Chéque","Carte bancaire"};
	
	public NouveauReglement() {
		this.setTitle("Gestion d'hotel");
		this.setSize(580, 660);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		
		label0 = new JLabel("Ajouter une Reglement");
		label0.setFont(new Font(Font.SERIF,Font.BOLD,30));
		
		label1 = new JLabel("CIN Client : ");
		label2 = new JLabel("Nom Complet : ");
		label3 = new JLabel("Prix payé :");
		label4 = new JLabel("Type de payement : ");
		
		cin = new JTextField();
		nomComplet = new JTextField();
		prixPaye = new JTextField();
		typePay = new JComboBox(type);
		
		ajouter = new JButton("Valider");
		ajouter.setPreferredSize(new Dimension(150,40));
		
		paneHaut = new JPanel();
		paneBas = new JPanel();
		paneBas.add(ajouter);
		panePrincipale = new JPanel(new BorderLayout());
		paneHaut.add(label0);
		paneCentre = new JPanel(new GridLayout(7,2,0,30));
		paneCentre.add(label1);
		paneCentre.add(cin);
		paneCentre.add(label2);
		paneCentre.add(nomComplet);
		paneCentre.add(label3);
		paneCentre.add(prixPaye);
		paneCentre.add(label4);
		paneCentre.add(typePay);
		paneBas.setBorder(new EmptyBorder(0, 0, 40, 0));
		paneCentre.setBorder(new EmptyBorder(30, 30, 30, 30));
		panePrincipale.add(paneHaut,BorderLayout.NORTH);
		panePrincipale.add(paneCentre,BorderLayout.CENTER);
		panePrincipale.add(paneBas,BorderLayout.SOUTH);
		
		this.setContentPane(panePrincipale);
		this.setVisible(true);
		
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		NouveauReglement f = new NouveauReglement();
		f.setVisible(true);
	}

}
