import java.io.PrintStream;
import java.net.Socket;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class InputProcessor 
{

	private static String email = "group13voting@gmail.com";
	private static String password = "VotingPoll";
	
	public static void main(String[] args) throws Exception
	{
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
		L.addPair("Name", "InputProcessor");
		mEncoder.sendMsg(L, universal.getOutputStream());
		PrintStream printOut = new PrintStream(universal.getOutputStream());
		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		
		try
		{
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", email, password);
			System.out.println("Connected");
			Folder inbox = store.getFolder("Inbox");
			while (true)
			{
				
				inbox.open(Folder.READ_WRITE);
				Flags seen = new Flags(Flags.Flag.SEEN);
				FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
				Message message[] = inbox.search(unseenFlagTerm);
				inbox.setFlags(message, new Flags(Flags.Flag.SEEN), true);
				
				
				if (message.length > 0) System.out.println( message.length +  " message(s) recieved.");
				for (int i =0; i < message.length; i++) 
				{
					String text = "";
					String VoterId;
					
					try 
					{				
						Multipart multipart = (Multipart) message[i].getContent();
				
						VoterId = message[i].getFrom()[0].toString();
						
						for (int j =0; j<multipart.getCount(); j++)
						{
							BodyPart bodyPart = multipart.getBodyPart(j);
							text = text.concat((String)bodyPart.getContent());
						}
					}
					catch (Exception e)
					{
						VoterId = message[i].getFrom()[0].toString();
						text = (String)message[i].getContent();
						/*Multipart multipart = (Multipart) message[i].getContent();
						for (int j =0; j<multipart.getCount(); j++)
						{
							BodyPart bodyPart = multipart.getBodyPart(j);
							text = text.concat((String)bodyPart.getContent());
						}*/
					}
					
					String content[] = text.split("[ ]");
					
					if (content[0].equals("701"))
					{
						KeyValueList valid = new KeyValueList();
						valid.addPair("MsgID", "701");
						valid.addPair("Description", "Cast Vote");
						valid.addPair("VoterPhoneNo", VoterId + "" );
						valid.addPair("CandidateID", content[1] + "");
						//mEncoder.sendMsg(valid, universal.getOutputStream());
						printOut.println("MsgID$$$701$$$Description$$$Cast Vote$$$VoterPhoneNo$$$"+VoterId+"$$$CandidateID$$$"+content[1]+"$$$");
					}
					if (content[0].equals("702"))
					{
						KeyValueList valid = new KeyValueList();
						valid.addPair("MsgID", "702");
						valid.addPair("Description", "Request Report");
						valid.addPair("Passcode", content[2] + "" );
						valid.addPair("N", content[1] + "");
						valid.addPair("RequestId", VoterId+"");
						mEncoder.sendMsg(valid, universal.getOutputStream());
					}
					if(content[0].equals("703"))
					{
						KeyValueList valid = new KeyValueList();
						valid.addPair("MsgID", "703");
						valid.addPair("Passcode", content[2] + "");
						valid.addPair("CandidateList", content[1] + "");
						mEncoder.sendMsg(valid, universal.getOutputStream());
					}
				}
				Thread.sleep(5000);
				inbox.close(true);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
	
	}

}
