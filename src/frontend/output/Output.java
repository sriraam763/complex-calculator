/**
 * Used to show the output of the calculator.
 */

package frontend.output;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import backend.Utils;
import ui.Theme;

/**
 * Shows the calulator output.
 */
public class Output extends JPanel {
	JTextField textField = new JTextField();
	JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);

	public Output() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Theme.BG_COLOR);

		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 38));
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setForeground(Theme.FG_COLOR);
		textField.setBackground(Theme.BG_COLOR);
		textField.setCaretColor(Theme.FG_COLOR);

		// Used to filter keyboard input to allowed key characters
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Utils.valInArray((Character)e.getKeyChar(), Utils.ALLOWED_KEYS)) {
					e.consume();  // Ignore the key
				}
				switch (e.getKeyChar()) {
					case '*':
						e.setKeyChar('x');
						break;
					default:
						break;
				}
				super.keyTyped(e);
			}
		});

		// Add a scrollbar to the textfield for long results.
		BoundedRangeModel brm = textField.getHorizontalVisibility();
		scrollBar.setModel(brm);
		scrollBar.setBackground(Theme.BG_COLOR);

		add(textField);
		add(scrollBar);
	}

	public JTextField getTextField() {
		return this.textField;
	}
}
