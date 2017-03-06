package com.basementstudios.client;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import org.json.simple.JSONObject;

import com.basementstudios.network.CharacterData;
import com.basementstudios.network.CharacterLoader;
import com.basementstudios.network.InvalidTokenException;
import com.basementstudios.network.Token;
import com.basementstudios.tag.Game;

/**
 * A login authenticator for the game.
 * 
 * @author James Bray
 * @author Jaspreet Dhanjan
 */

public class LoginLauncher {
	public static final String TITLE = "The Adventurers' Guild Launcher";

	private JFrame frame = new JFrame(TITLE);

	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	private JTextField usernameField = new JTextField("");
	private JPasswordField passwordField = new JPasswordField("");
	private JButton loginButton = new JButton("Login");
	private JLabel lblLogin = new JLabel("Login");
	private JLabel lblUsername = new JLabel("Username");
	private JLabel lblPassword = new JLabel("Password");

	public LoginLauncher() {
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
		GridBagLayout center = new GridBagLayout();
		center.columnWidths = new int[] { 294, 0 };
		center.rowHeights = new int[] { 20, 20, 20, 20, 0 };
		center.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		center.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		centerPanel.setLayout(center);

		GridBagConstraints username = new GridBagConstraints();
		username.fill = GridBagConstraints.VERTICAL;
		username.insets = new Insets(0, 0, 5, 0);
		username.gridx = 0;
		username.gridy = 0;
		centerPanel.add(lblUsername, username);

		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameField.gridx = 0;
		gbc_usernameField.gridy = 1;
		usernameField.setColumns(20);
		centerPanel.add(usernameField, gbc_usernameField);

		GridBagConstraints password = new GridBagConstraints();
		password.insets = new Insets(0, 0, 5, 0);
		password.gridx = 0;
		password.gridy = 2;
		centerPanel.add(lblPassword, password);

		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 3;
		passwordField.setColumns(20);
		centerPanel.add(passwordField, gbc_passwordField);
		southPanel.add(loginButton);

		northPanel.add(lblLogin);

		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
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
					List<CharacterData> characterData = new CharacterLoader().getCharacters();
					if (characterData.size() >= 3) {
						new Game();
					} else {
						try {
							new Token().remove();
						} catch (InvalidTokenException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "You need at least 3 characters on your account in order to play", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Login Falied", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}