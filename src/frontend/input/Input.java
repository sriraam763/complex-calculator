/**
 * Contains the input panel with all the buttons and operands.
 */

package frontend.input;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import backend.Utils;
import ui.CustomButton;
import ui.Theme;

/**
 * Input panel with all the buttons.
 */
public class Input extends JPanel {
	/**
	 * Used to show the scientific buttons for the calculator when there is enough
	 * space.
	 */
	// private static boolean show_scientific = false;

	/**
	 * List of all the buttons.
	 */
	static final String buttons[] = {
			"C", "(", ")", "%",
			"7", "8", "9", "÷",
			"4", "5", "6", "x",
			"1", "2", "3", "-",
			"0", ".", "=", "+"
	};

	private JTextField textField;

	/**
	 * Array of all the buttons.
	 */
	private CustomButton button_arr[] = new CustomButton[buttons.length];

	public Input(JTextField textField) {
		this.textField = textField;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		setLayout(new GridBagLayout());

		createButtons();

		// Change the clear button action
		button_arr[0].removeActionListener(button_arr[0].getActionListeners()[0]);
		button_arr[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		});

		// Set the button colors
		button_arr[3].setBackground(Theme.PRIMARY_COLOR);
		button_arr[7].setBackground(Theme.PRIMARY_COLOR);
		button_arr[11].setBackground(Theme.PRIMARY_COLOR);
		button_arr[15].setBackground(Theme.PRIMARY_COLOR);
		button_arr[18].setBackground(Theme.PRIMARY_COLOR);
		button_arr[19].setBackground(Theme.PRIMARY_COLOR);

		button_arr[4].setBackground(Theme.SECONDARY_COLOR);
		button_arr[5].setBackground(Theme.SECONDARY_COLOR);
		button_arr[6].setBackground(Theme.SECONDARY_COLOR);
		button_arr[8].setBackground(Theme.SECONDARY_COLOR);
		button_arr[9].setBackground(Theme.SECONDARY_COLOR);
		button_arr[10].setBackground(Theme.SECONDARY_COLOR);
		button_arr[12].setBackground(Theme.SECONDARY_COLOR);
		button_arr[13].setBackground(Theme.SECONDARY_COLOR);
		button_arr[14].setBackground(Theme.SECONDARY_COLOR);
		button_arr[16].setBackground(Theme.SECONDARY_COLOR);
		button_arr[17].setBackground(Theme.SECONDARY_COLOR);

		for (int i = 0; i < buttons.length; i++) {
			gbc.gridx = i % 4;
			gbc.gridy = i / 4;

			add(button_arr[i], gbc);
		}

		setBackground(Theme.BG_COLOR);
	}

	/**
	 * Creates and initializes the basic buttons in the grid.
	 */
	private void createButtons() {
		for (int i = 0; i < buttons.length; i++) {
			button_arr[i] = new CustomButton(buttons[i]);
			// What to do when you click the button.
			button_arr[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String text = ((JButton) e.getSource()).getText();
					textField.setText(textField.getText() + text);
				}
			});
			button_arr[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (!Utils.valInArray((Character) e.getKeyChar(), Utils.ALLOWED_KEYS)) {
						return;
					}
					if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && textField.getText().length() > 0) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
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
