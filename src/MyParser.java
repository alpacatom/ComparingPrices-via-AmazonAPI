import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;;

public class MyParser{
	private static final String CURRENCIES_JSON_ELM = "quotes";

	private static Document Initialize(String xml){
		try{
			/* Parse XML */
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	/* utf-8 */
	    	Document doc = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
	    	return doc;
		}catch(Exception e){
	    	e.printStackTrace();
	    	return null;
		}
	}
	public static String[] parseCSV(String str){
		return str.split(",");
	}
	/* extract *,* or *.* or ** */
	public static Double Convert2Double(String str){
		String regex = "(\\-*\\d+\\.*\\,*\\d*)";
		String val = str;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(val);
		if (m.find()){
			 val = m.group();
		}
		return Double.parseDouble(val) ;
	}

	public static String getTagElmFromXML(String xml, String tag){
		try{
			Document doc = Initialize(xml);
	        Node PriceNode = doc.getElementsByTagName(tag).item(0);

	        if(PriceNode != null){
	        	String str = PriceNode.getTextContent();
	        	str = str.replaceAll(",", ".");
	    		return str;
	        }
	        else{
	        	System.out.println("Price is null");
	        }
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static Double[] getCurrencies(String json){
		try{
			Double FromUSD[] = new Double[ItemData.COUNTRY_NUM];
			JSONObject root = new JSONObject(json);
			FromUSD[0] = 1.00;
			FromUSD[1] = root.getJSONObject(CURRENCIES_JSON_ELM).getDouble("USDCAD");
			FromUSD[2] = root.getJSONObject(CURRENCIES_JSON_ELM).getDouble("USDGBP");
			FromUSD[3] = root.getJSONObject(CURRENCIES_JSON_ELM).getDouble("USDEUR");
			FromUSD[4] = root.getJSONObject(CURRENCIES_JSON_ELM).getDouble("USDEUR");
			FromUSD[5] = root.getJSONObject(CURRENCIES_JSON_ELM).getDouble("USDJPY");
			return FromUSD;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
