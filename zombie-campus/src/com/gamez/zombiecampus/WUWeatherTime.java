package com.gamez.zombiecampus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Gets the current weather conditions using the Weather Underground API.
 * @author Greg Breard
 */
public class WUWeatherTime extends WeatherTime {
	
	// API stuff
	private final String API_KEY = "5fe672d9577a4954"; 
	private final String API_URL = "http://api.wunderground.com/api/" + API_KEY + "/astronomy/conditions/q/autoip.json";
	
	// JSON fields
	private final String API_FIELD_CONDITION = "weather";
	private final String API_FIELD_HOUR = "hour";
	private final String API_FIELD_MOONPHASE = "phaseofMoon";
	private final String API_FIELD_SUNRISE = "sunrise";
	private final String API_FIELD_SUNSET = "sunset";

	// Condition-enum maps
	private final String API_ENUM_MAP_CLEAR = "Clear,Squalls";
	private final String API_ENUM_MAP_CLOUDY = "Partly Cloudy,Mostly Cloudy,Scattered Clouds,Funnel Cloud";
	private final String API_ENUM_MAP_FOG = "Mist,Fog,Fog Patches,Smoke,Volcanic Ash,Widespread Dust,Sand,Haze,Spray,Dust Whirls,Sandstorm,Low Drifting Widespread Dust,Low Drifting Sand,Blowing Widespread Dust,Blowing Sand,Freezing Fog,Patches of Fog,Shallow Fog,Partial Fog";
	private final String API_ENUM_MAP_OVERCAST = "Overcast";
	private final String API_ENUM_MAP_RAIN = "Drizzle,Rain,Rain Mist,Rain Showers,Thunderstorm,Thunderstorms and Rain";
	private final String API_ENUM_MAP_SNOW = "Hail,Snow,Snow Grains,Ice Crystals,Ice Pellets,Low Drifting Snow,Blowing Snow,Snow Showers,Snow Blowing Snow Mist,Ice Pellet Showers,Hail Showers,Small Hail Showers,Thunderstorms and Snow,Thunderstorms and Ice Pellets,Thunderstorms with Hail,Thunderstorms with Small Hail,Freezing Drizzle,Freezing Rain,Small Hail";
	// Don't  need unknown, it's the default
	// private final String API_ENUM_MAP_UNKNOWN = "Unknown Precipitation,Unknown";
		
	/**
	 * Initialize a new instance of the WUWeather class.
	 */
	public WUWeatherTime() {
		loadData();
	} // end constructor
	
	/**
	 * Loads the objects data.
	 */
	private void loadData() {
		// Get the JSON data from the web
		String json = getWebData();
		
		// Parse the data
		m_sunRise =	Integer.parseInt(readJsonField(readJsonField(json, API_FIELD_SUNRISE), API_FIELD_HOUR));	
		m_sunSet =	Integer.parseInt(readJsonField(readJsonField(json, API_FIELD_SUNSET), API_FIELD_HOUR));
		m_timeOfDay = GregorianCalendar.getInstance().get(Calendar.HOUR_OF_DAY);
		m_moonPhase = parseMoonPhase(readJsonField(json, API_FIELD_MOONPHASE));
		m_weather = parseWeather(readJsonField(json, API_FIELD_CONDITION));
		
		System.out.println(m_weather);
	} // end loadData
	
	/**
	 * Return a MoonPhase corresponding to the input string.
	 */
	private MoonPhase parseMoonPhase(String moonPhase) {
		// Check the moon phase phase
		if (moonPhase.equals("New Moon"))
			return MoonPhase.NewMoon;
		else if (moonPhase.equals("Waxing Crescent"))
			return MoonPhase.WaxingCrescent;
		else if (moonPhase.equals("First Quarter"))
			return MoonPhase.FirstQuarter;
		else if (moonPhase.equals("Waxing Gibbous"))
			return MoonPhase.WaxingGibbous;
		else if (moonPhase.equals("Full Moon"))
			return MoonPhase.FullMoon;
		else if (moonPhase.equals("Waning Gibbous"))
			return MoonPhase.WaningGibbous;
		else if (moonPhase.equals("Last Quarter"))
			return MoonPhase.LastQuarter;
		else if (moonPhase.equals("Waning Crescent"))
			return MoonPhase.WaningCrescent;
		else // Default to full
			return MoonPhase.FullMoon;		
	} // end parseMoonPhase
	
	/**
	 * Return a Condition corresponding to the input string.
	 */
	private Condition parseWeather(String weather) {
		if (checkEnumMap(weather, API_ENUM_MAP_CLEAR))
			return Condition.Clear;
		else if (checkEnumMap(weather, API_ENUM_MAP_CLOUDY))
			return Condition.Cloudy;
		else if (checkEnumMap(weather, API_ENUM_MAP_FOG))
			return Condition.Fog;
		else if (checkEnumMap(weather, API_ENUM_MAP_OVERCAST))
			return Condition.Overcast;
		else if (checkEnumMap(weather, API_ENUM_MAP_RAIN))
			return Condition.Rain;	
		else if (checkEnumMap(weather, API_ENUM_MAP_SNOW))
			return Condition.Snow;
		else 		
			return Condition.Unknown;
	} // end parseWeather
	
	/**
	 * Gets the data from Weather Underground.
	 * @return String containing JSON data.
	 */
	private String getWebData() {
		String response = "";
		
		// Initialize reader
		BufferedReader reader = null;
		
		try {
			// Get the url
			URL url = new URL(API_URL);
			
			// Create the connection and reader
	        URLConnection con = url.openConnection();
	        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        
	        // Read the response
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) 
	        	response += inputLine;        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Make sure to close the reader
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				} // end catch
			} // end if
		} // end try
		
		return response;
	} // end getWebData
	
	/**
	 * Returns the value of a field in a JSON string.
	 * @param json the JSON string.
	 * @param fieldName the name of the field.
	 * @return String containing field value.
	 */
	private String readJsonField(String json, String fieldName) {
		// Get the indices
		Integer start = json.indexOf("\"" + fieldName + "\"");
		Integer end = 0;
			
		// Check for sub-object
		if (start < json.indexOf("{", start) && json.indexOf("{", start) < json.indexOf(",", start)) {
			start = json.indexOf("{", start) + 1;
			end = json.indexOf("}", start);
			return json.substring(start, end);
		} else
			end = json.indexOf(",", start);
		
		// Get the data
		String fieldData = json.substring(start, end);
		
		// Extract the value
		String value = fieldData.replace("\"", "");
		return value.split(":")[1].trim();
	} // end readJsonField
	
	/**
	 * Returns true if the enum map contains the value, false otherwise.
	 * @param value the value to check.
	 * @param enumMap the enum to check.
	 */
	private boolean checkEnumMap(String value, String enumMap) {
		// Get the possible values
		String vals[] = enumMap.split(",");
		
		// Check each value for match
		for (int i = 0; i < vals.length; i++) {
			if (value.contains(vals[i]))
				return true; 
		} // end for
		
		// Otherwise no match
		return false;
	} // end checkEnumMap
	
} // end WUWeather
