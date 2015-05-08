package com.appcelerator.weatherapp.entity;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.appcelerator.weatherapp.utils.WeatherAppUtils;

/**
 * Weather Entity that contains all information about weather of user's current
 * location
 * 
 * @author DEVEN
 * 
 */
public class Weather implements Entitiy, Parcelable {
	// Group of weather parameters (Rain, Snow, Extreme etc.)
	private String weatherTitle;
	// Weather condition within the group
	private String weatherDescription;
	// URL to download Weather Icon
	private String weatherIconURL;
	// Weather Icon in byte array to store in database
	private byte[] weatherIcon;
	// Temperature, Kelvin (subtract 273.15 to convert to Celsius)
	private double temperature;
	// Minimum temperature at the moment. This is deviation from current temp
	// that is possible for large cities and megalopolises geographically
	// expanded (use these parameter optionally)
	private double minTemperature;
	// Maximum temperature at the moment. This is deviation from current temp
	// that is possible for large cities and megalopolises geographically
	// expanded (use these parameter optionally)
	private double maxTemperature;
	// Atmospheric pressure (on the sea level, if there is no sea_level or
	// grnd_level data), hPa
	private double pressure;
	// Atmospheric pressure on the sea level, hPa
	private double seaLevel;
	// Humidity in %
	private int humidity;
	// Wind speed, mps
	private double windSpeed;
	// Wind direction, degrees (meteorological)
	private double windDirection;
	// Cloudiness, %
	private int cloudiness;
	// Precipitation volume for last 3 hours, mm
	private double rainHistory;
	// Data receiving time, unix time, GMT
	private String lastUpdated;
	// City name to retrieve weather based on city
	private String city;
	// Country to which the city belongs
	private String country;
	// Sunrise Time
	private String sunriseTime;
	// SunSet Time
	private String sunsetTime;
	// Status code for the operation
	private int statusCode;

	public Weather() {

	}

	/**
	 * 
	 * @return Weather Title
	 */
	public String getWeatherTitle() {
		return weatherTitle;
	}

	/**
	 * 
	 * @param weatherTitle
	 *            Set Weather Title
	 */
	public void setWeatherTitle(String weatherTitle) {
		this.weatherTitle = weatherTitle;
	}

	/**
	 * 
	 * @return Weather Description
	 */
	public String getWeatherDescription() {
		return weatherDescription;
	}

	/**
	 * 
	 * @param weatherDescription
	 *            Set Weather Description
	 */
	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	/**
	 * 
	 * @return get Weather Icon URL
	 */
	public String getWeatherIconURL() {
		return weatherIconURL;
	}

	/**
	 * 
	 * @param weatherIconURL
	 *            Set Weather Icon URL
	 */
	public void setWeatherIconURL(String weatherIconURL) {
		this.weatherIconURL = weatherIconURL;
	}

	/**
	 * 
	 * @return Weather Image
	 */
	public byte[] getWeatherIcon() {
		return weatherIcon;
	}

