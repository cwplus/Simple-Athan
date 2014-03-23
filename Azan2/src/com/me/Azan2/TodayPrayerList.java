package com.me.Azan2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Time;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TodayPrayerList 
{

	static class Salaats 
	{
		String Imsaak;
		String Fajr;
		String Sunrise;
		String Dhuhr;
		String Asr;
		String Sunset;
		String Maghrib;
		String Isha;
	}

	static class Day 
	{
		Map<String, Salaats> salaats;	
	}

	private static String latitude = "40";
	private static String longitude = "-73";
	private static String timeZone = "5";
	private static String month = "3";
	private static String year = "2014";
	private static String school = "0";

	private String timeFajr = "";
	private String timeDhuhr= "";
	private String timeAsr = "";
	private String timeMagrib = "";
	private String timeIsha = "";

	/**
	 * Constructor that takes dayOfMonth and then sets the prayer times of the day.
	 * @param dayOfMonth
	 * @throws Exception
	 */
	public TodayPrayerList(String dayOfMonth) throws Exception 
	{
		String json = readUrl
				(
						"http://praytime.info/getprayertimes.php?" +
								"lat=" + latitude +
								"lon=" + longitude +
								"gmt=" + timeZone +
								"m=" + month +
								"y=" + year +
								"school=" + school
						);

		Map<String, Salaats> day = new Gson().fromJson(json, new TypeToken<Map<String, Salaats>>(){}.getType());

		timeFajr = day.get(dayOfMonth).Fajr;
		timeDhuhr= day.get(dayOfMonth).Dhuhr;
		timeAsr = day.get(dayOfMonth).Asr;
		timeMagrib = day.get(dayOfMonth).Maghrib;
		timeIsha = day.get(dayOfMonth).Isha;
	}
	
	/**
	 * Returns a String array list of the prayer times of today.
	 * @return
	 */
	public String[] getSalaatTimes()
	{
		String[] salaatTimes = {"fajr", "dhuhr", "asr", "maghrib", "isha"};
		
		salaatTimes[0] = timeFajr;
		salaatTimes[1] = timeDhuhr;
		salaatTimes[2] = timeAsr;
		salaatTimes[3] = timeMagrib;
		salaatTimes[4] = timeIsha;
		
		return salaatTimes;	
	}

	/**
	 * Helper method that reads a URL.
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read); 

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
