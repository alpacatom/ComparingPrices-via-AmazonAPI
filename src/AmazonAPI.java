public class AmazonAPI {
	public static void main(String[] args) {
		/*
		// If you wanna look something (not working)
		final String SearchIndex = "Books";
		final String Keyword = "Python";
		String xml = SearchAnItem.SearchByKeyword(Keyword, SearchIndex);
		*/

		// Initializing
		ItemData arr[] = new ItemData[ItemData.COUNTRY_NUM];
		for(int i=0;i<ItemData.COUNTRY_NUM;i++){
			arr[i] = new ItemData();
		}
		String line[] = new String[ShipmentCharge.CSV_MAX_LINE_SIZE];
		ShipmentCharge SC[][] = new ShipmentCharge[ItemData.COUNTRY_NUM][ShipmentCharge.CSV_MAX_LINE_SIZE];

		// Get Shipment Charges
		for(int j=0;j<ItemData.COUNTRY_NUM;j++){
			line = ItemData.ReadFile(ShipmentCharge.ShipmentTXT[j]);
			String stmp[] = new String[ShipmentCharge.CSV_MAX_COLUM_SIZE];
			int k=0;
			while(line[k]!=null){
				stmp = MyParser.parseCSV(line[k]);
				SC[j][k] = ShipmentCharge.MakeTable(SC[j][k], stmp, j);
				k++;
			}
		}

		// Calculate the lowest Shipment Charges each countires.
		Double[] dtmp = new Double[6];
		dtmp = ShipmentCharge.LowestCalcSC(SC,ItemData.category);

		// Get Currencies via currencylayer
        String json = Currency.GetCurrencies();
        Double CurrenciesRate[] = new Double[ItemData.COUNTRY_NUM];
        CurrenciesRate = MyParser.getCurrencies(json);

		// Get Price and Country via Amazon Product API
		String xml[] = new String[ItemData.COUNTRY_NUM];
		final String tag[] = {"CurrencyCode","FormattedPrice"};

		for(int i=0;i<ItemData.COUNTRY_NUM;i++){
			arr[i].setCountry(ItemData.Countries[i]);
			xml[i] = SearchAnItem.SearchByID(ItemData.getItemID(), i);
			arr[i].setShitpment(dtmp[i]);
			arr[i].setCurrenciesCode(MyParser.getTagElmFromXML(xml[i],tag[0]));
			arr[i].setPrice( MyParser.Convert2Double(MyParser.getTagElmFromXML(xml[i],tag[1])));
        	arr[i].setCurrenciesRate(CurrenciesRate[i]);
        	arr[i].PrintAll();
		}

	}
}