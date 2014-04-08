import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Parse {
	private File f; 
	String filePath; 
	
	public Parse(File f, String filePath)
	{
		this.f = f; 
		this.filePath = filePath; 
		
	}
	// default 
	public Parse(){}
	
	public void parseFile() throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse (new File(filePath));

        // normalize text representation
        doc.getDocumentElement ().normalize ();
        System.out.println ("Root element of the doc is " + 
             doc.getDocumentElement().getNodeName());
        
        NodeList noItems= doc.getElementsByTagName("Item");
        int items = noItems.getLength();
        System.out.println("Total no of Items : " + items);

        for(int i = 0; i<noItems.getLength(); i++)
        {
        	Node firstPersonNode = noItems.item(i);
            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){

                Element firstPersonElement = (Element)firstPersonNode;

                //-------
                NodeList firstNameList = firstPersonElement.getElementsByTagName("Key");
                Element firstNameElement = (Element)firstNameList.item(0);
                NodeList textFNList = firstNameElement.getChildNodes();
                System.out.println("Key Name: " + 
                       ((Node)textFNList.item(0)).getNodeValue().trim());
                
                //-------
                NodeList lastNameList = firstPersonElement.getElementsByTagName("Value");
                Element lastNameElement = (Element)lastNameList.item(0);

                NodeList textLNList = lastNameElement.getChildNodes();
                System.out.println("Value : " + 
                       ((Node)textLNList.item(0)).getNodeValue().trim());
                
            }//end if 
        } // end for loop 

        
	} 
}
