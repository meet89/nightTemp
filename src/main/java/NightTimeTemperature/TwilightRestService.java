package NightTimeTemperature;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TwilightRestService {

	public long compareTimes(ResultsOutput request) {
		long TS = formatMillisecondsToMinutes(formatDates(request.getResults().getCivil_twilight_begin()));
		long TE = formatMillisecondsToMinutes(formatDates(request.getResults().getCivil_twilight_end()));
		long SR = formatMillisecondsToMinutes(formatDates(request.getResults().getSunrise()));
		long SS = formatMillisecondsToMinutes(formatDates(request.getResults().getSunset()));
		

		long localTime = formatMillisecondsToMinutes(formatDates(getLocalTime()));

		long temperature = 0;
		if (localTime > TS && localTime < (2 * SR - TS)) {
			// linearIncreaseInTemperate();
			temperature = findTempForRISE(localTime, TS, SR);

		} else if ((2 * SS - TE) > localTime && localTime > (2 * SR - TS)) {
			temperature = 6000;
		} else if ((2 * SS - TE) <= localTime && localTime < TE) {
			// linearInreaseinTemperature
			temperature = findTempForDAWN(localTime, TE, SS);
		} else if (localTime <= TS || TE <= localTime) {
			temperature = 2700;
		}

		return temperature;

	}
	
	public String getLocalTime()
	{
		
		LocalTime localTime1 = LocalTime.now();

		int hour = localTime1.getHour();
		int min = localTime1.getMinute();
		int sec = localTime1.getSecond();
		String dayNight = null;
		if (hour > 12) {
			dayNight = "PM";
			hour = hour - 12;

		} else if (hour == 12) {
			dayNight = "PM";

		} else {
			dayNight = "AM";
		}
		return hour+":"+min+":"+sec+" "+dayNight;
	}

	public long formatMillisecondsToMinutes(long time) {
		long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
		return minutes;

	}

	public long formatDates(String time) {
		SimpleDateFormat format = new SimpleDateFormat("h:mm:ss a");
		Date formattedTime = null;
		try {

			formattedTime = format.parse(time.toString());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedTime.getTime();

	}

	public long calculateTimeDifference(Date startTime, Date endTime) {

		long difference = 2 * startTime.getTime() - startTime.getTime();
		return difference;

	}

	public double calculateIncreaseInTempForEachMinuteForDAWN(long TE, long SS) {

		long timeDifference = TE - SS;
		long tempDifference = 6000 - 2700;

		double perMinuteRaise = tempDifference / (2 * timeDifference) * 1.0;
		return perMinuteRaise;
	}

	public double calculateIncreaseInTempForEachMinuteForRISE(long TS, long SR) {

		long timeDifference = SR - TS;
		long tempDifference = 6000 - 2700;

		double perMinuteRaise = tempDifference / (2 * timeDifference) * 1.0;
		return perMinuteRaise;
	}

	public long findTempForDAWN(long localTime, long TE, long SS) {
		long temp = (long) (2700 + (TE - localTime) * calculateIncreaseInTempForEachMinuteForDAWN(TE, SS));

		return temp;
	}

	public long findTempForRISE(long localTime, long TS, long SR) {
		long temp = (long) (2700 + (localTime - TS) * calculateIncreaseInTempForEachMinuteForRISE(TS, SR));

		return temp;
	}

}
