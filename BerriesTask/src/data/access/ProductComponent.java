package data.access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connection.ServiceHandler;

import android.content.ClipData.Item;
import android.util.Log;

public class ProductComponent {

	 private static final String TAG_ID = "id";
	    private static final String TAG_DESCRIPTION = "productDescription";
	    private static final String TAG_PRICE = "price";
	    private static final String TAG_WIDTH = "width";
	    private static final String TAG_HEIGHT = "height";
	    private static final String TAG_URL= "url";
	    private static final String TAG_IMAGE= "image";
	    
	
	public ArrayList<Product> getProducts(String Url){
		
      ArrayList<Product> items = new ArrayList<Product>();
      
	   ServiceHandler sh = new ServiceHandler();
	   List<NameValuePair> params = new ArrayList<NameValuePair>();
	  
	   String jsonResponseStr="";
	
		jsonResponseStr = sh.makeServiceCall(Url,ServiceHandler.GET);

	     
	    Log.d("Response: ", "> " + jsonResponseStr);
	     int tempID;
		 String tempDescription;
		 double tempPrice;
		ProductImage tempImage;
		 String tempImageUrl;
		 int tempWidth; 
		 int tempHeight;
		 JSONObject tempProductObj;
		 JSONObject tempImageObj; 
		
	    if (jsonResponseStr != null) {
	        try {
	        	JSONArray  products = new JSONArray(jsonResponseStr);
	            for (int i = 0; i < products.length(); i++) {
	            	tempProductObj = products.getJSONObject(i);
	                tempID=tempProductObj.getInt(TAG_ID);
	                tempDescription=tempProductObj.getString(TAG_DESCRIPTION);
	                tempPrice=tempProductObj.getDouble(TAG_PRICE);
	              
	                tempImageObj = tempProductObj.getJSONObject(TAG_IMAGE);
	                tempImageUrl=tempImageObj.getString(TAG_URL);
	                tempWidth=tempImageObj.getInt(TAG_WIDTH);
	                tempHeight= tempImageObj.getInt(TAG_HEIGHT);
	             
	                items.add(new Product(tempID, tempDescription, tempPrice, new ProductImage(tempImageUrl, tempWidth, tempHeight)));
	     
	     

	            }
	        } catch (JSONException e) {
	           // e.printStackTrace();
	        }
	    } else {
	    	
	        Log.e("ServiceHandler", "Couldn't get any data from the url");
	    }
	    
	  
	    
	    return items;
		
			
	}
	
}
