package com.appcelerator.weatherapp.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.appcelerator.weatherapp.ApplicationEx;

/**
 * Utils class of Weather App that contains common and utility methods.
 * 
 * @author DEVEN
 * 
 */
public class WeatherAppUtils {

	/**
	 * method to check for network availability. returns true for available and
	 * false for unavailable
	 */
	public static boolean isConnectionAvailable(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetwork = conn
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobileNetwork = conn
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifiNetwork != null && wifiNetwork.isAvailable() == true
				&& wifiNetwork.isConnectedOrConnecting() == true) {
			return true;
		} else if (mobileNetwork != null && mobileNetwork.isAvailable() == true
				&& mobileNetwork.isConnectedOrConnecting() == true) {
			return true;
		} else
			return false;
	}

	/**
	 * Converts long to date
	 * 
	 * @param timeStamp
	 *            date
	 * @return date converts the date in required format
	 */
	public static String convertToDate(long timeStamp) {
		if (timeStamp <= 0) {
			return "";
		}
		Calendar cal = Calendar.getInstance();
		TimeZone tz = cal.getTimeZone();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"EEE, MMM d, yyyy - h:mm a");
		formatter.setTimeZone(tz);
		String strDate = formatter.format(new Date(timeStamp * 1000));
		return strDate;
	}

	/**
	 * 
	 * @param temp
	 * @return temperature in degree Fahrenheit
	 */
	public static int convertKelvinToDegreeFahrenheit(double temp) {

		double fahTemp = 0;
		fahTemp = temp * 9.0 / 5 - 459.67;
		return (int) Math.round(fahTemp);
	}

	/**
	 * 
	 * @param pressure
	 * @return pressure in inhg
	 */
	public static double convertHpaToIn(double pressure) {
		double pressureIn = 0.0;
		pressureIn = pressure * 0.0295;
		return (double) Math.round(pressureIn);

	}

	/**
	 * Displays Toast with the provided message
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param context
	 * @return Current City
	 * @throws IOException
	 */
	public static String getCurrentAddress(Context context) throws IOException {
		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(context, Locale.getDefault());

		// Here 1 represents max location result to returned by document
		addresses = geocoder.getFromLocation(ApplicationEx.latitude,
				ApplicationEx.longitude, 1);

		// If any additional address line present then only check, check with
		// max available address lines by getMaxAddressLineIndex()
		String city = addresses.get(0).getLocality();

		return city;
	}
}
