package havis.middleware.ale.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTypeAdapter {

	private static final Pattern MILLISECOND_PATTERN = Pattern
			.compile("(?<time>\\d{4}\\-\\d{2}\\-\\d{2}T\\d{2}:\\d{2}:\\d{2})([.,](?<ms>\\d+))?(?<timezone>[Z+]*.*)");

	private static ThreadLocal<SimpleDateFormat> format = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		}
	};

	public static Date parseDate(String string) {
		if (string == null)
			return null;
		try {
			return format.get().parse(parseMilliseconds(string));
		} catch (ParseException e) {
			return null;
		}
	}

	private static String parseMilliseconds(String string) {
		Matcher matcher = MILLISECOND_PATTERN.matcher(string);
		if (matcher.matches()) {
			String ms = matcher.group("ms");
			if (ms == null || ms.length() == 0)
				ms = "000";
			else if (ms.length() > 3)
				ms = ms.substring(0, 3);
			else if (ms.length() < 3)
				ms = ms.length() == 1 ? ms + "00" : ms + "0";
			return matcher.group("time") + "." + ms + matcher.group("timezone");
		}
		return string;
	}

	public static String printDate(Date date) {
		if (date == null)
			return null;
		return format.get().format(date);
	}
}