	/**
	 * 
	 * @param weatherIcon
	 *            Set Weather Image
	 */
	public void setWeatherIcon(byte[] weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	/**
	 * 
	 * @return Weather Temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * 
	 * @param temperature
	 *            Set Weather Temperature
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * 
	 * @return Minimum Temperature
	 */
	public double getMinTemperature() {
		return minTemperature;
	}

	/**
	 * 
	 * @param minTemperature
	 *            Minimum Temperature
	 */
	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	/**
	 * 
	 * @return Maximum Temperature
	 */
	public double getMaxTemperature() {
		return maxTemperature;
	}

	/**
	 * 
	 * @param maxTemperature
	 *            Set Maximum Temperature
	 */
	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	/**
	 * 
	 * @return Pressure
	 */
	public double getPressure() {
		return pressure;
	}

	/**
	 * 
	 * @param pressure
	 *            Set Pressure
	 */
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	/**
	 * 
	 * @return Sea Level
	 */
	public double getSeaLevel() {
		return seaLevel;
	}

	/**
	 * 
	 * @param seaLevel
	 *            Set Sea Level
	 */
	public void setSeaLevel(double seaLevel) {
		this.seaLevel = seaLevel;
	}

	/**
	 * 
	 * @return Humidity in %
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * 
	 * @param humidity
	 *            Set Humidity in %
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	/**
	 * 
	 * @return Wind speed, mps
	 */
	public double getWindSpeed() {
		return windSpeed;
	}

	/**
	 * 
	 * @param windSpeed
	 *            Set Wind speed, mps
	 */
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * 
	 * @return Wind direction, degrees (meteorological)
	 */
	public double getWindDirection() {
		return windDirection;
	}

	/**
	 * 
	 * @param windDirection
	 *            Set Wind direction, degrees (meteorological)
	 */
	public void setWindDirection(double windDirection) {
		this.windDirection = windDirection;
	}

	/**
	 * 
	 * @return Cloudiness in %
	 */
	public int getCloudiness() {
		return cloudiness;
	}

	/**
	 * 
	 * @param cloudiness
	 *            Set Cloudiness in %
	 */
	public void setCloudiness(int cloudiness) {
		this.cloudiness = cloudiness;
	}

	/**
	 * 
	 * @return Precipitation volume for last 3 hours, mm
	 */
	public double getRainHistory() {
		return rainHistory;
	}

	/**
	 * 
	 * @param rainHistory
	 *            Set Precipitation volume for last 3 hours, mm
	 */
	public void setRainHistory(double rainHistory) {
		this.rainHistory = rainHistory;
	}

	/**
	 * 
	 * @return Date and Time of last updated weather
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * 
	 * @param lastUpdated
	 *            Date and Time of last updated weather
	 */
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * 
	 * @return City name
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @param city
	 *            Set city name
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 
	 * @return country name
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 
	 * @param country
	 *            Set country name
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @return Sunrise Time
	 */
	public String getSunriseTime() {
		return sunriseTime;
	}

	/**
	 * 
	 * @param sunriseTime
	 *            Set Sunrise Time
	 */
	public void setSunriseTime(String sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	/**
	 * 
	 * @return Sunset time
	 */
	public String getSunsetTime() {
		return sunsetTime;
	}

	/**
	 * 
	 * @param sunsetTime
	 *            Set Sunset time
	 */
	public void setSunsetTime(String sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	/**
	 * 
	 * @return Status code of retrieving weather operation
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 
	 * @param statusCode
	 *            Set Status code of retrieving weather operation
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public JSONObject serializeJSON() throws Exception {
		return null;
	}

	/**
	 * Method used to deserialize json for Weather object
	 */
	@Override
	public void deserializeJSON(JSONObject myWeatherObject) throws Exception {
		/**
		 * Parse entire Weather Object
		 */

		if (myWeatherObject.has("sys")) {
			JSONObject sysObject = myWeatherObject.getJSONObject("sys");
			this.setSunriseTime(sysObject.has("sunrise") ? WeatherAppUtils
					.convertToDate(sysObject.getLong("sunrise")) : "N/A");
			this.setSunsetTime(sysObject.has("sunset") ? WeatherAppUtils
					.convertToDate(sysObject.getLong("sunset")) : "N/A");

		}

		if (myWeatherObject.has("weather")) {
			JSONObject weatherObject = (JSONObject) myWeatherObject
					.getJSONArray("weather").get(0);
			this.setWeatherTitle(weatherObject.has("main") ? weatherObject
					.getString("main") : "N/A");
			this.setWeatherDescription(weatherObject.has("description") ? weatherObject
					.getString("description") : "N/A");
			this.setWeatherIconURL(weatherObject.has("icon") ? weatherObject
					.getString("icon") : null);

		}

		if (myWeatherObject.has("main")) {
			JSONObject mainObject = myWeatherObject.getJSONObject("main");
			this.setTemperature(mainObject.has("temp") ? mainObject
					.getDouble("temp") : 0.0);
			this.setMinTemperature(mainObject.has("temp_min") ? mainObject
					.getDouble("temp_min") : 0.0);
			this.setMaxTemperature(mainObject.has("temp_max") ? mainObject
					.getDouble("temp_max") : 0.0);
			this.setPressure(mainObject.has("pressure") ? mainObject
					.getDouble("pressure") : 0.0);
			this.setSeaLevel(mainObject.has("sea_level") ? mainObject
					.getDouble("sea_level") : 0.0);
			this.setHumidity(mainObject.has("humidity") ? mainObject
					.getInt("humidity") : -1);
		}

		if (myWeatherObject.has("wind")) {
			JSONObject windObject = myWeatherObject.getJSONObject("wind");
			this.setWindSpeed(windObject.has("speed") ? windObject
					.getDouble("speed") : 0.0);
			this.setWindDirection(windObject.has("deg") ? windObject
					.getDouble("deg") : 0.0);

		}

		if (myWeatherObject.has("clouds")) {
			JSONObject cloudsObject = myWeatherObject.getJSONObject("clouds");
			this.setCloudiness(cloudsObject.has("all") ? cloudsObject
					.getInt("all") : -1);
		}

		if (myWeatherObject.has("rain")) {
			JSONObject rainObject = myWeatherObject.getJSONObject("rain");
			this.setRainHistory(rainObject.has("3h") ? rainObject.getInt("3h")
					: -1);
		}

		this.setLastUpdated(myWeatherObject.has("dt") ? WeatherAppUtils
				.convertToDate(myWeatherObject.getLong("dt")) : "N/A");

		this.setStatusCode(myWeatherObject.has("cod") ? myWeatherObject
				.getInt("cod") : -1);

	}

	/**
	 * 
	 * @return creator
	 */
	public static Parcelable.Creator<Weather> getCreator() {
		return CREATOR;
	}

	private Weather(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Write Weather Object to parcel
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(weatherTitle);
		out.writeString(weatherDescription);
		out.writeString(weatherIconURL);
		out.writeByteArray(weatherIcon);
		out.writeDouble(temperature);
		out.writeDouble(minTemperature);
		out.writeDouble(maxTemperature);
		out.writeDouble(pressure);
		out.writeDouble(seaLevel);
		out.writeInt(humidity);
		out.writeDouble(windSpeed);
		out.writeDouble(windDirection);
		out.writeInt(cloudiness);
		out.writeDouble(rainHistory);
		out.writeString(lastUpdated);
		out.writeString(city);
		out.writeString(country);
		out.writeString(sunriseTime);
		out.writeString(sunsetTime);
		out.writeInt(statusCode);
	}

	/**
	 * Read Weather Object from Parcel
	 * 
	 * @param in
	 */
	public void readFromParcel(Parcel in) {
		weatherTitle = in.readString();
		weatherDescription = in.readString();
		weatherIconURL = in.readString();
		weatherIcon = new byte[in.readInt()];
		temperature = in.readDouble();
		minTemperature = in.readDouble();
		maxTemperature = in.readDouble();
		pressure = in.readDouble();
		seaLevel = in.readDouble();
		humidity = in.readInt();
		windSpeed = in.readDouble();
		windDirection = in.readDouble();
		cloudiness = in.readInt();
		rainHistory = in.readDouble();
		lastUpdated = in.readString();
		city = in.readString();
		country = in.readString();
		sunriseTime = in.readString();
		sunsetTime = in.readString();
		statusCode = in.readInt();

	}

	public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
		public Weather createFromParcel(Parcel in) {
			return new Weather(in);
		}

		public Weather[] newArray(int size) {
			return new Weather[size];
		}
	};

}
