package com.projet;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.client.NouveauClient;
 
 public class Login extends JFrame{
 
 	private JFrame frame;
 	private JTextField txtUsername;
 	private JPasswordField passwordField;
 	Connection conn =null;
 	ResultSet result =null;
    PreparedStatement prepared=null;
     
     
 	/**
 	 * Launch the application.
 	 */
 	public static void main(String[] args)  {
 		EventQueue.invokeLater(new Runnable() {
 			public void run() {
 				try {
 					UIManager.setLookAndFeel(new NimbusLookAndFeel());
 					Login window = new Login();
 					window.frame.setVisible(true);
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 		});
 	}
 
 	/**
 	 * Create the application.
 	 */
 	public Login() {
 		initialize();
 	}
 
 	/**
 	 * Initialize the contents of the frame.
 	 */
 	private void initialize() {
 		frame = new JFrame("LOGIN");
 		frame.getContentPane().setBackground(SystemColor.control);
 		frame.getContentPane().setForeground(Color.WHITE);
 		frame.getContentPane().setLayout(null);
 		conn = NouveauClient.connecterDB();
 		
 		txtUsername = new JTextField();
 		txtUsername.setFont(new Font("Ebrima", Font.ITALIC, 20));
 		txtUsername.setBounds(610, 210, 244, 44);
 		frame.getContentPane().add(txtUsername);
 		txtUsername.setColumns(10);
 		
 		passwordField = new JPasswordField();
 		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
 		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
 		passwordField.setToolTipText("Password");
 		passwordField.setBounds(610, 303, 244, 44);
 		frame.getContentPane().add(passwordField);
 		
 		JLabel lblNewLabel = new JLabel("Username :");
 		lblNewLabel.setFont(new Font("Ebrima", Font.ITALIC, 24));
 		lblNewLabel.setForeground(new Color(240,0,0));
 		lblNewLabel.setBounds(456, 218, 126, 28);//471 - 116(471, 218, 116, 28)
 		frame.getContentPane().add(lblNewLabel);
 		
 		JLabel lblNewLabel_1 = new JLabel("Password : ");
 		lblNewLabel_1.setFont(new Font("Ebrima", Font.PLAIN, 24));
 		lblNewLabel_1.setForeground(new Color(240,0,0));
 		lblNewLabel_1.setBounds(471, 307, 126, 34);//484 - 116(484, 307, 116, 34)
 		frame.getContentPane().add(lblNewLabel_1);
 		
 		JButton btnNewButton = new JButton("Se Connecter");
 		btnNewButton.setForeground(new Color(255, 0, 0));
 		btnNewButton.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent args0) {
 				
 				String username = txtUsername.getText().toString();
 				String password = passwordField.getText().toString();
 				
 				String sql = "SELECT username , password  FROM users ";
 				
 				try {
 					prepared = conn.prepareStatement(sql);
 					result =prepared.executeQuery();
 					
 					while(result.next()) {
 						String username1 = result.getString("username");
 						String password1 = result.getString("password");
 
 						if(!username1.equals(username) || !password1.equals(password)) {        
 			            		JOptionPane.showMessageDialog(null, "VOTRE USERNAME OU PASSWORD EST INCORRECTE !!!!");
 			            		
 			            		} else {
 				            		//JOptionPane.showMessageDialog(null, "welcome");
 			            			conn.close();
 			            			prepared.close();
				            		Accueil Dashboard = new Accueil();
 				            		Dashboard.setVisible(true);
 				            		frame.dispose();
 			            		}
 
 				
 					}
 					
 				}catch(SQLException eX) {
 					eX.printStackTrace();
 					
 				}
 				
 				
 			}
 			
 		});
 		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
 		btnNewButton.setBounds(610, 398, 244, 44);
 		frame.getContentPane().add(btnNewButton);
 		
 		JTextArea txtrScannAndShare = new JTextArea();
 		JLabel icone = new JLabel(new ImageIcon("image/img3.jpg"));
 		//txtrScannAndShare.setBackground(SystemColor.controlShadow);
 		icone.setBounds(0, 0, 1366, 750);
 		frame.getContentPane().add(icone);
 		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
 		//frame.setBounds(100, 100, 953, 711);
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	}
 }