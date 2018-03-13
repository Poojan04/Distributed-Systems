import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import javax.net.ssl.*;
import java.security.*;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.wsdl.Binding;
/*
 * Created by JFormDesigner on Wed Nov 29 22:08:46 CST 2017
 */
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.axis.AxisFault;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.ProductType;
import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.UnitType;
import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.WeatherParametersType;
import gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLBindingStub;
import javax.servlet.*;


/**
 * @author pooja narayan
 */
public class weathercli extends JFrame {
	public weathercli() {
		initComponents();
	}
	public void ActionListenerMethod(){
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getWeatherInfo(e);
				
			}
		});
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				weathercli wc= new weathercli();
				getWeatherInfo(e);
				wc.setVisible(true);
	
			}
		});
	}

public static void main(String args[]){

	weathercli wc= new weathercli();
	wc.setVisible(true);
	wc.ActionListenerMethod();
		
}

protected void getWeatherInfo(ActionEvent e) {
	
	//fetch lat n long
	
	String lat = textField1.getText();
	String longi = textField2.getText();
	//converting string values to big decimal and setting it to variables
	BigDecimal latitude = new BigDecimal(Double.parseDouble(lat));
	BigDecimal longitude = new BigDecimal(Double.parseDouble(longi));
	
	// setting the product type to "time-series" for getting all data between the  start and end time
	ProductType p =ProductType.fromValue("time-series");
	//setting unit to "e" for the data to be retrieved in US standard units
	UnitType unit = UnitType.fromValue("e");
	/*
	 * creating date objects and getting current time from the system and 
	 * creating calendar objects for start and end time.
	 * both the values are kept equal so that weather info for the instance
	 * can be fetched.
	 */
	
	Date d  = new Date(System.currentTimeMillis());
	Calendar starttime= Calendar.getInstance();
	starttime.setTime(d);
	Calendar endtime= Calendar.getInstance();
	starttime.setTime(d);
	
	//weather parameters set to true or false for them to be fetched
	WeatherParametersType wpt = new WeatherParametersType();
	
	String result;
	//endpoint
	String sb = "https://graphical.weather.gov:443/xml/SOAP_server/ndfdXMLserver.php";
	
//	String sb = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
//			"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
//		"xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
//		"xmlns:ndf=\"http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl\">" +
//		" <soapenv:Header/>" +
//			" <soapenv:Body>" +
//			"  <ndf:NDFDgen " +
//			"   soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
//		"   <latitude xsi:type=\"xsd:decimal\">35.4</latitude>" +
//			"   <longitude xsi:type=\"xsd:decimal\">-97.6</longitude>" +
//			"   <product xsi:type=\"dwml:productType\" " +
//			"    xmlns:dwml=\"http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd\">" +
//			"    glance</product>" +
//		"   <startTime xsi:type=\"xsd:dateTime\">2017-08-22T00:00:00</startTime>" +
//			"   <endTime xsi:type=\"xsd:dateTime\">2017-08-25T00:00:00</endTime>" +
//			"   <Unit xsi:type=\"dwml:unitType\" " +
//			"    xmlns:dwml=\"http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd\">" +
//			"    m</Unit>" +
//			"  </ndf:NDFDgen>" +
//			" </soapenv:Body>" +
//		"</soapenv:Envelope>";
//	
	

	
	URL url;
	NdfdXMLBindingStub stub;

		

		try {
			 url = new URL(sb);

			stub = new NdfdXMLBindingStub(url, null);
			
			result = stub.NDFDgen(latitude, longitude, p, starttime, endtime,unit, wpt);
			System.out.println(result);
			//	http://tutorials.jenkov.com/java-xml/dom.html
			//https://www.tutorialspoint.com/java/xml/javax_xml_parsers_documentbuilder_parse_string.htm
			DocumentBuilderFactory builderFactory =DocumentBuilderFactory.newInstance();
			try {
				
				DocumentBuilder docbuilder = builderFactory.newDocumentBuilder();
				//String builder object for managing the strings from XML and appending result from xml to string obj       
                StringBuilder strBuilder = new StringBuilder().append(result);
            	try {
            //		four weather variables from the result
					ByteArrayInputStream bytearrayinputstr = new ByteArrayInputStream(strBuilder.toString().getBytes("UTF-8"));
					Document doc = docbuilder.parse(bytearrayinputstr);
					String temperature = 	doc.getElementsByTagName("temperature").item(0).getTextContent();
					String CloudAmount = 	doc.getElementsByTagName("cloud-amount").item(0).getTextContent();
					String humidity = 	doc.getElementsByTagName("humidity").item(0).getTextContent();
					String WindSpeed = 	doc.getElementsByTagName("wind-speed").item(0).getTextContent();
                    String climate=doc.getElementsByTagName("climate-anomaly").item(0).getTextContent();
					//appending the information to the display screen
					textArea1.append("\n"+temperature+"Fahrenheit"+""
							+CloudAmount+"Precent"+""+humidity+"Percent"+""+WindSpeed+"Knots"+climate+"inches");				
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		} catch (AxisFault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}



}


	

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - pooja narayan
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		textField1 = new JTextField();
		textField2 = new JTextField();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Weather");
		contentPane.add(label1);
		label1.setBounds(150, 15, 95, label1.getPreferredSize().height);

		//---- label2 ----
		label2.setText("lati");
		contentPane.add(label2);
		label2.setBounds(new Rectangle(new Point(75, 45), label2.getPreferredSize()));

		//---- label3 ----
		label3.setText("longi");
		contentPane.add(label3);
		label3.setBounds(new Rectangle(new Point(70, 75), label3.getPreferredSize()));
		contentPane.add(textField1);
		textField1.setBounds(160, 50, 100, textField1.getPreferredSize().height);
		contentPane.add(textField2);
		textField2.setBounds(160, 80, 100, textField2.getPreferredSize().height);

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(textArea1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(45, 150, 270, 105);

		//---- button1 ----
		button1.setText("Get detail");
		contentPane.add(button1);
		button1.setBounds(new Rectangle(new Point(50, 115), button1.getPreferredSize()));

		//---- button2 ----
		button2.setText("Clear Details");
		contentPane.add(button2);
		button2.setBounds(185, 115, 105, button2.getPreferredSize().height);

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - pooja narayan
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JTextField textField1;
	private JTextField textField2;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JButton button1;
	private JButton button2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
