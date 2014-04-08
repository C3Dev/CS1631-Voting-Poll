/* Author: Corey Crooks
 * Email: ccc35@pitt.edu
 * Date: 03/25/2014
 * Purpose: To provide GUI application to replace 
 *  		PrjRemote.exe 
 *  		Note this acts as the client. 
 **************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;




public class Window extends JFrame implements ActionListener {

	// create serverIP

	  JFileChooser fc;
	  final JLabel statusbar = 
              new JLabel("File Path");
	private JTextArea area; 
	private	JTextField serverIP = new JTextField(9); 
	private JTextField portText = new JTextField(5);
	private JButton submit;
	   private JLabel statusLabel;
	private JButton load;
	final JFileChooser  fileDialog = new JFileChooser();
	private JFrame frame; 
	public static void main(String[] args)
	{
		new Window(); 
	}
	
	public Window() 
	{	// this needs to change. 
		 String content = "";
		
		// remove if not used on mac 
    	try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } 
    	catch(Exception ex) { System.out.println("Error setting Java LAF: " + ex); }
    	// end remove. 
    	
		// frame create
		JPanel panel;
		setLayout(null); 
		area = new JTextArea(content); 
		
		
		frame = new JFrame(); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Group 13 Milestone 3");
		frame.setSize(650,600);
		
		
		 panel = new JPanel(); 
		 panel.setLayout(null);
		
		// create labels. 
		JLabel ip = new JLabel("Server IP Address:");
		JLabel port = new JLabel("Port Number:");
		JLabel loadXML = new JLabel("Send Message:");
		
		statusbar.setSize(350,100);

		submit = new JButton("Connect");
		submit.addActionListener(this);
		 load = new JButton("Load");
		load.setEnabled(false);
		load.addActionListener(this);
		ip.setBounds(5,30, 150, 120);
		port.setBounds(220,30,150,120);
		loadXML.setBounds(5,70,150,120); 
		serverIP.setBounds(120,75,80,30);
		portText.setBounds(305,75,55,30);
		submit.setBounds(360,80,85,20); // windows = 76, 20
		load.setBounds(250,120,70,20);
		statusbar.setBounds(102,96,260,70);
		
		area.setBounds(10, 180, 200, 300);
		
		// JLabelText 
		serverIP.requestFocus();
		serverIP.setHorizontalAlignment(SwingConstants.RIGHT);
		serverIP.setPreferredSize(new Dimension(30,20));  
	
		serverIP.setHorizontalAlignment(SwingConstants.RIGHT);
		portText.setPreferredSize(new Dimension(30,20)); 
		
		
		// END JlabelText 
		
		
		serverIP.setText("127.0.0.1");
		portText.setText("7999");
		
		// add to the Panel
		
		panel.add(ip);
		panel.add(serverIP);
		panel.add(port); 
		panel.add(portText);
		panel.add(submit); 
		panel.add(load);
		panel.add(loadXML);
		panel.add(statusbar);
		panel.add(area);
		
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setBounds(1000, 1000, 1000, 1000);
		// add the panel to the to the window. 
		frame.add(panel);
		 //Create a file chooser
	    fc = new JFileChooser();
	
		// make sure the frame is visible 
		frame.setVisible(true);
	}
	
	
		
		
	public void actionPerformed(ActionEvent e) {

	    //Handle open button action.
	   if(e.getSource() == submit)
	   {
		   Socket socket;
	    	
	    	String serverString = serverIP.getText();	 
			 String portString = portText.getText();	
			 
			 int number = Integer.parseInt(portString); 
			 System.out.println("Attempting to connect to "+serverString+" : "+portString);
		      
			
					      
					      
					      try {
					    		socket = new Socket(serverString, number);
								 PrintWriter out =
									        new PrintWriter(socket.getOutputStream(), true);
									    BufferedReader in =
									        new BufferedReader(
									            new InputStreamReader(socket.getInputStream()));
									    BufferedReader stdIn =
									        new BufferedReader(
									            new InputStreamReader(System.in));
								
								// this is where the service is connected. 
									      System.out.println("Connection Established");
									      load.setEnabled(true);
									      
					    	} catch(IOException ex) {
					    	   ex.getMessage();
					    	} 
					      
					      
			 
		   
		   
	   }
	   
	   // load the xml. 
	   else if(e.getSource() == load)
	   {
		
		   //Handle open button action.
		    if (e.getSource() == load) {
		    	  JFileChooser chooser = new JFileChooser();
		          int option = chooser.showOpenDialog(Window.this);
		          if (option == JFileChooser.APPROVE_OPTION) {
		        	  File file = chooser.getSelectedFile();
		           
		                // What to do with the file, e.g. display it in a TextArea
		               String text =  file.getAbsolutePath();
		               System.out.println(text);
		             
		               Parse p = new Parse(file, text); 
		               
		               
		               
		               
		               try {
						p.parseFile();
					} catch (ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SAXException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
		               
		               
		               
		          }
		          
		          // perform operation
		          
		        }
		
		    
	   }
	   
	   
	   
	   
	   
	  }


	


	
	
	
	
}
