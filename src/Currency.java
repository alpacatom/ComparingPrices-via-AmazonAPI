import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Currency{
    // essential URL structure is built using constants
    public static final String ACCESS_KEY = "ef0b83a8259f237265f5febbcdf4f75a";
    public static final String BASE_URL = "http://apilayer.net/api/";
    public static final String ENDPOINT = "live";
    public static final String CURRENCIES = "CAD,GBP,EUR,JPY";

	public static String GetCurrencies(){
		String requestUrl = BASE_URL + ENDPOINT  + "?access_key=" + ACCESS_KEY + "&currencies=" + CURRENCIES;
        try {
        	URL url = new URL(requestUrl);
            Object content = url.getContent();
            if (content instanceof InputStream) {
                BufferedReader bf = new BufferedReader(new InputStreamReader( (InputStream)content) );
                String line;
                while ((line = bf.readLine()) != null) {
                	return line;
                }
            }
            else {
            	return content.toString();
            }
        }catch(IOException e) {
            System.err.println(e);
            return null;
        }
		return null;
	}
}
