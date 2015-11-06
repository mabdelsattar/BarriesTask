package com.Activity.gridview;

import java.util.ArrayList;

import model.CustomGridAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

import com.Berries.gridview.R;

import connection.ConnectionValidator;
import data.access.Product;
import data.access.ProductComponent;

public class ActivityMain extends ActionBarActivity{

	 ListView listView1,listView2;
	 private ProgressDialog pDialog;
	 ArrayList<Product> products1;
	 ArrayList<Product> products2;

	 CustomGridAdapter adapter1;
	 CustomGridAdapter adapter2;
	 int myLastVisiblePos;// global variable of activity
	 ConnectionValidator connectionValidator;
	 
	 
	 public static int i;
	 
	 
public void intiActionbar(){
	
	android.support.v7.app.ActionBar actionbar =getSupportActionBar();
	actionbar.setTitle("Products");
     actionbar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
     actionbar.setIcon(R.drawable.shopping);
}

 void validatInternetConnection(){
	connectionValidator=new ConnectionValidator(this);
	if(!connectionValidator.isNetworkAvailable())
	{
	     displayAlert("Network Error", "Please Check Your Internet Connection and Try Again");
	}	
}

 void displayAlert(String title,String message) {
	new AlertDialog.Builder(this)
			.setMessage(
					message)//"Please Check Your Internet Connection and Try Again"
			.setTitle(title)//"Network Error"
			.setCancelable(false)
			.setNeutralButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							finish();
						}
					}).show();
}


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		intiActionbar();
		validatInternetConnection();
		//actionbar.setIcon(R.drawable.b)
				//
				i=2;
				
		listView1=(ListView)findViewById(R.id.listView1);
		listView2=(ListView)findViewById(R.id.listView2);
		
		
		
		
		

		products1=new ArrayList<Product>();
		products2=new ArrayList<Product>();

		//gridView.setColumnWidth(widthPixels/2);
		//gridView.setNumColumns(2);
		//listView1.setLayoutParams(new LayoutParams(widthPixels/2, heightPixels));
		//listView2.setLayoutParams(new LayoutParams(widthPixels/2, heightPixels));
		
		
		
		
		//gridView.setOnScrollListener(this);
	    new GetProducts1().execute("http://grapesnberries.getsandbox.com/products?count=5&from=0");
	    new GetProducts2().execute("http://grapesnberries.getsandbox.com/products?count=5&from=1");
	
	  //  gridView.setOnScrollListener(this);
  }
	
	

/**
 * Async task class to get json by making HTTP call
 * */
private class GetProducts1 extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        if(pDialog==null){
        pDialog = new ProgressDialog(ActivityMain.this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(true);
        }
        if(!pDialog.isShowing())
        pDialog.show();
     }


    @Override
    protected String doInBackground(String... arg0) {
        // Creating service handler class instance
    	
    	ProductComponent productComponent=new ProductComponent();
    ArrayList<Product> temp=productComponent.getProducts(arg0[0]);
    if(temp.size()!=0)
    {
    	products1.addAll(productComponent.getProducts(arg0[0]));
      adapter1 = new CustomGridAdapter(ActivityMain.this,products1);
    }
    	   
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (pDialog.isShowing())
            pDialog.dismiss();
        
    	
        //Get gridview object from xml file
  		

  		// Set custom adapter (GridAdapter) to gridview
  	if(adapter1 !=null)
  	{
    	listView1.setAdapter(adapter1);
    	listView1.setOnScrollListener(new OnScrollListener() {
	        private int mLastFirstVisibleItem;

	        @Override
	        public void onScrollStateChanged(AbsListView view, int scrollState) {

	        }

	        @Override
	        public void onScroll(AbsListView view, int firstVisibleItem,
	                int visibleItemCount, int totalItemCount) {
	            if(mLastFirstVisibleItem<firstVisibleItem)
	            {
	                Log.i("SCROLLING DOWN","TRUE");
	            }
	            if(mLastFirstVisibleItem>firstVisibleItem)
	            {
	                Log.i("SCROLLING UP","TRUE");
	            }
	            mLastFirstVisibleItem=firstVisibleItem;
	            boolean loadMore =  firstVisibleItem + visibleItemCount >= totalItemCount-1;

	            if(loadMore)
	            {
	                i++;
	            	  new GetProducts1().execute("http://grapesnberries.getsandbox.com/products?count=5&from="+i);
	            }
	         //   Toast.makeText(getApplicationContext(), firstVisibleItem+"", Toast.LENGTH_LONG).show();

	        }
	    });
        adapter1.notifyDataSetChanged();
  	}else{
     	Toast.makeText(getApplicationContext(), "No available data", Toast.LENGTH_LONG).show();	
  	}
  		//save first value when you create GridView
  	//	myLastVisiblePos = gridView.getFirstVisiblePosition();
    }

	    
    }



/**
 * Async task class to get json by making HTTP call
 * */
private class GetProducts2 extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        
        if (!pDialog.isShowing())      
             pDialog.show();
     }

    @Override
    protected String doInBackground(String... arg0) {
        // Creating service handler class instance
    	
    	ProductComponent productComponent=new ProductComponent();
    	
    	ArrayList<Product> temp=productComponent.getProducts(arg0[0]);
    	if(temp.size()!=0){
    	products2.addAll(productComponent.getProducts(arg0[0]));

      adapter2 = new CustomGridAdapter(ActivityMain.this,products2);
    	}
    	
    	    	    // 2. Get ListView from activity_main.xml
    	   
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (pDialog.isShowing())
            pDialog.dismiss();
        
    	
     
  	if(adapter2!=null) {
    	listView2.setAdapter(adapter2);
    	
    
		listView2.setOnScrollListener(new OnScrollListener() {
	        private int mLastFirstVisibleItem;

	        @Override
	        public void onScrollStateChanged(AbsListView view, int scrollState) {

	        }

	        @Override
	        public void onScroll(AbsListView view, int firstVisibleItem,
	                int visibleItemCount, int totalItemCount) {
	            if(mLastFirstVisibleItem<firstVisibleItem)
	            {
	                Log.i("SCROLLING DOWN","TRUE");
	            }
	            if(mLastFirstVisibleItem>firstVisibleItem)
	            {
	                Log.i("SCROLLING UP","TRUE");
	            }
	            mLastFirstVisibleItem=firstVisibleItem;
	            boolean loadMore =  firstVisibleItem + visibleItemCount >= totalItemCount-1;
	            if(loadMore)
	            {
	            	i++;
	            	  new GetProducts2().execute("http://grapesnberries.getsandbox.com/products?count=5&from="+i);
	            }

	            //   Toast.makeText(getApplicationContext(), firstVisibleItem+"", Toast.LENGTH_LONG).show();

	        }
	    });
        adapter2.notifyDataSetChanged();
  	   }
  	 else{
     	
     	Toast.makeText(getApplicationContext(), "No available data", Toast.LENGTH_LONG).show();
     }
    }
   
	    
    }







}

