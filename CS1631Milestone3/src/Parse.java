import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Parse {
   
	String filePath; 
	ArrayList valueList; 
	ArrayList keyList;
	ArrayList itemsList; 
	ArrayList desc; 
	public Parse (String filePath)
	{
		
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
        valueList = new ArrayList(); // contains list of xml values.
        keyList = new ArrayList(); // contains list of keys 
        itemsList = new ArrayList(); 
        desc = new ArrayList(); 
        
        NodeList descList = doc.getElementsByTagName("Description");
        Element element1 = (Element)descList.item(0);	
        NodeList fstNm = element1.getChildNodes();
        //System.out.print("Name : " + (fstNm.item(0)).getNodeValue());
        
       desc.add( (fstNm.item(0)).getNodeValue());
        

        
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
                
                keyList.add(((Node)textFNList.item(0)).getNodeValue().trim());
                //-------
                NodeList lastNameList = firstPersonElement.getElementsByTagName("Value");
                Element lastNameElement = (Element)lastNameList.item(0);

                NodeList textLNList = lastNameElement.getChildNodes();
                System.out.println("Value : " + 
                       ((Node)textLNList.item(0)).getNodeValue().trim());
                valueList.add(((Node)textLNList.item(0)).getNodeValue().trim());
                
            }//end if 
        } // end for loop 
        System.out.println(valueList.get(1));
        
	} 
	
	
	
	public ArrayList getValues()
	{
		return valueList;
	}
	
	public ArrayList getKey()
	{
		return keyList;
	}
	
	public ArrayList getDesc()
	{
		return desc; 
	}
}
