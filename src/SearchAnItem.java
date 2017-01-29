import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SearchAnItem {
	private static class AmazonConstants{
		private static final String AWS_ACCESS_KEY_ID[] = {
				// Set your Access keys here. You need 2 type of keys(for japan and for the others) at least.
				"AWS_ACCESS_KEY_ID_NOT_FOR_JAPAN_HERE",
				"AWS_ACCESS_KEY_ID_NOT_FOR_JAPAN_HERE",
				"AWS_ACCESS_KEY_ID_NOT_FOR_JAPAN_HERE",
				"AWS_ACCESS_KEY_ID_NOT_FOR_JAPAN_HERE",
				"AWS_ACCESS_KEY_ID_NOT_FOR_JAPAN_HERE",
				"AWS_ACCESS_KEY_ID_FOR_JAPAN_HERE"
		};
		private static final String AWS_SECRET_KEY[] = {
				// Set your Secret keys here. You need 2 type of keys(for japan and for the others) at least.
				"AWS_SECRET_KEY_NOT_FOR_JAPAN_HERE",
				"AWS_SECRET_KEY_NOT_FOR_JAPAN_HERE",
				"AWS_SECRET_KEY_NOT_FOR_JAPAN_HERE",
				"AWS_SECRET_KEY_NOT_FOR_JAPAN_HERE",
				"AWS_SECRET_KEY_NOT_FOR_JAPAN_HERE",
				"AWS_SECRET_KEY_FOR_JAPAN_HERE"
		};
		private static final String AWS_ASSOCIATETAG[] = {
				// Set your Associate tags here. You need 6 tags each of countries.
				"AWS_ASSOCIATETAG_USA_HERE",
				"AWS_ASSOCIATETAG_CANADA_HERE",
				"AWS_ASSOCIATETAG_UK_HERE",
				"AWS_ASSOCIATETAG_DE_HERE",
				"AWS_ASSOCIATETAG_FR_HERE",
				"AWS_ASSOCIATETAG_JPN_HERE"
		};
		private static final String ENDPOINT[] = {
				// Setting Endpoints.
				"ecs.amazonaws.com",
				"ecs.amazonaws.ca",
				"ecs.amazonaws.co.uk",
				"ecs.amazonaws.de",
				"ecs.amazonaws.fr",
				"ecs.amazonaws.jp"
		};
	}
	/*Searching Kernel*/
	public static String SearchByID(String ItemID, int country) {
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(AmazonConstants.ENDPOINT[country], AmazonConstants.AWS_ACCESS_KEY_ID[country], AmazonConstants.AWS_SECRET_KEY[country]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("AWSAccessKeyId", AmazonConstants.AWS_ACCESS_KEY_ID[country]);
        params.put("Version", "2013-08-01");
        params.put("AssociateTag", AmazonConstants.AWS_ASSOCIATETAG[country]);
        params.put("Operation", "ItemLookup");
        params.put("ItemId", ItemID);
        params.put("ResponseGroup", "Offers");
        params.put("IdType", "ASIN");

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        try{
            String requestUrl = helper.sign(params);

            //check
            //System.out.println(requestUrl);

            URL url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            String charSet = "UTF-8";
            String method = "GET";
            httpURLConnection.setRequestMethod( method );
            InputStreamReader inputStreamReader = new InputStreamReader( httpURLConnection.getInputStream(), charSet );
            bufferedReader = new BufferedReader( inputStreamReader );

            String oneLine = null;
            String responseXml = "";
            while( true ){
                oneLine = bufferedReader.readLine();
                if(oneLine == null){
                    break;
                }else{
                    responseXml += oneLine;
                }
            }

            return responseXml;
        }catch( Exception e ){
        	System.out.println("Null Exception : SearchByID");
            return null;
        }finally{

            if( bufferedReader != null ){
                try{
                    bufferedReader.close();
                }catch( IOException e ){
                }
            }
            if( httpURLConnection != null ){
                httpURLConnection.disconnect();
            }
        }
    }
	/*
	// I'm not sure that this method works... but it's needless at this time.
    public static String SearchByKeyword(String key, String index, int country) {
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(AmazonConstants.ENDPOINT[country], AmazonConstants.AWS_ACCESS_KEY_ID[country], AmazonConstants.AWS_SECRET_KEY[country]);
        } catch (Exception e) {
            return null;
        }
        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("AWSAccessKeyId", AmazonConstants.AWS_ACCESS_KEY_ID[country]);
        params.put("Version", "2013-08-01");
        params.put("AssociateTag", AmazonConstants.AWS_ASSOCIATETAG[country]);
        params.put("Operation", "ItemSearch");
        params.put("SearchIndex", index);
        params.put("Keywords", key);
        params.put("Sort", "salesrank");
        params.put("ContentType", "text/xml");


        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;


        try{
            String requestUrl = helper.sign(params);

            //check
            //System.out.println(requestUrl);

            URL url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            String charSet = "UTF-8";
            String method = "GET";
            httpURLConnection.setRequestMethod( method );
            InputStreamReader inputStreamReader = new InputStreamReader( httpURLConnection.getInputStream(), charSet );
            bufferedReader = new BufferedReader( inputStreamReader );

            String oneLine = null;
            String responseXml = "";
            while( true ){
                oneLine = bufferedReader.readLine();
                if(oneLine == null){
                    break;
                }else{
                    responseXml += oneLine;
                }
            }
            return responseXml;
        }catch( Exception e ){
        	System.out.println("Null Exception : SearchByKeyword");
            return null;
        }finally{

            if( bufferedReader != null ){
                try{
                    bufferedReader.close();
                }catch( IOException e ){
                }
            }
            if( httpURLConnection != null ){
                httpURLConnection.disconnect();
            }
        }
        }
        */
}
