package com.appcelerator.weatherapp.services;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.appcelerator.weatherapp.ApplicationEx;
import com.appcelerator.weatherapp.data.DatabaseHandler;
import com.appcelerator.weatherapp.entity.Weather;
import com.appcelerator.weatherapp.services.utils.HTTPRequest;
import com.appcelerator.weatherapp.utils.Constants;
import com.appcelerator.weatherapp.utils.Constants.WeatherAppDialogCodes;
import com.appcelerator.weatherapp.utils.WeatherAppUtils;

/**
 * Service to retrieve weather details
 * 
 * @author DEVEN
 * 
 */
public class RetrieveWeatherService implements Runnable {
	/**
	 * Listener for WeatherService
	 */
	public interface RetrieveWeatherServiceListener {
		void onRetrieveWeatherFinished(Weather weather);

		void onRetrieveWeatherFailed(int error, String message);
	}

	private static final String TAG = "RetrieveWeatherService";
	/** Retrieve Weather URL */
	private static String RETRIEVE_WEATHER_URL = "";
	private RetrieveWeatherServiceListener listener;
	private String jsonResponse;
	private int statusCode;
	private Context context;
	private Weather weather = new Weather();
	private DatabaseHandler db;

	public RetrieveWeatherService(Context context) {
		this.context = context;
	}

	/**
	 * Sends a GET request to retrieve Weather
	 */
	public void run() {
		Message message = new Message();
		try {
			// Form Weather URL
			RETRIEVE_WEATHER_URL = Services.WEATHER_API_URL + Services.LAT
					+ ApplicationEx.latitude + Services.LONG
					+ ApplicationEx.longitude;
			// Create HTTP Request Object for Retrieving Weather
			HTTPRequest request = new HTTPRequest(RETRIEVE_WEATHER_URL, context);
			statusCode = request.execute(HTTPRequest.RequestMethod.GET);
			message.what = statusCode;
			// Weather Info as JSON response
			jsonResponse = request.getResponseString();
			if (message.what == WeatherAppDialogCodes.SUCCESS) {
				if (!TextUtils.isEmpty(jsonResponse)) {
					weather = parseRetrievedWeather(jsonResponse);
				}
			}
			Log.d(TAG, "run::" + jsonResponse);
			weatherHandler.sendMessage(message);
		} catch (Exception e) {
			message.what = statusCode;
			weatherHandler.sendMessage(message);
			Log.e(TAG, "Weather Service exception::" + e);
		}

	}

	// Return result to Main Thread
	private Handler weatherHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.WeatherAppDialogCodes.SUCCESS:
				if (!TextUtils.isEmpty(jsonResponse)) {
					listener.onRetrieveWeatherFinished(weather);
				} else {
					listener.onRetrieveWeatherFailed(
							Constants.WeatherAppDialogCodes.NETWORK_ERROR,
							Constants.WeatherAppDialogMessages.NETWORK_ERROR);
				}
				break;
			case Constants.WeatherAppDialogCodes.DATA_NOT_FOUND:
				listener.onRetrieveWeatherFailed(
						Constants.WeatherAppDialogCodes.DATA_NOT_FOUND,
						Constants.WeatherAppDialogMessages.NOT_FOUND);
				break;
			case Constants.WeatherAppDialogCodes.INTERNAL_SERVER_ERROR:
				listener.onRetrieveWeatherFailed(
						Constants.WeatherAppDialogCodes.INTERNAL_SERVER_ERROR,
						Constants.WeatherAppDialogMessages.INTERNAL_SERVER_ERROR);
				break;
			case Constants.WeatherAppDialogCodes.NETWORK_ERROR:
				listener.onRetrieveWeatherFailed(
						Constants.WeatherAppDialogCodes.NETWORK_ERROR,
						Constants.WeatherAppDialogMessages.NETWORK_ERROR);
				break;
			default:
				listener.onRetrieveWeatherFailed(
						Constants.WeatherAppDialogCodes.NETWORK_ERROR,
						Constants.WeatherAppDialogMessages.NETWORK_ERROR);
				break;
			}
		}
	};

	/**
	 * Get listener
	 * 
	 * @return
	 */
	public RetrieveWeatherServiceListener getListener() {
		return listener;
	}

	/**
	 * Set listener
	 * 
	 * @return
	 */
	public void setListener(RetrieveWeatherServiceListener listener) {
		this.listener = listener;
	}

	/**
	 * Store Weather details in database
	 * 
	 * @param response
	 * @return
	 */
	private Weather parseRetrievedWeather(String response) {

		try {
			db = new DatabaseHandler(context);
			db.openInternalDB();
			JSONObject jsonObject = new JSONObject(jsonResponse);
			Weather weather = new Weather();
			weather.deserializeJSON(jsonObject);
			weather.setCity(WeatherAppUtils.getCurrentAddress(context));
			db.deleteAllTableEntries(DatabaseHandler.TABLE_WEATHER);
			db.addWeather(DatabaseHandler.TABLE_WEATHER, weather);
			return weather;
		} catch (JSONException e) {
			e.printStackTrace();
			listener.onRetrieveWeatherFailed(
					Constants.WeatherAppDialogCodes.DATA_NOT_FOUND,
					Constants.WeatherAppDialogMessages.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeDB();
		}
		return null;
	}

}
