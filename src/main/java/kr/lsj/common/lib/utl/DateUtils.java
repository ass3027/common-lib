package kr.lsj.common.lib.utl;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.lib.utl
 * Class   name : DateUtil
 * Description  :
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-08-01
 * @author JINY
 * @version 1.0.0
 */
@SuppressWarnings({"CommentedOutCode", "GrazieInspection"})
public class DateUtils {

	private static final TimeZone timezone = TimeZone.getTimeZone("Asia/Seoul");


	public static Date setLastTime(Date now) {
		Calendar cal = Calendar.getInstance(timezone);
		cal.setTime(now);
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(year, mon, day, 23, 59, 59);

		return cal.getTime();
	}


//	public static String dateToString(Date date, String format) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//
//		return simpleDateFormat.format(date);
//	}
//
//
//	public static Date stringToDate(String dateString, String format) throws ParseException {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//
//		return simpleDateFormat.parse(dateString);
//	}
}
