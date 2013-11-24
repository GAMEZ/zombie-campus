package com.gamez.zombiecampus;

/**
 * Represents the current time and weather conditions.
 * @author Greg Breard
 */
public class WeatherTime {
	
	/**
	 * Represents the current condition.
	 */
	public enum Condition {
		Rain,
		Snow,
		Fog,
		Overcast,
		Clear,
		Cloudy,
		Unknown		
	} // end Condition
	
	/**
	 * Represents the current moon phase.
	 */
	public enum MoonPhase {
		NewMoon,
		WaxingCrescent,
		FirstQuarter,
		WaxingGibbous,
		FullMoon,
		WaningGibbous,
		LastQuarter,
		WaningCrescent	
	} // end MoonPhase
	
	// Stores current conditions
	protected MoonPhase m_moonPhase = MoonPhase.NewMoon;
	protected Integer m_sunRise = 7;
	protected Integer m_sunSet = 20;
	protected Integer m_timeOfDay = 0;
	protected Condition m_weather = Condition.Unknown;
	
	/**
	 * Returns true if it is night time, false otherwise.
	 */
	public boolean IsNightTime() {
		if (m_timeOfDay < m_sunRise || m_timeOfDay >= m_sunSet)
			return true;
		else
			return false;
	} // end IsNightTime	
	
	/**
	 * Returns the current moon phase.
	 */
	public MoonPhase GetMoonPhase() {
		return m_moonPhase;
	} // end GetMoonPhase
	
	/**
	 * Returns the sun rise time.
	 */
	public Integer GetSunRise() {
		return m_sunRise;
	} // end m_SunRise
	
	/**
	 * Returns the sun set time.
	 */
	public Integer GetSunSet() {
		return m_sunSet;
	} // end GetSunSet
	
	/**
	 * Returns the current time of day.
	 */
	public Integer GetTimeOfDay() {
		return m_timeOfDay;
	} // end GetTimeOfDay
	
	/**
	 * Returns the current weather condition.
	 */
	public Condition GetWeather() {
		return m_weather;
	} // end GetWeather
		
} // end WeatherTime
