import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType; 
import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.Object;

public class ReplyComponent {

	private static String USER = "cs1631spr13@gmail.com";
    private static String PASSWORD = "biomed09";
    

	private static String HOST = "smtp.gmail.com";
    private static String PORT = "465";
   	private static String FROM = "cs1631spr13@gmail.com";
    
    private static String STARTTLS = "false";
    private static String AUTH = "false";
    private static String DEBUG = "false";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    
	public static void main(String args[]) throws Exception {

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
		L.addPair("Name", "ReplyComponent");
		mEncoder.sendMsg(L, universal.getOutputStream());



		while(true) 
		{
			KeyValueList list = mDecoder.getMsg();
			int msgID = Integer.parseInt(list.getValue("MsgID"));
			switch(msgID) 
			{
				case 711: 
				{
					String recip = list.getValue("VoterEmail");
					int msg = Integer.parseInt(list.getValue("Status"));
					replyVote( recip, msg);

				} break;

				case 712:
				{
					String recip = list.getValue("AdminEmail");
					String reply = list.getValue("RankedReport");
					replyTTable(recip, reply);
				} break;

				default: break;
			}

		}

	}
	public static void replyVote(String email, int response) 
	{
		Properties props = new Properties();
 
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.user", USER);
 
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);
        props.put("mail.smtp.debug", DEBUG);
 
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
 
        try {
 
            //Obtain the default mail session
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
 
            //Construct the mail message
            MimeMessage message = new MimeMessage(session);
            
            switch(response) 
            {
				case 1: 
				{
					message.setText("Duplicate Vote: Your old vote will be replaced and your new vote recorded.");
					message.setSubject("MobileVoting: Duplicate Vote");
				} break;
				case 2:
				{
					message.setText("Invalid Vote: Your vote was not recorded, invalid candidate.");
					message.setSubject("MobileVoting: Invalid Vote");	
				} break;

				case 3:
				{
					message.setText("Successful Vote: Thank you for voting!");
					message.setSubject("MobileVoting: Successful Vote");
				} break;
            }
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(RecipientType.TO, new InternetAddress(email));
            message.saveChanges();
 
            //Use Transport to deliver the message
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public static void replyTTable(String email, String response) 
	{
		Properties props = new Properties();
 
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.user", USER);
 
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);
        props.put("mail.smtp.debug", DEBUG);
 
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
 
        try {
 
            //Obtain the default mail session
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
 
            //Construct the mail message
            MimeMessage message = new MimeMessage(session);
            
        	response = response.replace(";", " ");
        	response = response.replace(",", ":");
        	message.setText(response);
			message.setSubject("Voting Report");

			message.setFrom(new InternetAddress(FROM));
            message.addRecipient(RecipientType.TO, new InternetAddress(email));
            message.saveChanges();
 
            //Use Transport to deliver the message
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}