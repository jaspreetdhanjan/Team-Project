package com.basementstudios.client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.json.simple.JSONObject;

import com.basementstudios.network.CharaSelect;
import com.basementstudios.network.Token;

public class Launcher {
	public static final String TITLE = "The Adventurers' Guild Launcher";

	private JFrame frame = new JFrame(TITLE);

	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	private JTextField usernameField = new JTextField("");
	private JPasswordField passwordField = new JPasswordField("");
	private JButton loginButton = new JButton("Login");
	private final JLabel lblLogin = new JLabel("Login");
	private final JLabel lblUsername = new JLabel("Username");
	private final JLabel lblPassword = new JLabel("Password");

	public Launcher() {
		lblUsername.setLabelFor(lblUsername);
		lblPassword.setLabelFor(passwordField);
		Dimension dimension = new Dimension(300, 200);
		frame.setMinimumSize(dimension);
		frame.setMaximumSize(dimension);
		frame.setPreferredSize(dimension);

		addContent();

		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private void addContent() {
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		addActionListeners(loginButton);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[] { 294, 0 };
		gbl_centerPanel.rowHeights = new int[] { 20, 20, 20, 20, 0 };
		gbl_centerPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_centerPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		centerPanel.setLayout(gbl_centerPanel);

		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.fill = GridBagConstraints.VERTICAL;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		centerPanel.add(lblUsername, gbc_lblUsername);

		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameField.gridx = 0;
		gbc_usernameField.gridy = 1;
		usernameField.setColumns(20);
		centerPanel.add(usernameField, gbc_usernameField);

		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 2;
		centerPanel.add(lblPassword, gbc_lblPassword);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 3;
		passwordField.setColumns(20);
		centerPanel.add(passwordField, gbc_passwordField);
		southPanel.add(loginButton);

		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 15));

		northPanel.add(lblLogin);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
	}

	private void addActionListeners(JButton login) {
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (usernameField.getText().isEmpty()) return;

				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				JSONObject loginData = LoginController.login(username, password);

				if ((boolean) loginData.get("success")) {
					new Token((String) loginData.get("token"));
					System.out.println((String) loginData.get("token"));
					new CharaSelect();
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Login Falied", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}