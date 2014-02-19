/* Author: Corey Crooks & Ben Moncuso (Group 13)                                                        *
 * Date: 02/19/2014                                                                 *
 * Email: ccc35@pitt.edu  & blm83@pitt.edu                                                          *
 * Purpose: To act as a component for prjRemote.exe                                 *
 * Synopsis: Once SISServer.java is being executed on port 7999 with IP 127.0.0.1   *
 *           Open prjRemote & connect to the same port & ip.                        *
 *           Load the associated Testing.xml file and click send                    *
 *           Run the Test.java with the IP address (used for socket)                *
 ************************************************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.Socket;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Testing {
	

	public static void main(String []args) throws Exception{

		/*
			Checks for command line argument
		*/
		if (args.length < 1)
		{
        	System.out.println("Too few arguments.");
            System.out.println("Run it like this: java VotingComponent UniversalInterface_IP");
            System.exit(0);
        }

		System.out.println("Initializing...");
		Thread.sleep(2000);
		
		Socket universal = new Socket(args[0], 7999);

		MsgEncoder mEncoder = new MsgEncoder();
		final MsgDecoder mDecoder = new MsgDecoder(universal.getInputStream());
		KeyValueList L = new KeyValueList();
		
		System.out.println("Sending response to remote...");
		Thread.sleep(1000);
		
		L.addPair("MsgID", "23");
		L.addPair("Name", "Testing");
		mEncoder.sendMsg(L, universal.getOutputStream());
		
		KeyValueList Result = mDecoder.getMsg();
		int msgID = Integer.parseInt(Result.getValue("MsgID"));
	//	System.out.println("The result is " + Result.toString() + "and the size is " + Result.size());
	//	System.out.println(msgID);

		/*
			Fetches for new messages sent from InputProcessor to the SISServer
		*/
		
		
		System.out.println("Loading Window...");
		Thread.sleep(1000);
		

		  JFrame frame = new JFrame("Group 13 Milestone 1");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	 
	       JLabel textLabel = new JLabel("The message ID is " + msgID ,SwingConstants.CENTER);
	       JLabel textLabel2 = new JLabel("Please check the receiving end of the virtual remote", SwingConstants.CENTER);
	       textLabel.setForeground(Color.white);
	       textLabel.setPreferredSize(new Dimension(300, 100)); 
	       textLabel2.setForeground(Color.white);
	       textLabel2.setPreferredSize(new Dimension(300, 100)); 
	       
	        frame.getContentPane().add(textLabel, BorderLayout.CENTER); 
	        frame.getContentPane().add(textLabel2, BorderLayout.AFTER_LAST_LINE); 
	       frame.getContentPane().setBackground(Color.blue);
	 
	       //Display the window. 
	       frame.setLocationRelativeTo(null); 
	       frame.pack();
	       frame.setVisible(true); 
	      
	       System.out.println("Task Completed");
	}	 
	
	
	
	

	
}