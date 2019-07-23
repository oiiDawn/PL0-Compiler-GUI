import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableColumn;

public class LexUI extends JFrame {

	JFrame frame;
	JPanel background;

	JPanel upPanel;
	static JLabel inputLabel;
	static JTextField inputText;
	JButton inputButton;

	JPanel middlePanel;
	static JTextArea textArea;
	// JTable outputTable;

	JPanel bottomPanel;
	static JLabel outputLabel;
	static JTextField outputText;
	JButton outputButton;

	static JScrollPane scrollPane2;
	static JTable outputTable;

	String[] component = new String[3];

	public LexUI() {
		component[0] = "token";
		component[1] = "class";
		component[2] = "value";
		init();
	}

	public void init() {
		frame = new JFrame("Lexical Analysis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		background = new JPanel();
		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));

		upPanel = new JPanel();
		upPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		inputLabel = new JLabel("Input: ");
		inputText = new JTextField(20);
		inputButton = new JButton("Run");

		upPanel.add(inputLabel);
		upPanel.add(inputText);
		upPanel.add(inputButton);
		background.add(upPanel);

		inputButton.addActionListener(new InputListener());

		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		textArea = new JTextArea(25, 20);
		JScrollPane scrollPane1 = new JScrollPane(textArea);
		outputTable = new JTable(Token.table, component);
		TableColumn column = null;
		int columns = outputTable.getColumnCount();
		for (int i = 0; i < columns; i++) {
			column = outputTable.getColumnModel().getColumn(i);
			column.setPreferredWidth(100);
		}
		scrollPane2 = new JScrollPane(outputTable);

		middlePanel.add(textArea);
		middlePanel.add(scrollPane1);
		middlePanel.add(scrollPane2);
		background.add(middlePanel);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		outputLabel = new JLabel("Output: ");
		outputText = new JTextField(20);
		outputButton = new JButton("Output");

		bottomPanel.add(outputLabel);
		bottomPanel.add(outputText);
		bottomPanel.add(outputButton);
		background.add(bottomPanel);

		outputButton.addActionListener(new OutputListener());

		frame.getContentPane().add(BorderLayout.CENTER, background);
		frame.setSize(800, 700);
		frame.setVisible(true);
	}

}
