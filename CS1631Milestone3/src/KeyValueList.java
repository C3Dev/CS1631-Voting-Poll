import java.util.ArrayList;
import java.util.Vector;


public class KeyValueList {
	private Vector keys;
	private Vector values;
   
	/* Constructor */
	public KeyValueList()
	{
		keys = new Vector();
		values = new Vector();
	}
   
	/* Look up the value given key, used in getValue() */
   
	public int lookupKey(String strKey)
	{
		for(int i=0; i < keys.size(); i++)
		{
			String k = (String) keys.elementAt(i);
			if (strKey.equals(k)) 
				return i;
		} 
		return -1;
	}
   
	/* add new (key,value) pair to list */
   
	public boolean addPair(String strKey,String strValue)
	{
		return (keys.add(strKey) && values.add(strValue));
	}
   
	/* get the value given key */
   
	public String getValue(String strKey)
	{
		int index=lookupKey(strKey);
		if (index==-1) 
			return null;
		return (String) values.elementAt(index);
	} 

	public void setValue(int index, String val)
	{
		if(index >= 0 && index < size())
			values.set(index, val);
	}

	/* Show whole list */
	public String toString()
	{
		String result = new String();
		for(int i=0; i<keys.size(); i++)
		{
       		result+=(String) keys.elementAt(i)+":"+(String) values.elementAt(i)+"\n";
		} 
		return result;
	}
   
	public int size()
	{ 
		return keys.size(); 
	}
   
	/* get Key or Value by index */
	public String keyAt(int index){ return (String) keys.elementAt(index);}
	public String valueAt(int index){ return (String) values.elementAt(index);}

	public ArrayList<String> getValueLike(String key)
	{
		String temp;
		ArrayList<String> results = new ArrayList<String>();
		for(int i=0; i < keys.size(); i++)
		{
			temp = (String) keys.elementAt(i);
			if (temp.contains(key)) 
				results.add((String) values.elementAt(i));
		}
		if(results.size() == 0)
			return null;
		return results;
	}
}
