
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.Socket;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.Socket;
import java.util.Timer;

public class SISComponentBase implements ComponentBase
{
	private Socket client;
	JFrame frame = new JFrame("Group 13 Milestone 1");
	JLabel textLabel = new JLabel();
    
    
    SISComponentBase(Socket cl)
	{
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    	
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
		client = cl;
    }
    
    synchronized public void processMsg(KeyValueList kvList) throws Exception
	{
    	int MsgID = Integer.parseInt(kvList.getValue("MsgID"));
    	ArrayList<Socket> sendlist;
    	ArrayList<String> inmsgs, outmsgs;
    	CompMsgControl tempcmc;
    	String tempstr;

		switch(MsgID)
		{
		case 20://Add Comp Msg Control List, Unrecognized component cannot be connected
			tempstr = kvList.getValue("Name");
			if(!SISServer.CompExistCMC(tempstr))
			{
				tempcmc = new CompMsgControl();
				tempcmc.compname = tempstr;
				inmsgs = kvList.getValueLike("InputMsgID");
				outmsgs = kvList.getValueLike("OutputMsgID");
				if(inmsgs!=null)
					for(int i=0; i<inmsgs.size(); i++)
						tempcmc.InMsgs.add(Integer.parseInt(inmsgs.get(i)));
				if(outmsgs!=null)
					for(int i=0; i<outmsgs.size(); i++)
						tempcmc.OutMsgs.add(Integer.parseInt(outmsgs.get(i)));
				SISServer.cmc.add(tempcmc);
				SISServer.createhistory.add(kvList);
			}
			else
				System.out.println("Definition of component "+tempstr+" already exists.");
			break;
		case 21://Add Comp Msg Control List, Unrecognized component cannot be connected
			tempstr = kvList.getValue("Name");
			if(!SISServer.CompExistCMC(tempstr))
			{
				tempcmc = new CompMsgControl();
				tempcmc.compname = tempstr;
				inmsgs = kvList.getValueLike("InputMsgID");
				outmsgs = kvList.getValueLike("OutputMsgID");
				if(inmsgs!=null)
					for(int i=0; i<inmsgs.size(); i++)
						tempcmc.InMsgs.add(Integer.parseInt(inmsgs.get(i)));
				if(outmsgs!=null)
					for(int i=0; i<outmsgs.size(); i++)
						tempcmc.OutMsgs.add(Integer.parseInt(outmsgs.get(i)));
				SISServer.cmc.add(tempcmc);
				SISServer.createhistory.add(kvList);
			}
			else
				System.out.println("Definition of component "+tempstr+" already exists.");
			break;
		case 22:
			SISServer.TurnOff(kvList.getValue("Name"));
			break;
		case 24:
			System.out.println("Unexpected MsgID 24");
			break;
		case 25:
			System.out.println("Unexpected MsgID 25");
			break;
		case 26:
			System.out.println("Unexpected MsgID 26");
			break;
		default:
			PrintToWindow(kvList);
			SendToComponent(kvList, client);
			for(int i=0; i<SISServer.cmc.size(); i++)
			{
				
				if(SISServer.cmc.get(i).InMsgs.contains(MsgID))
				{
					//Turn on components if necessary
					SISServer.TurnOn(SISServer.cmc.get(i).compname);
					sendlist = SISServer.FindSocketCSM(SISServer.cmc.get(i).compname);
					if(sendlist != null)
						for(int j=0; j<sendlist.size(); j++)
							SendToComponent(kvList, sendlist.get(j));
				}
			}
			break;
		}
    }

	public void SendToComponent(KeyValueList kvList, Socket component)
	{
		try
		{
			MsgEncoder mEncoder = new MsgEncoder();
			if(component != null){

				mEncoder.sendMsg(kvList, component.getOutputStream());
			}
		}
		catch(Exception ex)
		{ System.out.println("Problem sending to component!");}
	}
	
	public void PrintToWindow(KeyValueList kvList)
	{
		String message = "<html>";
		for (int i=0; i<kvList.size(); i++){
			
			message = message+kvList.keyAt(i).toString()+" : "+kvList.valueAt(i).toString()+"<br>";
			
		}
		message = message+"</html>";
		textLabel.setText(message);
	    frame.pack();
	    frame.setVisible(true);
	}
}

