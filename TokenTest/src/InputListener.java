import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JScrollPane;

public class InputListener implements ActionListener {
	
	static String fileName;
	static FileReader reader;
	static BufferedReader br;
	static byte[] inputStream;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		fileName = LexUI.inputText.getText();


		try {		
				reader = new FileReader(fileName);
				br = new BufferedReader(reader);
	            String line;
	            while ((line = br.readLine()) != null) {
	                LexUI.textArea.append(line + "\n");
	            }
				reader = new FileReader(fileName);
				br = new BufferedReader(reader);
				Token.init();
	            while(Token.getSym() != -1) { }
	            LexUI.outputTable.validate();
	            br.close();
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }


	}

}
