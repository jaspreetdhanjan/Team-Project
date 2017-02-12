package com.basementstudios.client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.basementstudios.tag.Game;

public class Launcher {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 320;
	public static final String TITLE = "The Adventurers' Guild Launcher";

	private JFrame frame = new JFrame(TITLE);

	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	private JTextField usernameField = new JTextField("Username");
	private JPasswordField passwordField = new JPasswordField("Password");
	private JButton loginButton = new JButton("Login");

	private Launcher(Dimension dimension) {
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
		frame.setLayout(new BorderLayout(0, 0));

		addActionListeners(loginButton);

		centerPanel.add(usernameField);
		centerPanel.add(passwordField);
		southPanel.add(loginButton);

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
	}

	private void addActionListeners(JButton login) {
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (usernameField.getText().isEmpty()) return;

				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());

				if (!LoginController.verifyDetails(username, password)) {
					new Game(null);
					frame.dispose();
				}
			}
		});
	}

	//public static void main(String[] args) {
	//	new Launcher(new Dimension(WIDTH, HEIGHT));
	//}
}