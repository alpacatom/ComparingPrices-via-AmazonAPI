import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShipmentCharge {

	private static final double KGperLB = 2.20;

	//Type of Shippings, normal, fast, very fast
	private static final int SHIPPING_TYPE = 3;

	//CSV MAX raw and column
	public static final int CSV_MAX_COLUM_SIZE = 10;
	public static final int CSV_MAX_LINE_SIZE = 14;

	//Address of ShipmentCharge-*.txt
	public static String[] ShipmentTXT={
			"ShipmentCharge-com.csv",
			"ShipmentCharge-ca.csv",
			"ShipmentCharge-uk.csv",
			"ShipmentCharge-de.csv",
			"ShipmentCharge-fr.csv",
			"ShipmentCharge-jp.csv"
		};
	public String Categories;
	public Double[] Shipping;
	public Double[] perKG;
	public Double[] extraFees;

	ShipmentCharge(String str){
		Categories = str;
		Shipping = new Double[3];
		perKG = new Double[3];
		extraFees = new Double[3];
		for(int i=0; i<SHIPPING_TYPE;i++){
			Shipping[i] = 0.0;
			perKG[i] = 0.0;
			extraFees[i] = 0.0;
		}
	}

	public void PrintAll(){
		System.out.println(Categories);
		for(int i=0;i<SHIPPING_TYPE;i++){
			System.out.println(Shipping[i]);
			System.out.println(perKG[i]);
			System.out.println(extraFees[i]);
		}
	}

	public static ShipmentCharge MakeTable(ShipmentCharge table, String[] str, int j){
    	table = new ShipmentCharge(str[0]);
    	for(int i=0;i<SHIPPING_TYPE;i++){
    		table.Shipping[i] = MyParser.Convert2Double(str[1+3*i]);
        	if(j < 2){
        		table.perKG[i] = MyParser.Convert2Double(str[2+3*i]) * KGperLB;
        	}else{
        		table.perKG[i] = MyParser.Convert2Double(str[2+3*i]);
        	}
    		table.extraFees[i] = MyParser.Convert2Double(str[3+3*i]);
    	}
    	return table;
	}
	public static Double[] LowestCalcSC(ShipmentCharge[][] table, String category){
		Double dtmp[] = new Double[6];
		boolean flag = false;
		String regex = category;
		Pattern p = Pattern.compile(regex);
		for(int i=0;i<ItemData.COUNTRY_NUM;i++){
			int j=0;
			while(table[i][j]!=null){
				Matcher m = p.matcher(table[i][j].Categories);
				if(m.find()){
					dtmp[i] = CheckPrice(table[i][j]);
					break;
				}else{
					flag=true;
				}
				j++;
			}
		}
		if(flag){
			String all = "All";
			Pattern p2 = Pattern.compile(all);
			for(int i=0;i<ItemData.COUNTRY_NUM;i++){
				int j=0;
				while(table[i][j]!=null){
					Matcher m = p2.matcher(table[i][j].Categories);
					if(m.find()&&dtmp[i]==null){
						dtmp[i] = CheckPrice(table[i][j]);
					}
					j++;
				}
			}
		}
		return dtmp;
	}
	private static Double CheckPrice(ShipmentCharge table){
		for(int i=0;i<SHIPPING_TYPE;i++){
			if((table.Shipping[i] != -1.0) && (table.perKG[i] != -1.0) && (table.extraFees[i] != -1.0)){
				return (table.Shipping[i] + table.perKG[i]*ItemData.KG + table.extraFees[i]);
			}
		}
		return null;
	}
}
