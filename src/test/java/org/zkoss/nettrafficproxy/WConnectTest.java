package org.zkoss.nettrafficproxy;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


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
public class WConnectTest extends javax.swing.JFrame {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JButton startBtn;
	private JPanel mainPanel;
	private JButton restartBtn;
	private JButton stopBtn;
	
	private ProxyServer server;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WConnectTest inst = new WConnectTest();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public WConnectTest() {
		super();
		server = new ProxyServer(8001, "localhost", 8080);
		initGUI();
	}
	
	private void initGUI() {
		try {
			FlowLayout thisLayout = new FlowLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			{
				mainPanel = new JPanel();
				getContentPane().add(mainPanel);
				{
					startBtn = new JButton();
					mainPanel.add(startBtn);
					startBtn.setText("Start");
					startBtn.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent evt) {
							startBtnMousePressed(evt);
						}
					});
				}
			}
			{
				stopBtn = new JButton();
				getContentPane().add(stopBtn);
				stopBtn.setText("Stop");
				stopBtn.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						stopBtnMousePressed(evt);
					}
				});
			}
			{
				restartBtn = new JButton("Restart");
				getContentPane().add(restartBtn);
				restartBtn.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						onClick$restartBtn(evt);
					}
				});
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void startBtnMousePressed(MouseEvent evt) {
		server.start();
	}
	
	private void onClick$restartBtn(MouseEvent evt) {
		server.stop();
		server.start();
	}
	
	private void stopBtnMousePressed(MouseEvent evt) {
		server.stop();
	}

}
