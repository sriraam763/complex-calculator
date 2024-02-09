package frontend;

import javax.swing.*;

import frontend.input.*;
import frontend.output.*;

import java.awt.*;

/**
 * Main window layout.
 */
public class Home extends JPanel{
	public Home() {
		// Using a GridBagLayout for better flexibility.
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;

		// Add the output bar for results.
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0.25;
		add(new Output(), gbc);

		// Add the input bar for all the buttons.
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1;
		add(new Input(), gbc);
	}
}
