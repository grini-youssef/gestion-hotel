package com.projet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.jfree.ui.RefineryUtilities;

import com.chambre.ChambreInterface;
import com.client.Archive;
import com.client.ClientInterface;
import com.graphique.JavaJFreeChartLineChartExample;
import com.graphique.LineChartSample;
import com.journee.Journee;
import com.reglement.ReglementInterface;
import com.reservation.ReserverInterface;
import com.sun.glass.ui.Window.Level;
import com.sun.javafx.logging.Logger;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

public class Accueil extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel panePrincipale, paneGauche,paneHaut,paneCentre;
	JButton bClient, bReservation, bChambre, bArchive, bStatistique, bSituationGlobale, bQuitter;
	JMenuItem act = new JMenuItem("Actualiser");
	public Accueil() {
		this.setTitle("Gestion d'hotel");
		//this.setSize(1700, 900);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//--------------les bouttons-------------------------
		bClient = new JButton("    Client",new ImageIcon("icone/ajouter.png"));
		bClient.setPreferredSize(new Dimension(150,100)); 
		
		bReservation = new JButton("Reservation",new ImageIcon("icone/reserver.png"));
		bReservation.setPreferredSize(new Dimension(150,100));
		
		bChambre = new JButton("Chambre",new ImageIcon("icone/chambre.png"));
		bChambre.setPreferredSize(new Dimension(150,100));
		
		bArchive = new JButton("Archive",new ImageIcon("icone/planing.png"));
		bArchive.setPreferredSize(new Dimension(150,100));
		
		bStatistique = new JButton("Statistique",new ImageIcon("icone/cloture.png"));
		bChambre.setPreferredSize(new Dimension(150,100));
		
		bSituationGlobale = new JButton("Situation Globale",new ImageIcon("icone/global.png"));
		bSituationGlobale.setPreferredSize(new Dimension(150,100));
		
		bQuitter = new JButton("Quitter",new ImageIcon("icone/quitter.png"));
		bQuitter.setPreferredSize(new Dimension(150,100));
		
		JLabel icone = new JLabel(new ImageIcon("icone/hotel.png"));
		JLabel welcome = new JLabel(new ImageIcon("D:/S3/Java/image.jpg"));
		
		panePrincipale = new JPanel(new BorderLayout());
		paneGauche = new JPanel(new GridLayout(10,1,15,15));
		paneGauche.setBorder(new EmptyBorder(10, 7, 0, 7));
		paneHaut = new JPanel(new BorderLayout());
		//paneHaut.add(new ReserverInterface().getContentPane());
		paneCentre = new JPanel();
		paneCentre.add(welcome);
		paneHaut.add(icone,BorderLayout.NORTH);
		paneHaut.add(paneCentre,BorderLayout.CENTER);
		paneGauche.add(bClient);
		paneGauche.add(bReservation);
		paneGauche.add(bChambre);
		paneGauche.add(bArchive);
		paneGauche.add(bStatistique);
		paneGauche.add(bSituationGlobale);
		paneGauche.add(bQuitter);
		panePrincipale.add(paneGauche,BorderLayout.WEST);
		panePrincipale.add(paneHaut,BorderLayout.CENTER);
//		this.pack();
//		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setJMenuBar(createMenuBar());
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setContentPane(panePrincipale);
		bClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paneCentre.removeAll();
				paneCentre.add(new ClientInterface().getContentPane());
				paneCentre.revalidate();
				paneCentre.repaint();
				
			}
		});
		
		bReservation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				paneCentre.removeAll();
				paneCentre.add(new ReserverInterface().getContentPane());
				paneCentre.revalidate();
				paneCentre.repaint();
			}
		});
		
		bChambre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paneCentre.removeAll();
				paneCentre.add(new ChambreInterface().getContentPane());
				paneCentre.revalidate();
				paneCentre.repaint();
				
			}
		});
		
		bStatistique.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paneCentre.removeAll();
				paneCentre.add(new Journee().getContentPane());
				paneCentre.revalidate();
				paneCentre.repaint();
				
			}
		});
		
		bArchive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paneCentre.removeAll();
				paneCentre.add(new Archive().getContentPane());
				paneCentre.revalidate();
				paneCentre.repaint();
				
			}
		});
		
		bQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		
		bSituationGlobale.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					JavaJFreeChartLineChartExample barChart = new JavaJFreeChartLineChartExample("Line Chart Demo");
					barChart.pack();
					RefineryUtilities.centerFrameOnScreen(barChart);
					barChart.setVisible(true);
					new JavaJFreeChartLineChartExample("Line Chart Demo");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					
				}
		});
		
		act.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Actualiser.actualiser();
				JOptionPane.showMessageDialog(null, "L'opération a été éffectuer avec succée !");
				
			}
		});
	}

	private JMenuBar createMenuBar() {
		JMenuBar menueBar = new JMenuBar();
		
		JMenu fichier = new JMenu("Actualiser");
//		JMenuItem nouveau = new JMenuItem("Nouveau");
//		JMenuItem exporter = new JMenuItem("Exporter");
//		JMenuItem quitter = new JMenuItem("quitter");
		act.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
		fichier.add(act);
//		fichier.add(nouveau);
//		fichier.add(exporter);
//		fichier.add(quitter);
		
//		JMenu parametre = new JMenu("Paramétre");
//		JMenuItem acceuill = new JMenuItem("Accueil de l'application");
//		JMenuItem parametrage = new JMenuItem("paramétre de l'application");
//		parametre.add(acceuill);
//		parametre.add(parametrage);
		
//		JMenu help = new JMenu("Help");
//		JMenuItem helpContent = new JMenuItem("Help content");
//		JMenuItem about = new JMenuItem("About");
//		help.add(helpContent);
//		help.add(about);
		
//		menueBar.add(fichier);
//		menueBar.add(parametre);
		menueBar.add(fichier);
		
		return menueBar;
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		Accueil accueil = new Accueil();
		accueil.setVisible(true);
	}

}
