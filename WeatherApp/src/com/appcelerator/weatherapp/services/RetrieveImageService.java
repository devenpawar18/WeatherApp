package com.appcelerator.weatherapp.services;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import com.appcelerator.weatherapp.data.DatabaseHandler;
import com.appcelerator.weatherapp.services.utils.HTTPRequest;

/**
 * Service to Retrieve Weather Icon
 * 
 * @author DEVEN
 * 
 */
public class RetrieveImageService implements Runnable {
	public interface RetrieveImageServiceListener {
		void onGetImageFinished();

		void onGetImageFailed(RetrieveImageService getImageService, String error);
	}

	private RetrieveImageServiceListener listener;
	private Drawable image;
	private String imageURL;
	private String errorMessage;
	private Context context;
	private String iconName;
	private DatabaseHandler db;

	public RetrieveImageService(Context context, String iconName) {
		this.context = context;
		this.iconName = iconName;
	}

	public void run() {
		// Form Weather Icon URL
		imageURL = Services.WEATHER_ICON_API_URL + iconName
				+ Services.IMAGE_EXTENSION;

		// Form request with Weather icon url
		HTTPRequest request = new HTTPRequest(imageURL, context);

		try {
			request.execute(HTTPRequest.RequestMethod.GET);
			image = request.getResponseDrawable();

			Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] weatherIconBlob = stream.toByteArray();

			/**
			 * Save Weather Icon to db
			 */
			db = new DatabaseHandler(context);
			db.openInternalDB();
			db.updateWeatherTable(DatabaseHandler.TABLE_WEATHER, iconName,
					weatherIconBlob);
			db.closeDB();

		} catch (Exception e) {
			image = null;
			errorMessage = e.getLocalizedMessage();
		}

		handler.sendEmptyMessage(0);
	}

	// Send Result to UI thread
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (image != null)
				listener.onGetImageFinished();
			else
				listener.onGetImageFailed(RetrieveImageService.this,
						errorMessage);
		}
	};

	public RetrieveImageServiceListener getListener() {
		return listener;
	}

	public void setListener(RetrieveImageServiceListener listener) {
		this.listener = listener;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}