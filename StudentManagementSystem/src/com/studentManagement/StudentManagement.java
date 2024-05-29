package com.studentManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StudentManagement extends JFrame {

	private List<Student> students;
	private Connection connection;
	private ImageIcon[] buttonIcons;
	private JLabel titleLabel, addStudent, removeStudent, serachStudent, AllStudents;

	public StudentManagement() {
		super("Student Management System");

		students = new ArrayList<>();

		try {
			connection = DatabaseManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to connect to the database.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		loadButtonIcons();

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1100, 615);
		add(panel);

		titleLabel = new JLabel("Welcome to Student Management System");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Roboto", Font.BOLD, 30));
		titleLabel.setBounds(100, 30, 700, 60);
		panel.add(titleLabel);

		addStudent = new JLabel("Add Student");
		addStudent.setForeground(Color.WHITE);
		addStudent.setFont(new Font("Roboto", Font.BOLD, 15));
		addStudent.setBounds(130, 280, 150, 30);
		panel.add(addStudent);

		serachStudent = new JLabel("Search Student");
		serachStudent.setForeground(Color.WHITE);
		serachStudent.setFont(new Font("Roboto", Font.BOLD, 15));
		serachStudent.setBounds(320, 280, 150, 30);
		panel.add(serachStudent);

		removeStudent = new JLabel("Remove Student");
		removeStudent.setForeground(Color.WHITE);
		removeStudent.setFont(new Font("Roboto", Font.BOLD, 15));
		removeStudent.setBounds(115, 480, 150, 30);
		panel.add(removeStudent);

		AllStudents = new JLabel("All Students");
		AllStudents.setForeground(Color.WHITE);
		AllStudents.setFont(new Font("Roboto", Font.BOLD, 15));
		AllStudents.setBounds(335, 480, 150, 30);
		panel.add(AllStudents);

		// Load the background image
		ImageIcon backgroundImageIcon = new ImageIcon(StudentManagement.class.getResource("/icon/back2.jpg"));
		Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1100, 615, Image.SCALE_DEFAULT);
		ImageIcon backgroundIcon = new ImageIcon(backgroundImage);

		// Set the background image as a label
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, 1100, 615);
		panel.add(backgroundLabel);

		// Button creation with different images
		JButton addStudentButton = createButton("", 0, 120, 150, 120, 120);
		JButton removeStudentButton = createButton("", 1, 120, 350, 120, 120);
		JButton searchStudentButton = createButton("", 2, 320, 150, 120, 120);
		JButton displayAllStudentsButton = createButton("", 3, 320, 350, 120, 120);

		// Adding buttons to panel
		panel.add(addStudentButton);
		panel.add(removeStudentButton);
		panel.add(searchStudentButton);
		panel.add(displayAllStudentsButton);

		addStudentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check if the action event is coming from the addStudentButton
				if (e.getSource() == addStudentButton) {
					// Code to add a student
					String rollNumberStr = JOptionPane.showInputDialog(null, "Enter student roll number:");
					if (rollNumberStr != null && !rollNumberStr.isEmpty()) {
						int rollNumber = Integer.parseInt(rollNumberStr);
						String name = JOptionPane.showInputDialog(null, "Enter student name:");
						String grade = JOptionPane.showInputDialog(null, "Enter student grade:");
						Student student = new Student(name, rollNumber, grade);
						addStudent(student);
					} else {
						// Handle case where user canceled or closed the dialog without entering
						// anything
						JOptionPane.showMessageDialog(null, "No roll number entered. Please try again.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		removeStudentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Code to remove a student
				String rollNumberStr = JOptionPane.showInputDialog(null, "Enter student roll number to remove:");
				int rollNumber = Integer.parseInt(rollNumberStr);
				removeStudent(rollNumber);
			}
		});

		searchStudentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Code to search for a student
				String rollNumberStr = JOptionPane.showInputDialog(null, "Enter student roll number to search:");
				int rollNumber = Integer.parseInt(rollNumberStr);
				searchStudent(rollNumber);
			}
		});

		displayAllStudentsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Code to display all students
				displayAllStudents();
			}
		});

		JButton exitButton = new JButton("Exit");
		exitButton.setBackground(Color.RED);
		exitButton.setForeground(Color.white);
		exitButton.setFont(new Font("Roboto", Font.BOLD, 25));
		exitButton.setBounds(230, 550, 100, 30); // Adjust position and size
		panel.add(exitButton);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		ImageIcon i1 = new ImageIcon(StudentManagement.class.getResource("/icon/back2.jpg"));
		Image i2 = i1.getImage().getScaledInstance(1100, 615, Image.SCALE_DEFAULT); // Adjust width and height according
																					// // // to your frame
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		image.setBounds(0, 0, 1100, 650); // Set bounds according to your frame
		panel.add(image, BorderLayout.CENTER); // Add the image to the center of the BorderLayout

		setSize(1100, 650);
		setLocation(250, 100);
		setLayout(null);
		setVisible(true);

	}

	private void addStudent(Student student) {
		try {
			String sql = "INSERT INTO students (name, rollNumber, grade) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, student.getName());
			statement.setInt(2, student.getRollNumber());
			statement.setString(3, student.getGrade());
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				JOptionPane.showMessageDialog(null, "Student added successfully.");
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int id = generatedKeys.getInt(1);
					student.setId(id);
					new StudentDetailsFrame(student);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to add student.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void removeStudent(int rollNumber) {
		try {
			String sql = "DELETE FROM students WHERE rollNumber = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, rollNumber);
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				JOptionPane.showMessageDialog(null, "Student removed successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "No student found with the given roll number.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to remove student.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void searchStudent(int rollNumber) {
		try {
			String sql = "SELECT * FROM students WHERE rollNumber = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, rollNumber);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String name = resultSet.getString("name");
				String grade = resultSet.getString("grade");
				Student student = new Student(name, rollNumber, grade);
				new StudentDetailsFrame(student);
			} else {
				JOptionPane.showMessageDialog(null, "No student found with the given roll number.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to search for student.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void displayAllStudents() {
		try {
			String sql = "SELECT * FROM students";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Name");
			model.addColumn("Roll Number");
			model.addColumn("Grade");
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				int rollNumber = resultSet.getInt("rollNumber");
				String grade = resultSet.getString("grade");
				model.addRow(new Object[] { name, rollNumber, grade });
			}
			if (model.getRowCount() > 0) {
				JTable table = new JTable(model);

				// Set table style and colors
				table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
				table.getTableHeader().setForeground(Color.WHITE);
				table.getTableHeader().setBackground(Color.DARK_GRAY);
				table.setSelectionBackground(Color.LIGHT_GRAY);
				table.setSelectionForeground(Color.BLACK);
				table.setFont(new Font("Roboto", Font.PLAIN, 12));
				table.setForeground(Color.BLACK);
				table.setBackground(Color.WHITE);

				// Center-align data in cells
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				table.setDefaultRenderer(Object.class, centerRenderer);

				JOptionPane.showMessageDialog(null, new JScrollPane(table), "All Students",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "No students found.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to retrieve students.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Method to load button icons
	private void loadButtonIcons() {
		buttonIcons = new ImageIcon[4];

		for (int i = 0; i < buttonIcons.length; i++) {
			Image buttonImage = new ImageIcon(StudentManagement.class.getResource("/icon/button" + (i + 1) + ".jpg"))
					.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
			buttonIcons[i] = new ImageIcon(buttonImage);
		}
	}

	private JButton createButton(String text, int iconIndex, int x, int y, int width, int height) {
		JButton button = new JButton(text);

		button.setIcon(buttonIcons[iconIndex]);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.CENTER);
		button.setBounds(x, y, width, height);

		return button;
	}

	public static void main(String[] args) {

		new StudentManagement();

	}
}
