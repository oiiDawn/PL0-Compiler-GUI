import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class OutputListener implements ActionListener {

	static String fileName;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		fileName = LexUI.outputText.getText();
		
		try {
			File outFile = new File(fileName);
			outFile.createNewFile();
			try (FileWriter writer = new FileWriter(outFile);
				 BufferedWriter bw = new BufferedWriter(writer)){
				for(int i = 0;i < Token.tablePtr; i++) {
					bw.write("Token: " + Token.table[i][0] + "\t\t\tClass: " + Token.table[i][1] + "\t\t\tValue: " + Token.table[i][2] + "\r\n");
					bw.flush();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
