package com.xmalloc.javatool.time;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * copy from org.apache.tika.utils.DateUtils; and
 * org.apache.tika.metadata.Metadata;
 * 
 * @author zhch
 *
 */
public class DateTool {
	/**
	 * The UTC time zone. Not sure if {@link TimeZone#getTimeZone(String)}
	 * understands "UTC" in all environments, but it'll fall back to GMT in such
	 * cases, which is in practice equivalent to UTC.
	 */
	public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

	/**
	 * Custom time zone used to interpret date values without a time component
	 * in a way that most likely falls within the same day regardless of in
	 * which time zone it is later interpreted. For example, the "2012-02-17"
	 * date would map to "2012-02-17T12:00:00Z" (instead of the default
	 * "2012-02-17T00:00:00Z"), which would still map to "2012-02-17" if
	 * interpreted in say Pacific time (while the default mapping would result
	 * in "2012-02-16" for UTC-8).
	 */
	public static final TimeZone MIDDAY = TimeZone.getTimeZone("GMT-12:00");

	/**
	 * Returns a ISO 8601 representation of the given date. This method is
	 * thread safe and non-blocking.
	 *
	 * @see <a href="https://issues.apache.org/jira/browse/TIKA-495">TIKA-495
	 *      </a>
	 * @param date given date
	 * @return ISO 8601 date string, including timezone details
	 */
	public static String formatDate(Date date) {
		Calendar calendar = GregorianCalendar.getInstance(UTC, Locale.US);
		calendar.setTime(date);
		return doFormatDate(calendar);
	}

	/**
	 * Returns a ISO 8601 representation of the given date. This method is
	 * thread safe and non-blocking.
	 *
	 * @see <a href="https://issues.apache.org/jira/browse/TIKA-495">TIKA-495
	 *      </a>
	 * @param date given date
	 * @return ISO 8601 date string, including timezone details
	 */
	public static String formatDate(Calendar date) {
		// Explicitly switch it into UTC before formatting 
		date.setTimeZone(UTC);
		return doFormatDate(date);
	}

	/**
	 * Returns a ISO 8601 representation of the given date, which is in an
	 * unknown timezone. This method is thread safe and non-blocking.
	 *
	 * @see <a href="https://issues.apache.org/jira/browse/TIKA-495">TIKA-495
	 *      </a>
	 * @param date given date
	 * @return ISO 8601 date string, without timezone details
	 */
	public static String formatDateUnknownTimezone(Date date) {
		// Create the Calendar object in the system timezone
		Calendar calendar = GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.US);
		calendar.setTime(date);
		// Have it formatted
		String formatted = formatDate(calendar);
		// Strip the timezone details before returning
		return formatted.substring(0, formatted.length() - 1);
	}

	private static String doFormatDate(Calendar calendar) {
		return String.format(Locale.ROOT, "%04d-%02d-%02dT%02d:%02d:%02dZ", calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	}

	/**
	 * Some parsers will have the date as a ISO-8601 string already, and will
	 * set that into the Metadata object. So we can return Date objects for
	 * these, this is the list (in preference order) of the various ISO-8601
	 * variants that we try when processing a date based property.
	 */
	private static final DateFormat[] iso8601InputFormats = new DateFormat[] {
			// yyyy-mm-ddThh...
			createDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", UTC), // UTC/Zulu
			createDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", null), // With timezone
			createDateFormat("yyyy-MM-dd'T'HH:mm:ss", null), // Without timezone
			// yyyy-mm-dd hh...
			createDateFormat("yyyy-MM-dd' 'HH:mm:ss'Z'", UTC), // UTC/Zulu
			createDateFormat("yyyy-MM-dd' 'HH:mm:ssZ", null), // With timezone
			createDateFormat("yyyy-MM-dd' 'HH:mm:ss", null), // Without timezone
			// Date without time, set to Midday UTC
			createDateFormat("yyyy-MM-dd", MIDDAY), // Normal date format
			createDateFormat("yyyy:MM:dd", MIDDAY), // Image (IPTC/EXIF) format
	};

	private static DateFormat createDateFormat(String format, TimeZone timezone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, new DateFormatSymbols(Locale.US));
		if (timezone != null) {
			sdf.setTimeZone(timezone);
		}
		return sdf;
	}

	/**
	 * Parses the given date string. This method is synchronized to prevent
	 * concurrent access to the thread-unsafe date formats.
	 *
	 * @see <a href="https://issues.apache.org/jira/browse/TIKA-495">TIKA-495
	 *      </a>
	 * @param date date string
	 * @return parsed date, or <code>null</code> if the date can't be parsed
	 */
	private static synchronized Date parseDate(String date) {
		// Java doesn't like timezones in the form ss+hh:mm
		// It only likes the hhmm form, without the colon
		int n = date.length();
		if (date.charAt(n - 3) == ':' && (date.charAt(n - 6) == '+' || date.charAt(n - 6) == '-')) {
			date = date.substring(0, n - 3) + date.substring(n - 2);
		}

		// Try several different ISO-8601 variants
		for (DateFormat format : iso8601InputFormats) {
			try {
				return format.parse(date);
			} catch (ParseException ignore) {
			}
		}
		return null;
	}
}
