package com.appcelerator.weatherapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.appcelerator.weatherapp.ApplicationEx;
import com.appcelerator.weatherapp.R;
import com.appcelerator.weatherapp.data.DatabaseHandler;
import com.appcelerator.weatherapp.entity.Weather;
import com.appcelerator.weatherapp.services.RetrieveImageService;
import com.appcelerator.weatherapp.services.RetrieveImageService.RetrieveImageServiceListener;
import com.appcelerator.weatherapp.services.RetrieveLatLong;
import com.appcelerator.weatherapp.services.RetrieveWeatherService;
import com.appcelerator.weatherapp.services.RetrieveWeatherService.RetrieveWeatherServiceListener;
import com.appcelerator.weatherapp.utils.WeatherAppUtils;

/**
 * Home Activity to retrieve Weather Info
 * 
 * @author DEVEN
 * 
 */
public class HomeActivity extends Activity implements
		RetrieveWeatherServiceListener, RetrieveImageServiceListener {
	// UI Elements
	private TextView temperatureTextView;
	private ImageView weatherIconImageView;
	private TextView realFeelTextView;
	private TextView lastUpdatedTextView;
	private TextView sunriseTextView;
	private TextView sunsetTextView;
	private TextView pressureTextView;
	private TextView seaLevelTextView;
	private TextView humidityTextView;
	private TextView cloudsTextView;
	private TextView windSpeedTextView;
	private TextView windDirectionTextView;
	private ProgressDialog progressDialog;

	// Current Weather
	private Weather weather;

	// Manage database operations
	private DatabaseHandler db;

	public static final int REQUEST_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// Get reference to UI elements
		weatherIconImageView = (ImageView) findViewById(R.id.iv_weather_icon);
		temperatureTextView = (TextView) findViewById(R.id.tv_temperature);
		realFeelTextView = (TextView) findViewById(R.id.tv_real_feel);
		lastUpdatedTextView = (TextView) findViewById(R.id.tv_last_updated_value);
		sunriseTextView = (TextView) findViewById(R.id.tv_sunrise_value);
		sunsetTextView = (TextView) findViewById(R.id.tv_sunset_value);
		pressureTextView = (TextView) findViewById(R.id.tv_pressure_value);
		seaLevelTextView = (TextView) findViewById(R.id.tv_sea_level_value);
		humidityTextView = (TextView) findViewById(R.id.tv_humidity_value);
		cloudsTextView = (TextView) findViewById(R.id.tv_clouds_value);
		windSpeedTextView = (TextView) findViewById(R.id.tv_wind_speed_value);
		windDirectionTextView = (TextView) findViewById(R.id.tv_wind_direction_value);

		// Call Service to get Weather at current location
		getCurrentLocationWeather();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_refresh:
			getCurrentLocationWeather();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Checks if the network connection and gps are available, if yes then hit
	 * the web service call to get Weather of current location.
	 */
	public void getCurrentLocationWeather() {
		if (WeatherAppUtils.isConnectionAvailable(this)) {
			getCurrentLocation();
			if (ApplicationEx.latitude == 0.0 && ApplicationEx.longitude == 0.0) {
				/**
				 * Ask user to turn GPS ON
				 */
				displayGPSMessage();
			} else {
				/**
				 * Call Weather Service
				 */
				getWeather();
			}
		} else {
			/**
			 * Retrieve Info from Database when no network
			 */
			retrieveWeatherFromDB();
			WeatherAppUtils.showToast(HomeActivity.this, getResources()
					.getString(R.string.network_error));
		}

	}

	/**
	 * Retrieve weather from Database
	 */
	public void retrieveWeatherFromDB() {
		try {
			db = new DatabaseHandler(getApplicationContext());
			db.openInternalDB();
			weather = db.getWeatherInfo();
			updateUserInterface();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	/**
	 * Display GPS Message
	 */
	public void displayGPSMessage() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
		dialog.setTitle("Turn On GPS");
		dialog.setIcon(R.drawable.gps);
		dialog.setMessage("Please turn on the GPS to get current location");
		dialog.setCancelable(false);
		dialog.setPositiveButton(getResources().getString(R.string.turn_on),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent gpsSettings = new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivityForResult(gpsSettings, REQUEST_CODE);
					}
				});
		dialog.setNegativeButton(
				getResources().getString(android.R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Dismiss Dialog
						dialog.dismiss();
						finish();
					}
				});

		dialog.show();

	}

	/**
	 * Update the User Interface with weather info
	 */
	public void updateUserInterface() {
		String title = getResources().getString(R.string.weather_at)
				+ "<font color=#FFFF00>" + " " + weather.getCity() + "</font>";
		getActionBar().setTitle(Html.fromHtml(title));
		temperatureTextView.setText(""
				+ WeatherAppUtils.convertKelvinToDegreeFahrenheit(weather
						.getTemperature()) + "\u2109");
		weatherIconImageView.setImageBitmap(BitmapFactory.decodeByteArray(
				weather.getWeatherIcon(), 0, weather.getWeatherIcon().length));
		realFeelTextView.setText("Real Feel "
				+ WeatherAppUtils.convertKelvinToDegreeFahrenheit(weather
						.getMinTemperature()) + "\u2109");

		lastUpdatedTextView.setText(weather.getLastUpdated());
		sunriseTextView.setText(weather.getSunriseTime());
		sunsetTextView.setText(weather.getSunsetTime());
		pressureTextView
				.setText(""
						+ WeatherAppUtils.convertHpaToIn(weather.getPressure())
						+ " in");
		seaLevelTextView
				.setText(""
						+ WeatherAppUtils.convertHpaToIn(weather.getSeaLevel())
						+ " in");
		humidityTextView.setText(weather.getHumidity() + "%");
		cloudsTextView.setText(weather.getCloudiness() + "%");
		windSpeedTextView.setText("" + weather.getWindSpeed() + " mph");
		windDirectionTextView.setText("" + weather.getWindDirection()
				+ (char) 0x00B0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			getCurrentLocationWeather();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Get Weather for Current Location
	 */
	private void getWeather() {
		progressDialog = ProgressDialog.show(HomeActivity.this, "",
				"Loading Weather...");
		RetrieveWeatherService service = new RetrieveWeatherService(
				getApplicationContext());
		service.setListener(this);
		ApplicationEx.operationsQueue.execute(service);
	}

	/**
	 * Get Weather Icon for current Weather
	 * 
	 * @param imageURL
	 */
	private void getImage(String imageURL) {
		RetrieveImageService service = new RetrieveImageService(
				HomeActivity.this, weather.getWeatherIconURL());
		service.setListener(this);
		service.setImageURL(imageURL);
		ApplicationEx.operationsQueue.execute(service);
	}

	/**
	 * Retrieves current location lat-long
	 */
	public void getCurrentLocation() {
		RetrieveLatLong latLongService = new RetrieveLatLong(this);
		ApplicationEx.latitude = latLongService.getLatitude();
		ApplicationEx.longitude = latLongService.getLongitude();
	}

	/**
	 * Image Service Success Callback
	 */
	@Override
	public void onGetImageFinished() {
		dismissProgressDialog();
		retrieveWeatherFromDB();
		updateUserInterface();

	}

	/**
	 * Image Service Failure Callback
	 */
	@Override
	public void onGetImageFailed(RetrieveImageService getImageService,
			String error) {
		dismissProgressDialog();
		WeatherAppUtils.showToast(HomeActivity.this, error);
	}

	/**
	 * Weather Service Success Callback
	 */
	@Override
	public void onRetrieveWeatherFinished(Weather weather) {
		this.weather = weather;
		if (weather != null && weather.getWeatherIconURL() != null)
			getImage(weather.getWeatherIconURL());

	}

	/**
	 * Weather Service Failure Callback
	 */
	@Override
	public void onRetrieveWeatherFailed(int error, String message) {
		dismissProgressDialog();
		WeatherAppUtils.showToast(HomeActivity.this, message);
	}

	/**
	 * Dismiss Progress Dialog
	 */
	public void dismissProgressDialog() {
		if (progressDialog != null || progressDialog.isShowing())
			progressDialog.dismiss();
	}
}
