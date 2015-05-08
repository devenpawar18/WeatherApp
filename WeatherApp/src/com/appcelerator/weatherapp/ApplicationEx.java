package com.appcelerator.weatherapp;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;

/**
 * Application level class to initialize and maintain various application life
 * cycle specific details
 */

/**
 * @author devpawar
 */

public class ApplicationEx extends android.app.Application {
	/**
	 * used to set core number of threads
	 */
	private static final int CORE_POOL_SIZE = 6;
	/**
	 * used to set the maximum allowed number of threads.
	 */
	private static final int MAXIMUM_POOL_SIZE = 6;
	/**
	 * executes each submitted task using one of possibly several pooled threads
	 */
	public static ThreadPoolExecutor operationsQueue;
	/** Application Context */
	public static Context context;

	// Lat-Long
	public static double latitude;
	public static double longitude;

	/**
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();

		operationsQueue = new ThreadPoolExecutor(CORE_POOL_SIZE,
				MAXIMUM_POOL_SIZE, 100000L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

	}

}
