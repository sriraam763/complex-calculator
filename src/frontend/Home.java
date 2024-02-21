/**
 * Entry point to the UI of the app.
 */

package frontend;

import javax.swing.*;

import frontend.input.*;
import frontend.output.*;
import ui.Theme;

import java.awt.*;
import java.awt.event.*;

/**
 * Main window layout.
 */
public class Home extends JPanel {
	private GridBagConstraints gbc = new GridBagConstraints();

	private void build() {
		removeAll();

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
		add(new BasicInput(), gbc);

		revalidate();
	}

	public Home() {
		// Using a GridBagLayout for better flexibility.
		setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;

		build();

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (e.getComponent().getWidth() >= 800) {
					BasicInput.showScientific = true;
				} else {
					BasicInput.showScientific = false;
				}
				build();
			}
		});

		setBackground(Theme.BG_COLOR);
	}
}
