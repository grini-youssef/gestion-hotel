package com.reglement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class ReglementInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panePrincipale,paneHaut,paneHaut2;
	private JLabel titre,par,vide;
	private JButton bChercher,bNouveau,bModifier,bSupprimer;
	JComboBox<String> type_reglement;
	public static JTable table;
	static String[] titreColonnes = {"Nom de client","Prix payé","Date de payement"};
	static String donnees [][]= {};
	String [] label = {"Espece","Chéque","Carte banquaire"};
	public static DefaultTableModel model = new DefaultTableModel(donnees,titreColonnes);
	
	public ReglementInterface() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		titre = new JLabel("Liste des reglements :");
		titre.setFont(new Font(Font.SERIF,Font.BOLD,30));
		type_reglement = new JComboBox<String>(label);
		par = new JLabel("Chercher par :");
		par.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		vide = new JLabel();
		vide.setPreferredSize(new Dimension(540,0));
		
		bNouveau = new JButton("Nouveau");
		bNouveau.setPreferredSize(new Dimension(120,40));
		bModifier = new JButton("Modifier");
		bModifier.setPreferredSize(new Dimension(120,40));
		bSupprimer = new JButton("Supprimer");
		bSupprimer.setPreferredSize(new Dimension(120,40));
		
		//getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
		//table = new JTable(donnees,titreColonnes);
		table = new JTable(model);
		table.setRowHeight(30);
		table.setBackground(new Color(210, 210, 210));
		
		panePrincipale = new JPanel(new BorderLayout());
		paneHaut = new JPanel(new GridLayout(2,1,0,7));
		paneHaut2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		paneHaut2.add(par);
		paneHaut2.add(type_reglement);
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
		
		bNouveau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new NouveauReglement().getContentPane();
				
			}
		});
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		ReglementInterface r = new ReglementInterface();
		r.setVisible(true);
	}
}
