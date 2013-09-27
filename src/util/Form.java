package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import util.FormatChecker.ErrorStatus;

public class Form {
	public static void main(String[] args) {
		//String s = Option.sharedOption().sourcePath;

		myFrame myframe = new myFrame();
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
	}
}

@SuppressWarnings("serial")
class myFrame extends JFrame implements ActionListener {

	JMenuBar myMenuBar;
	JMenu myMenu;
	JMenuItem myMenuItem;
	JFileChooser myFileChooser;

	public myFrame() {
		myMenu = new JMenu("File");
		myMenuItem = new JMenuItem("Select File");
		myMenuItem.addActionListener(this);

		myMenuBar = new JMenuBar();
		myMenuBar.add(myMenu);
		myMenu.add(myMenuItem);

		this.setJMenuBar(myMenuBar);

		this.setTitle("Format Check Tool");
		this.setSize(300, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == myMenuItem) {
			myFileChooser = new JFileChooser();
			myFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			myFileChooser.setAcceptAllFileFilterUsed(false);
			myFileChooser.addChoosableFileFilter(new MyFileFilter("txt","TXT"));

			int result = myFileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				String path = myFileChooser.getSelectedFile().getAbsolutePath();
				
				ErrorStatus es = FormatChecker.check(path);
				
				JOptionPane.showMessageDialog(null, es + ":" + path);
				
			} else {
				System.out.println("Canceled.");
			}
		}
	}

}

class MyFileFilter extends FileFilter {
	String ends;
	String description;

	public MyFileFilter(String ends, String description) {
		this.ends = ends;
		this.description = description;
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory())
			return true;

		String fileName = file.getName();
		if (fileName.toUpperCase().endsWith(this.ends.toUpperCase()))
			return true;

		return false;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public String getEnds() {
		return this.ends;
	}

}