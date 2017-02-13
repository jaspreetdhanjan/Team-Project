package com.basementstudios.network;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class CharaList extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	public JList<CharacterData> list = new JList<CharacterData>();

	public CharaList(DefaultListModel<CharacterData> model) {
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblSelect = new JLabel("Select a Character");
			contentPanel.add(lblSelect, BorderLayout.NORTH);
			lblSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			scrollPane.setViewportView(list);
			list.setModel(model);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (list.isSelectionEmpty()) {
							JOptionPane.showMessageDialog(getContentPane(), "Please select a character", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							dispose();
						}
					}
				});
			}
		}
	}

	public CharacterData getSelectedChara() {
		return (CharacterData) list.getSelectedValue();
	}

	public Object showDialog() {
		setVisible(true);
		return getSelectedChara();
	}
}