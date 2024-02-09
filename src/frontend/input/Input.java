package frontend.input;

import java.awt.*;

import javax.swing.*;

/**
 * Input panel with all the buttons.
 */
public class Input extends JPanel {
	/** 
	 * List of all the buttons. 
	 */
	static final String buttons[] = {
			"C", "(", ")", "mod",
			"7", "8", "9", "÷",
			"4", "5", "6", "x",
			"1", "2", "3", "-",
			"0", ".", "=", "+"
	};

	public Input() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		setLayout(new GridBagLayout());

		// Add all the buttons to the grid.
		int i = 0;
		for (; i < buttons.length; i++) {
			gbc.gridx = i % 4;
			gbc.gridy = i / 4;
			add(new JButton(buttons[i]), gbc);
		}
	}
}
