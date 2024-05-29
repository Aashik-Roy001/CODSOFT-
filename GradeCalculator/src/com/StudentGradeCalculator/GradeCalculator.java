package com.StudentGradeCalculator;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GradeCalculator extends JFrame {
	private JTextField[] subjectFields;
	private JButton calculateButton;
	private JLabel totalMarksLabel, averagePercentageLabel, gradeLabel;

	public GradeCalculator() {
		super("Grade Calculator");

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 840, 540);
		panel.setLayout(null);
		add(panel);

		String[] subjectNames = { "English", "Hindi", "Math", "Science", "Social Study" };
		JLabel[] labels = new JLabel[5];
		subjectFields = new JTextField[5];
		for (int i = 0; i < 5; i++) {
			labels[i] = new JLabel(subjectNames[i] + ": ");
			labels[i].setBounds(150, 100 + 40 * i, 200, 30);
			panel.add(labels[i]);

			subjectFields[i] = new JTextField();
			subjectFields[i].setBounds(250, 100 + 40 * i, 200, 30);
			subjectFields[i].setForeground(Color.BLACK);
			subjectFields[i].setOpaque(false);
			final int index = i;
			subjectFields[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (index < subjectFields.length - 1) {
						subjectFields[index + 1].requestFocus();
					}
				}
			});
			panel.add(subjectFields[i]);
		}

		calculateButton = new JButton("Calculate");
		calculateButton.setBounds(150, 310, 100, 30);
		calculateButton.setBackground(new Color(4, 106, 21));
		calculateButton.setForeground(Color.WHITE);
		calculateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				calculateGrade();
			}
		});
		panel.add(calculateButton);
		getRootPane().setDefaultButton(calculateButton);

		totalMarksLabel = new JLabel("Total Marks: ");
		totalMarksLabel.setBounds(530, 210, 200, 30);
		panel.add(totalMarksLabel);

		averagePercentageLabel = new JLabel("Average Percentage: ");
		averagePercentageLabel.setBounds(530, 230, 200, 30);
		panel.add(averagePercentageLabel);

		gradeLabel = new JLabel("Grade: ");
		gradeLabel.setBounds(530, 250, 170, 30);
		panel.add(gradeLabel);

		Image imageIcon = new ImageIcon(GradeCalculator.class.getResource("/icon/back5.jpg")).getImage();
		ImageIcon i1 = new ImageIcon(imageIcon.getScaledInstance(850, 500, Image.SCALE_DEFAULT));
		JLabel image = new JLabel(i1);
		image.setBounds(0, 0, 850, 480);
		panel.add(image);

		setSize(840, 480);
		setLocation(350, 200);
		setLayout(null);
		setVisible(true);
	}

	private void calculateGrade() {
		int totalMarks = 0;
		int numberOfSubjects = subjectFields.length;
		for (int i = 0; i < numberOfSubjects; i++) {
			try {
				int marks = Integer.parseInt(subjectFields[i].getText());
				if (marks < 0 || marks > 100) {
					throw new NumberFormatException();
				}
				totalMarks += marks;
			} catch (NumberFormatException e) {
				totalMarksLabel.setText("Total Marks:  null");
				averagePercentageLabel.setText("Average Percentage:  null");
				gradeLabel.setText("Grade:  null");
				return;
			}
			subjectFields[i].setText("");
		}

		double averagePercentage = (double) totalMarks / numberOfSubjects;
		String grade = calculateGradeFromPercentage(averagePercentage);

		totalMarksLabel.setText("Total Marks: " + totalMarks);
		averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
		gradeLabel.setText("Grade: " + grade);
	}

	private String calculateGradeFromPercentage(double percentage) {
		if (percentage >= 90) {
			return "A";
		} else if (percentage >= 80) {
			return "B";
		} else if (percentage >= 70) {
			return "C";
		} else if (percentage >= 60) {
			return "D";
		} else {
			return "F";
		}
	}

	public static void main(String[] args) {
		new GradeCalculator();
	}
}
