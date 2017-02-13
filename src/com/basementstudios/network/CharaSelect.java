package com.basementstudios.network;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.basementstudios.tag.Game;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class CharaSelect {

	private JFrame frame;
	private boolean chara1, chara2, chara3 = false;
	private CharaViewControler charaViewControler;
	private DefaultListModel<CharacterData> modal;

	public CharaSelect() {
		charaViewControler = new CharaViewControler();
		modal = charaViewControler.getModal();
		frame = new JFrame();
		if(modal.getSize()>3)initialize(); 
		else JOptionPane.showMessageDialog(frame.getContentPane(), "You need at least 3 characters to play the game go online to add more", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ArrayList<CharacterData> selectedCharas = new ArrayList<CharacterData>();
		for (int i = 0; i < 3; i++) {
			selectedCharas.add(null);
		}
		
		frame.setResizable(false);
		frame.setBounds(100, 100, 655, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnPlay = new JButton("Play");
		panel.add(btnPlay);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.WEST);

		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.EAST);

		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.NORTH);

		JLabel lblSelectCharacter = new JLabel("Select Character 1");
		lblSelectCharacter.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_8.add(lblSelectCharacter);

		JPanel panel_9 = new JPanel();
		panel_5.add(panel_9, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Select");
		panel_9.add(btnNewButton);

		JPanel panel_14 = new JPanel();
		panel_5.add(panel_14, BorderLayout.CENTER);
		panel_14.setLayout(null);

		JLabel name1 = new JLabel("Name:");
		name1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		name1.setBounds(0, 0, 206, 19);
		panel_14.add(name1);

		JLabel health1 = new JLabel("Health:");
		health1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		health1.setBounds(0, 25, 206, 19);
		panel_14.add(health1);

		JLabel strength1 = new JLabel("Strength:");
		strength1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		strength1.setBounds(0, 50, 206, 19);
		panel_14.add(strength1);

		JLabel agility1 = new JLabel("Agility:");
		agility1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		agility1.setBounds(0, 75, 206, 19);
		panel_14.add(agility1);

		JLabel magic1 = new JLabel("Magic:");
		magic1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		magic1.setBounds(0, 100, 206, 19);
		panel_14.add(magic1);

		JLabel stamina1 = new JLabel("Stamina :");
		stamina1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		stamina1.setBounds(0, 126, 206, 19);
		panel_14.add(stamina1);

		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));

		JPanel panel_10 = new JPanel();
		panel_6.add(panel_10, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Select Character 2");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_10.add(lblNewLabel);

		JPanel panel_11 = new JPanel();
		panel_6.add(panel_11, BorderLayout.SOUTH);

		JButton btnSelect = new JButton("Select");
		panel_11.add(btnSelect);

		JPanel panel_15 = new JPanel();
		panel_15.setLayout(null);
		panel_6.add(panel_15, BorderLayout.CENTER);

		JLabel name2 = new JLabel("Name:");
		name2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		name2.setBounds(0, 0, 206, 19);
		panel_15.add(name2);

		JLabel health2 = new JLabel("Health:");
		health2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		health2.setBounds(0, 25, 206, 19);
		panel_15.add(health2);

		JLabel strength2 = new JLabel("Strength:");
		strength2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		strength2.setBounds(0, 50, 206, 19);
		panel_15.add(strength2);

		JLabel agility2 = new JLabel("Agility:");
		agility2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		agility2.setBounds(0, 75, 206, 19);
		panel_15.add(agility2);

		JLabel magic2 = new JLabel("Magic:");
		magic2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		magic2.setBounds(0, 100, 206, 19);
		panel_15.add(magic2);

		JLabel stamina2 = new JLabel("Stamina :");
		stamina2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		stamina2.setBounds(0, 126, 206, 19);
		panel_15.add(stamina2);

		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));

		JPanel panel_12 = new JPanel();
		panel_7.add(panel_12, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel("Select Character 3");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_12.add(lblNewLabel_1);

		JPanel panel_13 = new JPanel();
		panel_7.add(panel_13, BorderLayout.SOUTH);

		JButton btnSelect_1 = new JButton("Select");
		panel_13.add(btnSelect_1);

		JPanel panel_16 = new JPanel();
		panel_16.setLayout(null);
		panel_7.add(panel_16, BorderLayout.CENTER);

		JLabel name3 = new JLabel("Name:");
		name3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		name3.setBounds(0, 0, 206, 19);
		panel_16.add(name3);

		JLabel health3 = new JLabel("Health:");
		health3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		health3.setBounds(0, 25, 206, 19);
		panel_16.add(health3);

		JLabel strength3 = new JLabel("Strength:");
		strength3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		strength3.setBounds(0, 50, 206, 19);
		panel_16.add(strength3);

		JLabel agility3 = new JLabel("Agility:");
		agility3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		agility3.setBounds(0, 75, 206, 19);
		panel_16.add(agility3);

		JLabel magic3 = new JLabel("Magic:");
		magic3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		magic3.setBounds(0, 100, 206, 19);
		panel_16.add(magic3);

		JLabel stamina3 = new JLabel("Stamina :");
		stamina3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		stamina3.setBounds(0, 126, 206, 19);
		panel_16.add(stamina3);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chara1 = true;
				if (selectedCharas.get(0) != null) {
					modal.addElement(selectedCharas.get(0));
				}
				CharaList charaList = new CharaList(modal);
				CharacterData chara = (CharacterData) charaList.showDialog();
				chara.addStat();

				name1.setText("Name: " + chara.getName());
				health1.setText("Health: " + String.valueOf(chara.getCurrentHelth()));

				ArrayList<CharacterStat> stats = chara.getStats();

				strength1.setText("Strength: " + String.valueOf(stats.get(2).getValue()));
				agility1.setText("Agility: " + String.valueOf(stats.get(3).getValue()));
				stamina1.setText("Stamina: " + String.valueOf(stats.get(4).getValue()));
				magic1.setText("Magic: " + String.valueOf(stats.get(5).getValue()));

				modal.removeElement(chara);
				selectedCharas.set(0, chara);
			}
		});

		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chara2 = true;
				if (selectedCharas.get(1) != null) {
					modal.addElement(selectedCharas.get(1));
				}
				CharaList charaList = new CharaList(modal);
				CharacterData chara = (CharacterData) charaList.showDialog();
				chara.addStat();

				name2.setText("Name: " + chara.getName());
				health2.setText("Health: " + String.valueOf(chara.getCurrentHelth()));

				ArrayList<CharacterStat> stats = chara.getStats();

				strength2.setText("Strength: " + String.valueOf(stats.get(2).getValue()));
				agility2.setText("Agility: " + String.valueOf(stats.get(3).getValue()));
				stamina2.setText("Stamina: " + String.valueOf(stats.get(4).getValue()));
				magic2.setText("Magic: " + String.valueOf(stats.get(5).getValue()));

				modal.removeElement(chara);
				selectedCharas.set(1, chara);
			}
		});

		btnSelect_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chara3 = true;
				if (selectedCharas.get(2) != null) {
					modal.addElement(selectedCharas.get(2));
				}
				CharaList charaList = new CharaList(modal);
				CharacterData chara = (CharacterData) charaList.showDialog();
				chara.addStat();

				name3.setText("Name: " + chara.getName());
				health3.setText("Health: " + String.valueOf(chara.getCurrentHelth()));

				ArrayList<CharacterStat> stats = chara.getStats();

				strength3.setText("Strength: " + String.valueOf(stats.get(2).getValue()));
				agility3.setText("Agility: " + String.valueOf(stats.get(3).getValue()));
				stamina3.setText("Stamina: " + String.valueOf(stats.get(4).getValue()));
				magic3.setText("Magic: " + String.valueOf(stats.get(5).getValue()));

				modal.removeElement(chara);
				selectedCharas.set(2, chara);
			}
		});

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chara1&&chara2&&chara3){
					new Game(selectedCharas);
					frame.dispose();
				}else{
					JOptionPane.showMessageDialog(frame.getContentPane(), "Please select three characters", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		frame.setVisible(true);
	}
}
