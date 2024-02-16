package frontend.input;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import backend.Parser;
import backend.Utils;
import ui.ButtonTexts;
import ui.CustomButton;
import ui.Theme;

public class ScientificInput extends JPanel {
	/**
	 * List of all the buttons.
	 */
	static final private String buttons[] = {
			"(", ")", ButtonTexts.x_sqr, ButtonTexts.x_cube, ButtonTexts.pow,
			ButtonTexts.inverse, ButtonTexts.squareroot, ButtonTexts.cuberoot, ButtonTexts.yroot, ButtonTexts.factorial,
			ButtonTexts.ln, ButtonTexts.log, ButtonTexts.e_pow, ButtonTexts.ten_pow, ButtonTexts.e,
			ButtonTexts.pi, ButtonTexts.sin, ButtonTexts.cos, ButtonTexts.tan,
			ButtonTexts.rad, ButtonTexts.arcsin, ButtonTexts.arccos, ButtonTexts.arctan
	};

	private static final String text[] = {
		"(", ")", "^2", "^3", "^",
		"^-1", "2√", "3√", "√", "!",
		"ln(", "log(", "e^", "10^", "e",
		"π", "sin(", "cos(", "tan(",
		"rad", "asin(", "acos(", "atan(",
	};

	private JTextField textField = Parser.textField;

	/**
	 * Array of all the buttons.
	 */
	private CustomButton button_arr[] = new CustomButton[buttons.length];

	public ScientificInput() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		setLayout(new GridBagLayout());

		createButtons();

		// Set the rad-deg button
		button_arr[19].removeActionListener(button_arr[19].getActionListeners()[0]);
		button_arr[19].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Parser.radians = !Parser.radians;
				if (Parser.radians) {
					button_arr[19].setText(ButtonTexts.rad);
				} else {
					button_arr[19].setText(ButtonTexts.deg);
				}
			}
		});

		for (int i = 0; i < button_arr.length-8; i++) {
			gbc.gridx = i%5;
			gbc.gridy = i/5;
			add(button_arr[i], gbc);
		}

		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		add(button_arr[15], gbc);

		gbc.gridx += 2;
		gbc.gridwidth = 1;
		add(button_arr[16], gbc);
		gbc.gridx++;
		add(button_arr[17], gbc);
		gbc.gridx++;
		add(button_arr[18], gbc);

		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		add(button_arr[19], gbc);

		gbc.gridx += 2;
		gbc.gridwidth = 1;
		add(button_arr[20], gbc);
		gbc.gridx++;
		add(button_arr[21], gbc);
		gbc.gridx++;
		add(button_arr[22], gbc);

		setBackground(Theme.BG_COLOR);
	}

	private void createButtons() {
		for (int i = 0; i < buttons.length; i++) {
			button_arr[i] = new CustomButton(buttons[i]);
			final int index = i;
			button_arr[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Parser.clearOutput();
					textField.setText(textField.getText() + text[index]);
				}
			});
			button_arr[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (!Utils.valInArray((Character) e.getKeyChar(), Utils.ALLOWED_KEYS)) {
						e.consume();
						return;
					}

					Parser.clearOutput();

					if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && textField.getText().length() > 0) {
						String text = textField.getText();
						textField.setText(text.substring(0, text.length() - 1));
					} else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
						Parser.calculate();
					} else {
						switch (e.getKeyChar()) {
							case '*':
								e.setKeyChar('x');
								break;
							default:
								break;
						}
						textField.setText(textField.getText() + e.getKeyChar());
					}
				}
			});
		}
	}
}
