package frontend.output;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import backend.Utils;

/**
 * Shows the calulator output.
 */
public class Output extends JPanel {
	JTextField textField = new JTextField();
	JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);

	/**
	 * Array of allowed keys that can be input.
	 */
	private final static Character ALLOWED_KEYS[] = {
			'%', '+', '-', '/', 'x', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
	};

	public Output() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 38));

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Utils.valInArray((Character)e.getKeyChar(), ALLOWED_KEYS)) {
					e.consume();
				}
				super.keyTyped(e);
			}
		});

		// Add a scrollbar to the textfield for long results.
		BoundedRangeModel brm = textField.getHorizontalVisibility();
		scrollBar.setModel(brm);

		add(textField);
		add(scrollBar);
	}
}
