package com.studentManagement;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StudentDetailsFrame extends JFrame {

	public StudentDetailsFrame(Student student) {
		super("Student Details");

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(new JLabel("Name: " + student.getName()));
		panel.add(new JLabel("Roll Number: " + student.getRollNumber()));
		panel.add(new JLabel("Grade: " + student.getGrade()));

		add(panel);

		setSize(300, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
