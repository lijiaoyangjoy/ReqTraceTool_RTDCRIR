package com.soulsight.argouml.ui;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Application;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ResultJDialog extends javax.swing.JDialog {
	private JScrollPane resultScrollPane;
	private JTextPane resultTextPane;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				ResultJDialog inst = new ResultJDialog(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public ResultJDialog(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				resultScrollPane = new JScrollPane();
				getContentPane().add(resultScrollPane, BorderLayout.CENTER);
				resultScrollPane.setPreferredSize(new java.awt.Dimension(403, 267));
				{
					resultTextPane = new JTextPane();
					resultScrollPane.setViewportView(resultTextPane);
					resultTextPane.setName("resultTextPane");
					//resultTextPane.setText("aaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaaa");
				}
			}
			this.setSize(549, 395);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setResult(String text)
	{
		resultTextPane.setText(text);
	}
}
