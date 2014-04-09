import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Object;

/*
	Description:
	The Voting Component is responsible for intializing the TallyTable, tracking and storing casted votes, and returning vote totals.
	Once the component is closed all stored votes are lost. 
*/
public class VotingComponent{ 

	private final static String passcode = "****";
		
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

		
		Socket universal = new Socket(args[0], 7999);

		MsgEncoder mEncoder = new MsgEncoder();
		final MsgDecoder mDecoder = new MsgDecoder(universal.getInputStream());
		KeyValueList L = new KeyValueList();
		L.addPair("MsgID", "23");
		L.addPair("Name", "VotingComponent");
		mEncoder.sendMsg(L, universal.getOutputStream());
		
		TallyTable tTable = null;
		
		/*
			Fetches for new messages sent from InputProcessor to the SISServer
		*/
		while(true)
		{
			KeyValueList list = mDecoder.getMsg();
			int msgID = Integer.parseInt(list.getValue("MsgID"));
			switch(msgID)
			{
			
				/*
					Casts Vote 
				*/	
				case 701: 
				{
					if (tTable != null) 
					{
						String phone = list.getValue("VoterPhoneNo");
						String candidate = list.getValue("CandidateID");
						
						int status = tTable.castVote(phone, candidate);
						
						KeyValueList valid = new KeyValueList();
						valid.addPair("MsgID", "711");
						valid.addPair("Description", "Acknowledge Vote (1 - duplicate, 2 - invalid, 3 - valid");
						valid.addPair("Status", status + "" );
						valid.addPair("VoterEmail", phone + "");
						mEncoder.sendMsg(valid, universal.getOutputStream());
						
					}
				}
				break;
				/*
					Returns TallyTable request
				*/
				case 702: 
				{
					if (list.getValue("Passcode").equals(passcode) && tTable != null)
					{		
						int winnersNum = Integer.parseInt(list.getValue("N"));
						String rankedReport = tTable.getWinner(winnersNum);
							
						KeyValueList valid = new KeyValueList();
						valid.addPair("MsgID", "712");
						valid.addPair("Description", "Acknowledge RequestReport");
						valid.addPair("RankedReport", rankedReport);
						valid.addPair("AdminEmail", list.getValue("RequestId"));
						mEncoder.sendMsg(valid, universal.getOutputStream());
					}
					
				}
				break;
				/*
					Initallizes TallyTable
				*/
				case 703: 
				{
					if (list.getValue("Passcode").equals(passcode)) 
					{
						String CandidateList = list.getValue("CandidateList");
						String CandidateIDs[] = CandidateList.split("[;]");
						List<String> CandidateID = Arrays.asList(CandidateIDs);
						
						tTable = new TallyTable(CandidateID);
						
						KeyValueList valid = new KeyValueList();
						valid.addPair("MsgID", "26");
						valid.addPair("Description", "Acknowledgement (Server acknowledges that GUI component is now connected to Server)");
						valid.addPair("AckMsgID", "703");
						valid.addPair("YesNo", "Yes");
						valid.addPair("Name", "TallyTable");
						mEncoder.sendMsg(valid, universal.getOutputStream());

					}
				}
				break;	
				default: break;
			}	
		}
	}
}

