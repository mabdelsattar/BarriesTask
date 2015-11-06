package model;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Berries.gridview.R;

import data.access.Product;


public class CustomGridAdapter extends BaseAdapter {
	
	private Context context; 
	private final ArrayList<Product> products;

	//Constructor to initialize values
	public CustomGridAdapter(Context context,ArrayList<Product> products) {
		
		this.context = context;
		this.products = products;
	}
	
	@Override
	public int getCount() {
		
		// Number of times getView method call depends upon gridValues.length
		return products.size();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}
	
	
    // Number of times getView method call depends upon gridValues.length
	
	public View getView(int position, View convertView, ViewGroup parent) {

		//LayoutInflator to call external grid_item.xml file
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {
			gridView = new View(context);
			// get layout from grid_item.xml
			gridView = inflater.inflate(R.layout.grid_item, null);
			// set value into textview
			Product productCell=products.get(position);
			

			TextView tvPrice = (TextView) gridView.findViewById(R.id.tvproductPrice);
			tvPrice.setText(productCell.Price+" $");
			
			
			TextView tvDescription = (TextView) gridView.findViewById(R.id.productDescription);
			tvDescription.setText(productCell.Description);
			
			// set image based on selected text
			ImageView ImgProduct = (ImageView) gridView
					.findViewById(R.id.productImage);

			// show The Image
			new DownloadImageTask(ImgProduct)
			            .execute(productCell.Image.ImageUrl);
			
			}
			
		
	
	else {
			gridView = (View) convertView;
		}

		return gridView;
	}
	
 class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

	
}
	
	
