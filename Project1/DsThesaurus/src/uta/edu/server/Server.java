/*
 * Created by JFormDesigner on Tue Sep 19 21:51:17 CDT 2017
 */

package uta.edu.server;

import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Pooja Narayan 1001508253
 */
public class Server extends JFrame {

	// Socket variables
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	boolean serverconnected = true;
	int port = 5000;

	public Server() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Pooja Narayan
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		label1 = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(null);

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(textArea1);
				}
				contentPanel.add(scrollPane1);
				scrollPane1.setBounds(20, 40, 320, 140);

				//---- label1 ----
				label1.setText("Server");
				contentPanel.add(label1);
				label1.setBounds(30, 5, 75, label1.getPreferredSize().height);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < contentPanel.getComponentCount(); i++) {
						Rectangle bounds = contentPanel.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = contentPanel.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					contentPanel.setMinimumSize(preferredSize);
					contentPanel.setPreferredSize(preferredSize);
				}
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Pooja Narayan
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JLabel label1;
	private JPanel buttonBar;
	private JButton okButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables
	
	public static void main(String args[]) {
		// calling the class server
		Server server = new Server();
        server.setVisible(true);
        server.StartConn();
		
	}
// Connection to client using same port as client
	public void StartConn() {
		int port = 5000;
		//test
		textArea1.setText("pooja");
		//message to be displayed on screen 
		textArea1.append("Connection Initiated" + "\nListening on port : " + port
				+ "\nWaiting for the Client to Connect.....\n");
		try {
			serverSocket = new ServerSocket(port);
			//after connection
			while (serverconnected) {
				clientSocket = serverSocket.accept();
				textArea1.append("**********************************\n");
				textArea1.append("Client Request Recieved.\n");
				//Creating Thread for Multiple Client Architecture 
				ClientMultiThread clientThread = new ClientMultiThread(clientSocket);
				clientThread.start();
			}
			serverSocket.close();
			// after connection is lost
			textArea1.append("Server Stopped..\n");
			System.out.println("Server Stopped");
		} catch (Exception exec) {
			exec.printStackTrace();
		}
	}
// MultiThreading
	//https://www.tutorialspoint.com/javaexamples/net_multisoc.htm
	class ClientMultiThread extends Thread {
		Socket threadCliSocket;
		boolean runthread = true;

		public ClientMultiThread() {
			super();
		}

		ClientMultiThread(Socket clientSocket) {
			threadCliSocket = clientSocket;

		}

		public void run() {


			//java.util.List<String> stringToRemove = new ArrayList<String>();
			String FileLine, serverResponse = "";
			BufferedReader in = null;
			PrintWriter out = null;			

			try {
				in = new BufferedReader(new InputStreamReader(threadCliSocket.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(threadCliSocket.getOutputStream()));

				String clientRequest = in.readLine();
				textArea1.append("Client Query >" + clientRequest + " \n");

				/* Non-Case Sensitive Approach For Comparison */
				//clientWords = new ArrayList<String>(Arrays.asList(clientRequest.toLowerCase().split(" ")));

				// Open the Thesaurus File for checking all the words
				FileInputStream fstream = new FileInputStream("dsthesaurus.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//http://javadevnotes.com/java-string-split-tutorial-and-examples
				// Read Thesaurus File Line By Line
				while ((FileLine = br.readLine()) != null) {
                 System.out.println(FileLine);
					FileLine = FileLine.toLowerCase();
					String [] arry =FileLine.split(":");
					
					// Code to check the condition if the word typed by client exists in server side file
					if (!clientRequest.toLowerCase().isEmpty() && arry[0].equalsIgnoreCase(clientRequest.toLowerCase())) {
						serverResponse = arry[1];
						System.out.println("");
						break;

					}
//					else {
//						serverResponse = "Words not found.";	
//					}
				}
				// Close the input stream
				br.close();
				if(serverResponse.isEmpty()) {
					serverResponse = "Word is not found.";
				}
				

				// Sending Response  to the Client
				textArea1.append("Server Response >" + serverResponse + " \n");
				textArea1.append("********************* \n");
				out.println(serverResponse);
				out.flush();
				// Clear variable  
				serverResponse = "";
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
					threadCliSocket.close();
				} catch (IOException ioexp) {
					ioexp.printStackTrace();
				}
			}
		}
	}

}
