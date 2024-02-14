/**
 * Entry point to the UI of the app.
 */

package frontend;

import javax.swing.*;

import frontend.input.*;
import frontend.output.*;
import ui.Theme;

import java.awt.*;

/**
 * Main window layout.
 */
public class Home extends JPanel {
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
		Output output = new Output();
		add(output, gbc);

		// Add the input bar for all the buttons.
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1;
		add(new Input(output.getTextField()), gbc);

		setBackground(Theme.BG_COLOR);
	}
}
