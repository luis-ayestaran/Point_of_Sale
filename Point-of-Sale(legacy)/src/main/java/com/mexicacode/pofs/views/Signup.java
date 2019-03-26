package com.mexicacode.pofs.views;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

import com.mexicacode.pofs.configuration.SystemMessages;
import com.mexicacode.pofs.services.AuthenticationService;
import com.mexicacode.pofs.exceptions.ToolkitException;
import com.mexicacode.pofs.entities.User;
import com.mexicacode.pofs.entities.UserGroup;
import com.mexicacode.pofs.utils.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Signup extends JFrame {
	private JTextField txtName;
	private JTextField txtFathSurname;
	private JTextField txtMothSurname;
	private JTextField txtUsername;
	
	private JPasswordField txtPassword;
	private JPasswordField txtRepPswd;
	
	private final JLabel lblAdvertencia;
	private final JLabel lblCrearNuevo;
	
	private JCheckBox chckbxAdmin;
	
	private final SpringLayout sl_background;
	
	public Signup() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(Signup.class.getResource("/com/mexicacode/pofs/images/LOGO.png")));
		try {
			this.setTitle(SystemMessages.getMessage("system.appname"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    //this.setSize(screenSize.width, screenSize.height - 45);
		this.setResizable(false);
		this.setBounds(30, 30, 1300, 700);
	    SpringLayout springLayout = new SpringLayout();
		this.getContentPane().setLayout(springLayout);
		
		JPanel background = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, background, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, background, 0, SpringLayout.SOUTH, getContentPane());
		background.setBorder(null);
		background.setBackground(new Color(255, 255, 255));
		springLayout.putConstraint(SpringLayout.WEST, background, 0, SpringLayout.WEST, this.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, background, 0, SpringLayout.EAST, this.getContentPane());
		this.getContentPane().add(background);
		sl_background = new SpringLayout();
		background.setLayout(sl_background);
		
		
		JLabel lblRegstrate = new JLabel("Reg\u00EDstrate");
		sl_background.putConstraint(SpringLayout.NORTH, lblRegstrate, 50, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, lblRegstrate, 588, SpringLayout.WEST, background);
		lblRegstrate.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 36));
		background.add(lblRegstrate);
		
		lblCrearNuevo = new JLabel("Crea un nuevo usuario para acceder al sistema");
		sl_background.putConstraint(SpringLayout.NORTH, lblCrearNuevo, 10, SpringLayout.SOUTH, lblRegstrate);
		sl_background.putConstraint(SpringLayout.WEST, lblCrearNuevo, 485, SpringLayout.WEST, background);
		lblCrearNuevo.setForeground(new Color(51, 51, 51));
		lblCrearNuevo.setFont(new Font("Avenir LT Std 35 Light", Font.PLAIN, 18));
		background.add(lblCrearNuevo);
		
		JSeparator sepName = new JSeparator();
		sepName.setForeground(new Color(60, 169, 113));
		sl_background.putConstraint(SpringLayout.NORTH, sepName, 120, SpringLayout.SOUTH, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.WEST, sepName, -200, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.EAST, sepName, -250, SpringLayout.EAST, lblCrearNuevo);
		background.add(sepName);
		
		txtName = new JTextField();
		txtName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		txtName.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 24));
		txtName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_background.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, sepName);
		sl_background.putConstraint(SpringLayout.EAST, txtName, 0, SpringLayout.EAST, sepName);
		sl_background.putConstraint(SpringLayout.SOUTH, txtName, -2, SpringLayout.NORTH, sepName);
		sl_background.putConstraint(SpringLayout.NORTH, txtName, -30, SpringLayout.NORTH, sepName);
		background.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Nombre");
		sl_background.putConstraint(SpringLayout.WEST, lblName, -200, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.SOUTH, lblName, -8, SpringLayout.NORTH, txtName);
		lblName.setForeground(new Color(60, 169, 113));
		lblName.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 15));
		background.add(lblName);
		
		JSeparator sepFathSurname = new JSeparator();
		sepFathSurname.setForeground(new Color(60, 169, 113));
		sl_background.putConstraint(SpringLayout.NORTH, sepFathSurname, 100, SpringLayout.SOUTH, sepName);
		sl_background.putConstraint(SpringLayout.WEST, sepFathSurname, -200, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.EAST, sepFathSurname, -250, SpringLayout.EAST, lblCrearNuevo);
		background.add(sepFathSurname);
		
		txtFathSurname = new JTextField();
		txtFathSurname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		txtFathSurname.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 24));
		txtFathSurname.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_background.putConstraint(SpringLayout.WEST, txtFathSurname, 0, SpringLayout.WEST, sepFathSurname);
		sl_background.putConstraint(SpringLayout.EAST, txtFathSurname, 0, SpringLayout.EAST, sepFathSurname);
		sl_background.putConstraint(SpringLayout.SOUTH, txtFathSurname, -2, SpringLayout.NORTH, sepFathSurname);
		sl_background.putConstraint(SpringLayout.NORTH, txtFathSurname, -30, SpringLayout.NORTH, sepFathSurname);
		background.add(txtFathSurname);
		
		JLabel lblFathSurname = new JLabel("Apellido paterno");
		sl_background.putConstraint(SpringLayout.WEST, lblFathSurname, -200, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.SOUTH, lblFathSurname, -8, SpringLayout.NORTH, txtFathSurname);
		lblFathSurname.setForeground(new Color(60, 169, 113));
		lblFathSurname.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 15));
		background.add(lblFathSurname);
		
		JSeparator sepMothSurname = new JSeparator();
		sepMothSurname.setForeground(new Color(60, 169, 113));
		sl_background.putConstraint(SpringLayout.NORTH, sepMothSurname, 100, SpringLayout.SOUTH, sepFathSurname);
		sl_background.putConstraint(SpringLayout.WEST, sepMothSurname, -200, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.EAST, sepMothSurname, -250, SpringLayout.EAST, lblCrearNuevo);
		background.add(sepMothSurname);
		
		txtMothSurname = new JTextField();
		txtMothSurname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		txtMothSurname.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 24));
		txtMothSurname.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_background.putConstraint(SpringLayout.WEST, txtMothSurname, 0, SpringLayout.WEST, sepMothSurname);
		sl_background.putConstraint(SpringLayout.EAST, txtMothSurname, 0, SpringLayout.EAST, sepMothSurname);
		sl_background.putConstraint(SpringLayout.SOUTH, txtMothSurname, -2, SpringLayout.NORTH, sepMothSurname);
		sl_background.putConstraint(SpringLayout.NORTH, txtMothSurname, -30, SpringLayout.NORTH, sepMothSurname);
		background.add(txtMothSurname);
		
		JLabel lblMothSurname = new JLabel("Apellido materno");
		lblMothSurname.setForeground(new Color(60, 169, 113));
		lblMothSurname.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 15));
		lblMothSurname.setToolTipText("");
		sl_background.putConstraint(SpringLayout.WEST, lblMothSurname, -200, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.SOUTH, lblMothSurname, -8, SpringLayout.NORTH, txtMothSurname);
		background.add(lblMothSurname);
		
		JSeparator sepUsername = new JSeparator();
		sepUsername.setForeground(new Color(60, 169, 113));
		sl_background.putConstraint(SpringLayout.NORTH, sepUsername, 0, SpringLayout.NORTH, sepName);
		sl_background.putConstraint(SpringLayout.WEST, sepUsername, 250, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.EAST, sepUsername, 200, SpringLayout.EAST, lblCrearNuevo);
		background.add(sepUsername);
		
		txtUsername = new JTextField();
		txtUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		txtUsername.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 24));
		txtUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_background.putConstraint(SpringLayout.WEST, txtUsername, 0, SpringLayout.WEST, sepUsername);
		sl_background.putConstraint(SpringLayout.EAST, txtUsername, 0, SpringLayout.EAST, sepUsername);
		sl_background.putConstraint(SpringLayout.SOUTH, txtUsername, -2, SpringLayout.NORTH, sepUsername);
		sl_background.putConstraint(SpringLayout.NORTH, txtUsername, -30, SpringLayout.NORTH, sepUsername);
		background.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Nombre de usuario ");
		lblUsername.setForeground(new Color(60, 169, 113));
		lblUsername.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 15));
		sl_background.putConstraint(SpringLayout.WEST, lblUsername, 250, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.SOUTH, lblUsername, 0, SpringLayout.SOUTH, lblName);
		background.add(lblUsername);
		
		JSeparator sepPassword = new JSeparator();
		sepPassword.setForeground(new Color(60, 169, 113));
		sl_background.putConstraint(SpringLayout.NORTH, sepPassword, 0, SpringLayout.NORTH, sepFathSurname);
		sl_background.putConstraint(SpringLayout.WEST, sepPassword, 0, SpringLayout.WEST, sepUsername);
		sl_background.putConstraint(SpringLayout.EAST, sepPassword, 0, SpringLayout.EAST, sepUsername);
		background.add(sepPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		txtPassword.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 24));
		txtPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_background.putConstraint(SpringLayout.WEST, txtPassword, 0, SpringLayout.WEST, sepPassword);
		sl_background.putConstraint(SpringLayout.EAST, txtPassword, 0, SpringLayout.EAST, sepPassword);
		sl_background.putConstraint(SpringLayout.SOUTH, txtPassword, -2, SpringLayout.NORTH, sepPassword);
		sl_background.putConstraint(SpringLayout.NORTH, txtPassword, -30, SpringLayout.NORTH, sepPassword);
		background.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblPassword = new JLabel("Contraseña");
		lblPassword.setForeground(new Color(60, 169, 113));
		lblPassword.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 15));
		sl_background.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, txtPassword);
		sl_background.putConstraint(SpringLayout.SOUTH, lblPassword, 0, SpringLayout.SOUTH, lblFathSurname);
		background.add(lblPassword);
		
		JSeparator sepRepPswd = new JSeparator();
		sepRepPswd.setForeground(new Color(60, 169, 113));
		sl_background.putConstraint(SpringLayout.NORTH, sepRepPswd, 0, SpringLayout.NORTH, sepMothSurname);
		sl_background.putConstraint(SpringLayout.WEST, sepRepPswd, 0, SpringLayout.WEST, sepUsername);
		sl_background.putConstraint(SpringLayout.EAST, sepRepPswd, 0, SpringLayout.EAST, sepUsername);
		background.add(sepRepPswd);
		
		txtRepPswd = new JPasswordField();
		txtRepPswd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		txtRepPswd.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 24));
		txtRepPswd.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_background.putConstraint(SpringLayout.WEST, txtRepPswd, 0, SpringLayout.WEST, sepRepPswd);
		sl_background.putConstraint(SpringLayout.EAST, txtRepPswd, 0, SpringLayout.EAST, sepRepPswd);
		sl_background.putConstraint(SpringLayout.SOUTH, txtRepPswd, -2, SpringLayout.NORTH, sepRepPswd);
		sl_background.putConstraint(SpringLayout.NORTH, txtRepPswd, -30, SpringLayout.NORTH, sepRepPswd);
		background.add(txtRepPswd);
		txtRepPswd.setColumns(10);
		
		JLabel lblRepPswd = new JLabel("Confirmar contraseña");
		lblRepPswd.setForeground(new Color(60, 169, 113));
		lblRepPswd.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 15));
		sl_background.putConstraint(SpringLayout.WEST, lblRepPswd, 0, SpringLayout.WEST, txtRepPswd);
		sl_background.putConstraint(SpringLayout.SOUTH, lblRepPswd, 0, SpringLayout.SOUTH, lblMothSurname);
		background.add(lblRepPswd);
		
		chckbxAdmin = new JCheckBox("Registrar como administrador");
		chckbxAdmin.setBackground(new Color(255, 255, 255));
		chckbxAdmin.setFont(new Font("Avenir LT Std 35 Light", Font.PLAIN, 13));
		sl_background.putConstraint(SpringLayout.NORTH, chckbxAdmin, 40, SpringLayout.SOUTH, sepMothSurname);
		sl_background.putConstraint(SpringLayout.EAST, chckbxAdmin, 0, SpringLayout.EAST, lblCrearNuevo);
		background.add(chckbxAdmin);
		
		lblAdvertencia = new JLabel("");
		lblAdvertencia.setForeground(new Color(255, 127, 80));
		lblAdvertencia.setFont(new Font("Avenir LT Std 65 Medium", Font.PLAIN, 14));
		sl_background.putConstraint(SpringLayout.WEST, lblAdvertencia, 80, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.NORTH, lblAdvertencia, 18, SpringLayout.SOUTH, chckbxAdmin);
		sl_background.putConstraint(SpringLayout.SOUTH, lblAdvertencia, 32, SpringLayout.SOUTH, chckbxAdmin);
		background.add(lblAdvertencia);
		
		final JPanel signupPanel = new JPanel();
		signupPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				signupPanel.setBackground(new Color(51, 204, 102));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				signupPanel.setBackground(new Color(60, 169, 113));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				registrar();
			}
		});
		signupPanel.setBorder(null);
		signupPanel.setBackground(new Color(60, 169, 113));
		sl_background.putConstraint(SpringLayout.NORTH, signupPanel, 50, SpringLayout.SOUTH, chckbxAdmin);
		sl_background.putConstraint(SpringLayout.WEST, signupPanel, 0, SpringLayout.WEST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.EAST, signupPanel, 0, SpringLayout.EAST, lblCrearNuevo);
		sl_background.putConstraint(SpringLayout.SOUTH, signupPanel, 90, SpringLayout.SOUTH, chckbxAdmin);
		background.add(signupPanel);
		SpringLayout sl_signupPanel = new SpringLayout();
		signupPanel.setLayout(sl_signupPanel);
		
		JLabel lblRegistrarUsuario = new JLabel("Registrar usuario");
		sl_signupPanel.putConstraint(SpringLayout.SOUTH, lblRegistrarUsuario, -8, SpringLayout.SOUTH, signupPanel);
		sl_signupPanel.putConstraint(SpringLayout.NORTH, lblRegistrarUsuario, 8, SpringLayout.NORTH, signupPanel);
		sl_signupPanel.putConstraint(SpringLayout.WEST, lblRegistrarUsuario, 86, SpringLayout.WEST, signupPanel);
		lblRegistrarUsuario.setForeground(new Color(255, 255, 255));
		lblRegistrarUsuario.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 24));
		signupPanel.add(lblRegistrarUsuario);
	}
	
	public void registrar() { 	//Método que registra un usuario
		sl_background.putConstraint(SpringLayout.WEST, lblAdvertencia, 80, SpringLayout.WEST, lblCrearNuevo); 	 //Cambia la medida de la etiqueta de advertencia para que el mensaje se muestre céntrico
		char[] password = txtPassword.getPassword();	//Obtiene las
		char[] repPswd = txtRepPswd.getPassword();		//contraseñas y
		String contrasena = new String(password);		//las convierte en
		String repCont = new String(repPswd);			//strings para poder compararlas
		if(txtName.getText().equals("") || txtFathSurname.getText().equals("") || txtMothSurname.getText().equals("") || txtUsername.getText().equals("") || contrasena.equals("") || repCont.equals(""))	//Si el usuario no ha llenado todos los campos,lanza una advertencia
		{
			sl_background.putConstraint(SpringLayout.WEST, lblAdvertencia, -10, SpringLayout.WEST, lblCrearNuevo);
			lblAdvertencia.setText("Asegúrate de haber llenado todos los campos correctamente");
		}
		else		//Si sí, comienza el proceso de registro
		{
			if(contrasena.equals(repCont))		//Si las contraseñas son iguales, permite registrar
			{
				try {
					lblAdvertencia.setText("");		//Limpia la etiqueta de advertencia
					UserGroup userGroup;
					if(chckbxAdmin.isSelected())	//Confirma si el nuevo registro será administrador o usuario
					{
						userGroup = new UserGroup("admin");
					}
					else
					{
						userGroup = new UserGroup("user");
					}
					User user = new User(txtName.getText(), txtFathSurname.getText(), txtMothSurname.getText(), txtUsername.getText(),Toolkit.strToMD5(new String(txtPassword.getPassword())), userGroup);
					AuthenticationService authServ = new AuthenticationService();
					User u = authServ.searchUser(user);		//Busca el usuario en la base de datos
					if(u != null)							//Si existe, no lo inserta
					{
						lblAdvertencia.setText("Este usuario ya fue registrado");
					}
					else		//Si no, continúa con el proceso de autenticación
					{
						if(user.getUserGroup().getGroup().equals("admin"))		//Si se registrará como administrador
						{
							if(authServ.adminExists())							//Comprueba si ya hay otros administradores. Si sí, 
							{
																				//Pide el usuario y contraseña de alguno de ellos
								String usrnm = JOptionPane.showInputDialog(null, "Proporcione un nombre de usuario de administrador \n", "Ejecutar como administrador", JOptionPane.QUESTION_MESSAGE);
								String pswd = JOptionPane.showInputDialog(null, "Proporcione la contraseña \n", "Ejecutar como administrador", JOptionPane.QUESTION_MESSAGE);
								User userParam = new User(usrnm, Toolkit.strToMD5(pswd));
								userParam = authServ.searchUser(userParam);		
								if(userParam == null)		//Si no encuentra un registro con esos datos, no permite el registro
								{
									JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos \n", "Error de autenticaciónr", JOptionPane.ERROR_MESSAGE);
								}
								else						//Si sí, verifica que sea administrador
								{
									if(userParam.getUserGroup().getGroup().equals("admin"))		//Si es administrador, ingresa el nuevo registro
									{
										authServ.registrateUser(user, userGroup);
										dispose();
									}
									else
										JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos \n", "Error de autenticaciónr", JOptionPane.ERROR_MESSAGE);
								}
							}
							else
							{
								authServ.registrateUser(user, userGroup);
								dispose();
							}
						}
						else		//Si se registrará como usuario, lo ingresa a la base de datos
						{
							authServ.registrateUser(user, userGroup);
							dispose();
						}
					}
				} catch (ToolkitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else		//Si no son iguales las contraseñas, lanza una advertencia
			{
				sl_background.putConstraint(SpringLayout.WEST, lblAdvertencia, 80, SpringLayout.WEST, lblCrearNuevo);
				lblAdvertencia.setText("Las contraseñas no coinciden");
			}
		}
	}
}
