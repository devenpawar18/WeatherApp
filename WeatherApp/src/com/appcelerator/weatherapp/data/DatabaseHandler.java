package com.appcelerator.weatherapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appcelerator.weatherapp.entity.Weather;

/**
 * Database Handler class to manage database activities
 * 
 * @author DEVEN
 */

public class DatabaseHandler extends SQLiteOpenHelper {

	/**
	 * Database Version
	 */
	private static final int DATABASE_VERSION = 1;
	/**
	 * Database Name
	 */
	private static final String DATABASE_NAME = "weatherapp.db";

	/**
	 * Database Tables & Columns
	 */

	private static final String KEY_ID = "_id";

	/**
	 * Table Weather
	 */
	public static final String TABLE_WEATHER = "weather";
	/**
	 * Fields for Table Weather
	 */
	private static final String KEY_WEATHER_TITLE = "WEATHER_TITLE";
	private static final String KEY_WEATHER_DESCRIPTION = "WEATHER_DESCRIPTION";
	private static final String KEY_WEATHER_ICON_URL = "WEATHER_ICON_URL";
	private static final String KEY_WEATHER_ICON_BLOB = "WEATHER_ICON_BLOB";
	private static final String KEY_TEMPERATURE = "TEMPERATURE";
	private static final String KEY_MIN_TEMPERATURE = "MIN_TEMPERATURE";
	private static final String KEY_MAX_TEMPERATURE = "MAX_TEMPERATURE";
	private static final String KEY_PRESSURE = "PRESSURE";
	private static final String KEY_SEA_LEVEL = "SEA_LEVEL";
	private static final String KEY_HUMIDITY = "HUMIDITY";
	private static final String KEY_WIND_SPEED = "WIND_SPEED";
	private static final String KEY_WIND_DIRECTION = "WIND_DIRECTION";
	private static final String KEY_CLOUDINESS = "CLOUDINESS";
	private static final String KEY_RAIN_HISTORY = "RAIN_HISTORY";
	private static final String KEY_LAST_UPDATED = "LAST_UPDATED";
	private static final String KEY_CITY = "CITY";
	private static final String KEY_COUNTRY = "COUNTRY";
	private static final String KEY_SUNRISE_TIME = "SUNRISE_TIME";
	private static final String KEY_SUNSET_TIME = "SUNSET_TIME";
	private static final String KEY_STATUS_CODE = "STATUS_CODE";

	private Context context;

	private SQLiteDatabase m_db;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out
				.println("###############################Creating tables########################");

		/**
		 * Creating Weather Table
		 */
		String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_WEATHER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_WEATHER_TITLE
				+ " TEXT," + KEY_WEATHER_DESCRIPTION + " TEXT,"
				+ KEY_WEATHER_ICON_URL + " TEXT," + KEY_WEATHER_ICON_BLOB
				+ " BLOB," + KEY_TEMPERATURE + " INTEGER,"
				+ KEY_MIN_TEMPERATURE + " INTEGER," + KEY_MAX_TEMPERATURE
				+ " INTEGER," + KEY_PRESSURE + " INTEGER," + KEY_SEA_LEVEL
				+ " INTEGER," + KEY_HUMIDITY + " INTEGER," + KEY_WIND_SPEED
				+ " INTEGER," + KEY_WIND_DIRECTION + " INTEGER,"
				+ KEY_CLOUDINESS + " INTEGER," + KEY_RAIN_HISTORY + " INTEGER,"
				+ KEY_LAST_UPDATED + " TEXT," + KEY_CITY + " TEXT,"
				+ KEY_COUNTRY + " TEXT," + KEY_SUNRISE_TIME + " TEXT,"
				+ KEY_SUNSET_TIME + " TEXT," + KEY_STATUS_CODE + " INTEGER"
				+ ")";

		db.execSQL(CREATE_WEATHER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/**
		 * Drop older table if existed
		 */
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);

