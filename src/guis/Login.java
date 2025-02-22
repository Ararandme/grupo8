package guis;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import eventos.LoginEvent;

public class Login extends JDialog implements ComponentEvents {

	private JButton btnLogin;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;

	private final LoginEvent loginEvent;
	private final HashMap<String,JComponent> components = new HashMap<>();

	public Login(LoginEvent loginEvent) {
		this.loginEvent = loginEvent;
		setComponentsSetting();
	}


	private void setComponentsSetting(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Login");
		setSize(400, 250);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setFocusable(true);
		setResizable(false);


		getContentPane().setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsuario.setBounds(50, 50, 100, 25);
		getContentPane().add(lblUsuario);

		JLabel lblContrasena = new JLabel("Contrasena:");
		lblContrasena.setFont(new Font("Arial", Font.BOLD, 14));
		lblContrasena.setBounds(50, 100, 100, 25);
		getContentPane().add(lblContrasena);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(180, 50, 150, 25);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);

		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(180, 100, 150, 25);
		getContentPane().add(txtContrasena);
		txtContrasena.setColumns(10);

		btnLogin = new JButton("Iniciar sesión");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
		btnLogin.setBounds(125, 160, 140, 35);
		btnLogin.setBackground(new Color(0, 102, 204));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFocusPainted(false);
		getContentPane().add(btnLogin);

		sendComponents();
		sendingEvents();
		setVisible(true);
	}

private void sendSetters(){
	loginEvent.setUsuario(txtUsuario.getText())
			  .setContrasena(new String(txtContrasena.getPassword()));
}

	@Override
	public void sendingEvents() {


		btnLogin.addActionListener((e)->{
			sendSetters();
			loginEvent.setButtonEvent(e);
		});
		txtUsuario.addKeyListener(new KeyListener() {


			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				sendSetters();
				loginEvent.getKeyIsPressed(e);

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		txtContrasena.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				sendSetters();
				loginEvent.getKeyIsPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
	}

	@Override
	public void sendComponents() {
		loginEvent.setLogingDialog(this);
	}



}
