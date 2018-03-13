/*
 * Created by JFormDesigner on Tue Sep 19 21:57:48 CDT 2017
 */
/**
 * @author Pooja Narayan 1001508253
 */
package uta.edu.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.*;

import uta.edu.server.Server;

/**
 * @author Pooja Narayan 1001508253
 */
public class Client extends JFrame {
	
	//Create socket variables 
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in ,sin;
    String msg,smsg;
    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

	
	public Client() {
		initComponents();
	}
	//Connection
    private void RequestConn(){
        startconn();
        //sending request to server
        try {
			//Reading from text field
        	//https://stackoverflow.com/questions/5752307/how-to-retrieve-value-from-jtextfield-in-java-swing
			msg = textField1.getText();
			if (msg.isEmpty()) {
				textArea1.append("Error: Enter some text to send to server. \n");
				return;
			}
                        			
                        textField1.setText("");
                      //server side
                        out.println(msg);
                        textArea1.append("User requested the synonym of "+msg+"\n");
                        
                        //server response to client
                        smsg=sin.readLine();
                        textArea1.append("Server Response > " + smsg + "\n");
			textArea1.append("Terminating Connection \n");
			textArea1.append("*********************** \n");

                       }
catch(UnknownHostException unknownHost){
    			System.err.println("Unknown host!");

}
catch (IOException ioException) {
			ioException.printStackTrace();    
}
        		/* Closing connection - Closing Socket */
		try {
			out.close();
			sin.close();
			socket.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		
    }

    private void startconn() {
        //To change body of generated methods, choose Tools | Templates.
    	// Socket conn port defined
    	//http://www.geeksforgeeks.org/socket-programming-in-java/
      try {
           int port=5000;
			socket = new Socket("127.0.0.1", port);
		} 
      catch (IOException e) {
			e.printStackTrace();
		}
        textArea1.append("****************** \n");
		textArea1.append("Connected to Server\n");
		//  get Input / Output stream
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
    public static void main(String args[]) {
		//call the class Client - run multiple clients
    	Client cli = new Client();
        cli.setVisible(true);
		
	}

	private void okButtonMouseClicked(MouseEvent e) {
		// TODO add your code here
		// action performed when ok button clicked
        RequestConn();

	}
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Pooja Narayan
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		textField1 = new JTextField();
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		label2 = new JLabel();
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
				contentPanel.add(textField1);
				textField1.setBounds(145, 30, 160, textField1.getPreferredSize().height);

				//---- label1 ----
				label1.setText("Enter word");
				contentPanel.add(label1);
				label1.setBounds(60, 30, 85, label1.getPreferredSize().height);

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(textArea1);
				}
				contentPanel.add(scrollPane1);
				scrollPane1.setBounds(30, 65, 305, 130);

				//---- label2 ----
				label2.setText("Client");
				contentPanel.add(label2);
				label2.setBounds(10, 5, 75, label2.getPreferredSize().height);

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
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						okButtonMouseClicked(e);
					}
				});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Pooja Narayan
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JTextField textField1;
	private JLabel label1;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JLabel label2;
	private JPanel buttonBar;
	private JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

