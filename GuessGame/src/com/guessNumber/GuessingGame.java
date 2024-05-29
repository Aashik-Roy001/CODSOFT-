package com.guessNumber;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuessingGame extends JFrame {

	private final int minRange = 1;
	private final int maxRange = 100;
	private final int maxAttempts = 10;
	private int randomNumber;
	private int attempts;
	private JLabel feedbackLabel, titLabel, updateLabel;
	private JTextField guessInput;
	private JButton guessButton;

	public GuessingGame() {
		super("Guess Number");

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 840, 540);
		panel.setLayout(null);
		add(panel);

		titLabel = new JLabel("Welcome to Guessing Game");
		titLabel.setForeground(Color.WHITE);
		titLabel.setFont(new Font("Roboto", Font.BOLD, 30));
		titLabel.setBounds(30, 30, 450, 60);
		panel.add(titLabel);

		feedbackLabel = new JLabel("Guess a number between " + minRange + " and " + maxRange);
		feedbackLabel.setFont(new Font("Roboto", Font.BOLD, 15));
		feedbackLabel.setForeground(Color.white);
		feedbackLabel.setBounds(30, 100, 375, 30);
		panel.add(feedbackLabel);

		guessInput = new JTextField(10);
		guessInput.setBounds(30, 150, 220, 30);
		guessInput.setForeground(Color.WHITE);
		guessInput.setOpaque(false);
		guessInput.setFont(new Font("Roboto", Font.PLAIN, 15));
		Insets insets = new Insets(0, 5, 0, 20); // Adjust the values as needed
		guessInput.setMargin(insets);
		guessInput.setCaretColor(Color.WHITE);
		panel.add(guessInput);

		updateLabel = new JLabel("Attempts left: " + (maxAttempts - attempts));
		updateLabel.setFont(new Font("Roboto", Font.BOLD, 12));
		updateLabel.setForeground(Color.white);
		updateLabel.setBounds(30, 185, 375, 30);
		panel.add(updateLabel);

		guessButton = new JButton("Guess");
		guessButton.setBounds(30, 230, 100, 30);
		guessButton.setFont(new Font("Roboto", Font.BOLD, 14));
		guessButton.setForeground(Color.white);
		guessButton.setBackground(new Color(2, 13, 31));
		guessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkGuess();
			}
		});
		panel.add(guessButton);
		getRootPane().setDefaultButton(guessButton);

		ImageIcon i1 = new ImageIcon(GuessingGame.class.getResource("/icon/back4.jpg"));
		Image i2 = i1.getImage().getScaledInstance(850, 500, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		image.setBounds(0, 0, 850, 480);
		panel.add(image);

		setSize(840, 480);
		setUndecorated(true);
		setLocation(350, 200);
		setLayout(null);
		setVisible(true);

		startNewGame();
	}

	private void startNewGame() {
		Random rand = new Random();
		randomNumber = rand.nextInt(maxRange - minRange + 1) + minRange;
		attempts = 0;
		updateFeedbackLabel();
	}

	private void checkGuess() {
		try {
			int guess = Integer.parseInt(guessInput.getText());
			attempts++;

			if (guess < minRange || guess > maxRange) {
				JOptionPane.showMessageDialog(this, "Please enter a number between " + minRange + " and " + maxRange);
				return;
			}

			if (guess == randomNumber) {
				JOptionPane.showMessageDialog(this,
						"Congratulations! You guessed the number in " + attempts + " attempts.");
				int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					startNewGame();
				} else {
					System.exit(0);
				}
			} else if (guess < randomNumber) {
				feedbackLabel.setText("Too low. Try again.");
			} else {
				feedbackLabel.setText("Too high. Try again.");
			}

			updateLabel.setText("Attempts left: " + (maxAttempts - attempts));

			if (attempts >= maxAttempts) {
				JOptionPane.showMessageDialog(this,
						"Sorry, you've reached the maximum number of attempts. The correct number was " + randomNumber
								+ ".");
				int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					startNewGame();
				} else {
					System.exit(0);
				}
			}

			guessInput.setText("");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number.");
		}
	}

	private void updateFeedbackLabel() {
		feedbackLabel.setText("Guess a number between " + minRange + " and " + maxRange);

	}

	public static void main(String[] args) {
		new GuessingGame();
	}
}
