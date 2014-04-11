/* Author: Corey Crooks
 * Email: ccc35@pitt.edu
 * Date: 03/25/2014
 * Purpose: To provide GUI application to replace 
 *  		PrjRemote.exe 
 *  		Note this acts as the client. 
 **************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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
	   private String fullPath; 
	  JFileChooser fc;
	  final JLabel statusbar = 
              new JLabel("Waiting for file...");
	private JTextArea area; 
	private JTextArea rightSide; 
	
	private	JTextField serverIP = new JTextField(9); 
	private JTextField portText = new JTextField(5);
	private JButton submit;
	private JButton send; 
	private JButton save;
	private JLabel statusLabel;
	private JButton load;
	final JFileChooser  fileDialog = new JFileChooser();
	private JFrame frame; 
	 BufferedReader in;
	 BufferedReader stdIn;
	 PrintWriter out;
	 String filename; 
	 Socket socket;
	 String value; 
	public static void main(String[] args)
	{
		new Window(); 
		
	}
	
	public Window() 
	{	// this needs to change. 
		 String content = "";
		 String content2 = "";
		// remove if not used on mac 
    	try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } 
    	catch(Exception ex) { System.out.println("Error setting Java LAF: " + ex); }
    	// end remove. 
    	
		// frame create
		JPanel panel;
		setLayout(null); 
		area = new JTextArea(content); 
		rightSide = new JTextArea(content2);
		
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
		save = new JButton("Save");
		send = new JButton("Send");
		send.addActionListener(this);
		save.addActionListener(this);
		send.setEnabled(false);
		send.setBounds(5,490, 70,20);
		save.setBounds(80,490, 70,20);
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
		load.setBounds(360,120,70,20);
		statusbar.setBounds(102,96,260,70);
		
		area.setBounds(5, 180, 300, 300);
		rightSide.setBounds(320,180,300,300);
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
		panel.add(save);
		panel.add(ip);
		panel.add(serverIP);
		panel.add(port); 
		panel.add(portText);
		panel.add(submit); 
		panel.add(load);
		panel.add(loadXML);
		panel.add(statusbar);
		panel.add(area);
		panel.add(send);
		panel.add(rightSide);
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
		   
	    	
	    	String serverString = serverIP.getText();	 
			 String portString = portText.getText();	
			 
			 int number = Integer.parseInt(portString); 
			 System.out.println("Attempting to connect to "+serverString+" : "+portString);
		      
			
					      
					      
					      try {
					    		socket = new Socket(serverString, number);
								  out =
									        new PrintWriter(socket.getOutputStream(), true);
									     in =
									        new BufferedReader(
									            new InputStreamReader(socket.getInputStream()));
									   stdIn =
									        new BufferedReader(
									            new InputStreamReader(System.in));
								
								// this is where the service is connected. 
									      System.out.println("Connection Established");
									      load.setEnabled(true);
									      submit.setEnabled(false);
									      send.setEnabled(true);
									      
					    	} catch(IOException ex) {
					    	   ex.getMessage();
					    	} 
					     
		   
		   
	   }
	   else if(e.getSource() == save)
	   {// save the file. 
		  
		   /*
		   File file = new File(fullPath);
		   FileDialog fileDialog = new FileDialog(new Frame(), "Save", FileDialog.SAVE);
	        fileDialog.setFilenameFilter(new FilenameFilter() {
	            public boolean accept(File dir, String name) {
	                return name.endsWith(".txt");
	            }
	        });
	        fileDialog.setFile(file.toString());
	        fileDialog.setVisible(true);
	        System.out.println("File: " + fileDialog.getFile());
	    
	    */ 
	   }
	   
	   else if(e.getSource() == send) // sends data to the client 
	   {
		   //sending over valuet. -> no need for encoder....yet.
		   //System.out.println("In Send");
		   //send the file over. 
	
		 //  MsgEncoder mEncode = new MsgEncoder();
		 // KeyValueList valid = new KeyValueList();
		try {
			
		     out =
			        new PrintWriter(socket.getOutputStream(), true);
			 out.println(fullPath);
			 out.println(filename);
			 
		//	 System.out.println("After Sending the data to the client");
			 
			// out.flush(); 
			switch(filename){
			case "21.xml":
					//System.out.println("In 21");
					value = in.readLine();
				//	System.out.println("The Value RETURNED IS " + value);
				//	rightSide.setText("");
					rightSide.setText(value);
					
					break;
				
			case "703.xml":
			    value = in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
			//	System.out.println("The case value is " + value); 
			//	rightSide.setText("");
				rightSide.setText(value);
				break;
			case "704.xml":
			{
				
				// data in from 
			    value = in.readLine() + "\n";
			   
			//	System.out.println("The RETURNED VAlUE  " + value); 
				//rightSide.setText("");
		//		System.out.println("THE TEXT ES " + rightSide.getText());
				rightSide.setText(value);
				
			}
			
			case "701.xml":
			case "701a.xml":
			case "701b.xml":
			case "701c.xml":
				value = in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
			//	System.out.println("The case value is " + value); 
			//	rightSide.setText("");
				rightSide.setText(value);
				break;
				
				
				
			case "702.xml":
				String value = in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
				value = value + in.readLine() + "\n";
			//	System.out.println("The case value is " + value); 
			//	rightSide.setText("");
				rightSide.setText(value);
				break;
			}
			 
			 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		     

	   }
	   
	   // load the xml. 
	   else if(e.getSource() == load)
	   {
	
		   
		   
		 
		
		   //Handle open button action.
		    if (e.getSource() == load) {
		    	FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
		    	fd.setDirectory("C:\\");
		    	fd.setFile("*.xml");
		    	fd.setVisible(true);
		    	filename = fd.getFile();
		    	String absPath = fd.getDirectory();
		    	fullPath = absPath+filename; 
		    	statusbar.setText(fullPath);
		    	if (filename == null)
		    	  System.out.println("You cancelled the choice");
		    	else
		    	{
		    	
					    Parse p = new Parse(fullPath);
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
					    
					    // make the key and the xml list display on the text area. 
					   ArrayList keys = p.getKey();
					   ArrayList values = p.getValues();
					   ArrayList desc = p.getDesc();
				
					   String display = "";
					  
					   for(int j = 0; j<keys.size(); j++)
					   {
						   for(int k = 0; k<values.size(); k++)
						   {
							   display = "Key: " + keys.get(j) + "\n" + "Value : " + values.get(k);
							   display = display + "\n" + "Description: " + desc.get(0);
							   
						   }
					   }
					   
					   area.setText(display);
					   
		    		
		    		
		    		
		    		// used for valueting
		    	  //System.out.println("You chose " + filename);
		    	  //System.out.println("You chose " + absPath);
		        }
	
		    }
		    
	   }
	   
	   
	   
	   
	   
	  }


	


	
	
	
	
}
