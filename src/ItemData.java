import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemData {

	/* PARAMETERS */
	/* Get a Price of what you want via ASIN code */
	private static final String ItemID = "B018SBS856";

	/* You need this parameter to calculate shipment charges */
	public static final int KG = 1;
	public static final String category = "DVD";

	/* There are amazon.com, .ca, .co.uk, .de, .fr, .jp stores*/
	public static final int COUNTRY_NUM = 6; // NUM should be less than 7

	public static final String Countries[] = {
		"Amazon.com (USA)",
		"Amazon.ca (Canada)",
		"Amazon.co.uk (England)",
		"Amazon.de (German)",
		"Amazon.fr (France)",
		"Amazon.co.jp (Japan)"
	};
	private Double Price;
	private Double Shipment;
	private Double CurrenciesRate;
	private String CurrenciesCode;
	private String Country;

	ItemData(){
		Price = 0.0;
		CurrenciesRate = 0.0;
		Shipment = 0.0;
		CurrenciesCode = "";
		Country = "";
	}
	public static String[] ReadFile(String filename){
		String str[] = new String[14];
		String line = null;
		int itr = 0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	str[itr++] = line;
            }
            bufferedReader.close();
            return str;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file :" + filename);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	public void PrintAll(){
		System.out.println("---------------------");
		System.out.println("[" + this.Country + "]");
		System.out.println(this.CurrenciesCode + " " + this.Price);
		System.out.println("CurrenciesRate : " + this.CurrenciesRate);
		System.out.println("TOTAL : " + Calc(this.Price+this.Shipment));
		System.out.println("---------------------");
	}
	public void setPrice(Double val){
		/* need to remove a dot if it is JPY */
		String regex = "(JPY)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(this.CurrenciesCode);
		if(!m.find()){
			this.Price = val;
		}else{
			String tmp = Double.toString(val);
			tmp = tmp.replace(".", "");
			val = Double.parseDouble(tmp);
			this.Price = val;
		}
	}
	public void setCountry(String val){
		this.Country = val;
	}
	public void setShitpment(Double val){
		this.Shipment = val;
	}
	public void setCurrenciesRate(Double val){
		this.CurrenciesRate = val;
	}
	public void setCurrenciesCode(String val){
		this.CurrenciesCode = val;
	}
	public static String getItemID(){
		return ItemID;
	}
	public Double Calc(Double val){
		if(this.CurrenciesRate == 0.0){
			return -1.0;
		}else{
			return(val/this.CurrenciesRate);
		}
	}
}