		/**
		 * Create tables again
		 */
		onCreate(db);
	}

	/**
	 * Opens the Database.
	 */
	public void openInternalDB() {
		if ((m_db == null) || (m_db.isOpen() == false)) {
			m_db = this.getWritableDatabase();
		}
	}

	/**
	 * Closes the Database.
	 */
	public void closeDB() {
		if (m_db != null) {
			m_db.close();
			m_db = null;
		}
	}

	/**
	 * Deletes all table entries of WEATHER TABLE
	 * 
	 * @param TABLE_NAME
	 */
	public void deleteAllTableEntries(String TABLE_NAME) {
		int rows = m_db.delete(TABLE_NAME, null, null);
		System.out.println("****deleted rows******" + rows);
	}

	/**
	 * Updates Weather Table by adding Weather Icon Blob
	 * 
	 * @param TABLE_NAME
	 * @param weatherIcon
	 * @param imageBlob
	 */
	public void updateWeatherTable(String TABLE_NAME, String weatherIcon,
			byte[] imageBlob) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();

			values.put(KEY_WEATHER_ICON_BLOB, imageBlob);
			db.update(TABLE_NAME, values, KEY_WEATHER_ICON_URL + "=?",
					new String[] { weatherIcon });

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	/**
	 * Adds Weather to Database.
	 */
	public void addWeather(String TABLE_NAME, Weather weather) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_WEATHER_TITLE, weather.getWeatherTitle());
		values.put(KEY_WEATHER_DESCRIPTION, weather.getWeatherDescription());
		values.put(KEY_WEATHER_ICON_URL, weather.getWeatherIconURL());
		values.put(KEY_WEATHER_ICON_BLOB, weather.getWeatherIcon());
		values.put(KEY_TEMPERATURE, weather.getTemperature());
		values.put(KEY_MIN_TEMPERATURE, weather.getMinTemperature());
		values.put(KEY_MAX_TEMPERATURE, weather.getMaxTemperature());
		values.put(KEY_PRESSURE, weather.getPressure());
		values.put(KEY_SEA_LEVEL, weather.getSeaLevel());
		values.put(KEY_HUMIDITY, weather.getHumidity());
		values.put(KEY_WIND_SPEED, weather.getWindSpeed());
		values.put(KEY_WIND_DIRECTION, weather.getWindDirection());
		values.put(KEY_CLOUDINESS, weather.getCloudiness());
		values.put(KEY_RAIN_HISTORY, weather.getRainHistory());
		values.put(KEY_LAST_UPDATED, weather.getLastUpdated());
		values.put(KEY_CITY, weather.getCity());
		values.put(KEY_COUNTRY, weather.getCountry());
		values.put(KEY_SUNRISE_TIME, weather.getSunriseTime());
		values.put(KEY_SUNSET_TIME, weather.getSunsetTime());
		values.put(KEY_STATUS_CODE, weather.getStatusCode());
		long result = db.insert(TABLE_NAME, null, values);
		System.out.println("Weather Insertion Result " + result);
		db.close();
	}

	/**
	 * 
	 * @return Weather Object from database.
	 */
	public Weather getWeatherInfo() {

		String selectQuery = "SELECT  * FROM " + TABLE_WEATHER;
		SQLiteDatabase db = this.getWritableDatabase();
		Weather weather = null;

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				weather = new Weather();
				weather.setWeatherTitle(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_WEATHER_TITLE)));
				weather.setWeatherDescription(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_WEATHER_DESCRIPTION)));
				weather.setWeatherIconURL(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_WEATHER_ICON_URL)));
				weather.setWeatherIcon(cursor.getBlob(cursor
						.getColumnIndex(DatabaseHandler.KEY_WEATHER_ICON_BLOB)));
				weather.setTemperature(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_TEMPERATURE)));
				weather.setMinTemperature(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_MIN_TEMPERATURE)));
				weather.setMaxTemperature(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_MAX_TEMPERATURE)));
				weather.setPressure(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_PRESSURE)));
				weather.setSeaLevel(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_SEA_LEVEL)));
				weather.setHumidity(cursor.getInt(cursor
						.getColumnIndex(DatabaseHandler.KEY_HUMIDITY)));
				weather.setWindSpeed(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_WIND_SPEED)));
				weather.setWindDirection(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_WIND_DIRECTION)));
				weather.setCloudiness(cursor.getInt(cursor
						.getColumnIndex(DatabaseHandler.KEY_CLOUDINESS)));
				weather.setRainHistory(cursor.getDouble(cursor
						.getColumnIndex(DatabaseHandler.KEY_RAIN_HISTORY)));
				weather.setLastUpdated(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_LAST_UPDATED)));
				weather.setCity(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_CITY)));
				weather.setCountry(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_COUNTRY)));
				weather.setSunriseTime(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_SUNRISE_TIME)));
				weather.setSunsetTime(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.KEY_SUNSET_TIME)));
				weather.setStatusCode(cursor.getInt(cursor
						.getColumnIndex(DatabaseHandler.KEY_STATUS_CODE)));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return weather;
	}
}
