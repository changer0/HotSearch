package com.tencent.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;

import com.qq.reader.baseui.R;
import com.qq.reader.core.BaseApplication;
import com.tencent.mars.xlog.Log;

public class TimeFormatterUtils {
	private static Calendar stampCal;
	private static StringBuffer dateStrBuf;
	private static final char timeSplit = '/';
	private static int timeOffset;

	public static void init() {
		TimeZone zone = TimeZone.getTimeZone("GMT+8");// TimeZone.getDefault();
		timeOffset = zone.getRawOffset();
		stampCal = Calendar.getInstance();
		dateStrBuf = new StringBuffer();
	}

	public static int getDateName(long timeStamp) {
		int strId = -1;
		long now = System.currentTimeMillis() + timeOffset;
		int nowdate = (int) (now / 86400000);
		int yesterday = nowdate - 1;
		int daybefoyes = nowdate - 2;
		int stampday = (int) ((timeStamp + timeOffset) / 86400000);
		if (stampday == nowdate) {
			strId = R.string.today;
		} else if (stampday == yesterday) {
			strId = R.string.yesterday;
		} else if (stampday == daybefoyes) {
			strId = R.string.before_yesterday;
		}
		return strId;
	}

	public static int getDateName(long timeStamp, Calendar calendar) {
		int strId = -1;
		long now = System.currentTimeMillis() + timeOffset;
		int nowdate = (int) (now / 86400000);
		int yesterday = nowdate - 1;
		int daybefoyes = nowdate - 2;
		int oneWeekAgo = nowdate - 7;
		int stampday = (int) ((timeStamp + timeOffset) / 86400000);
		if (stampday == nowdate) {
			strId = R.string.today;
		} else if (stampday == yesterday) {
			strId = R.string.yesterday;
		} else if (stampday < yesterday && stampday > oneWeekAgo) {
			if (calendar == null) {
				calendar = Calendar.getInstance();
				calendar.setTimeInMillis(timeStamp);
			}
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			switch (dayOfWeek) {
			case Calendar.MONDAY:
				strId = R.string.monday;
				break;
			case Calendar.TUESDAY:
				strId = R.string.tuesday;
				break;
			case Calendar.WEDNESDAY:
				strId = R.string.wednesday;
				break;
			case Calendar.THURSDAY:
				strId = R.string.thursday;
				break;
			case Calendar.FRIDAY:
				strId = R.string.friday;
				break;
			case Calendar.SATURDAY:
				strId = R.string.saturday;
				break;
			case Calendar.SUNDAY:
				strId = R.string.sunday;
				break;
			default:
				break;
			}
		}
		return strId;
	}

	public static final long ONE_DAY = 24 * 60 * 60 * 1000L;

	public static int getDateNameForRecentList(long timeStamp, Calendar calendar) {
		int strId = -1;

		Time then = new Time();
		then.set(timeStamp);
		long cur = System.currentTimeMillis();
		Time now = new Time();
		now.set(cur);

		int yesterday = now.yearDay - 1;
		int oneWeekBefore = now.yearDay - 7;

		if (then.year == now.year) {
			if (now.yearDay < then.yearDay) {
				return strId;
			} else if (now.yearDay == then.yearDay) {
				strId = R.string.today;
			} else if (then.yearDay == yesterday) {
				strId = R.string.yesterday;
			} else if (then.yearDay < yesterday && then.yearDay > oneWeekBefore) {
				switch (then.weekDay) {
				case Time.MONDAY:
					strId = R.string.monday;
					break;
				case Time.TUESDAY:
					strId = R.string.tuesday;
					break;
				case Time.WEDNESDAY:
					strId = R.string.wednesday;
					break;
				case Time.THURSDAY:
					strId = R.string.thursday;
					break;
				case Time.FRIDAY:
					strId = R.string.friday;
					break;
				case Time.SATURDAY:
					strId = R.string.saturday;
					break;
				case Time.SUNDAY:
					strId = R.string.sunday;
					break;
				default:
					break;
				}
			}
		} else if ((then.year + 1) == now.year) {
			// 濡傛灉鏄法骞寸殑锛屽嵆铡诲勾镄勶紝闇?瑕佽繘涓?姝ュ鐞嗗搱
			long day = (cur - timeStamp + ONE_DAY - 1) / (ONE_DAY);
			if (day > 0 && day <= 7) {
				if (day == 1) {
					strId = R.string.yesterday;
				} else {
					switch (then.weekDay) {
					case Time.MONDAY:
						strId = R.string.monday;
						break;
					case Time.TUESDAY:
						strId = R.string.tuesday;
						break;
					case Time.WEDNESDAY:
						strId = R.string.wednesday;
						break;
					case Time.THURSDAY:
						strId = R.string.thursday;
						break;
					case Time.FRIDAY:
						strId = R.string.friday;
						break;
					case Time.SATURDAY:
						strId = R.string.saturday;
						break;
					case Time.SUNDAY:
						strId = R.string.sunday;
						break;
					default:
						break;
					}
				}
			}
		}

		return strId;
	}

	public static String getRecentMessageDateTime(long timeStamp,
			boolean isShortTime, String formatStr) {
		return getRecentMessageDateTime(dateStrBuf, timeStamp, isShortTime,
				formatStr);
	}

	public static String getRecentMessageDateTime(StringBuffer srtBuf,
			long timeStamp, boolean isShortTime, String formatStr) {
		if (srtBuf != null) {
			srtBuf.setLength(0);
			Calendar localCalendar = Calendar.getInstance();
			localCalendar.setTimeInMillis(timeStamp);

			boolean formatTime = false;

			int strId = getDateNameForRecentList(timeStamp, localCalendar);
			if (strId != -1) {
				formatTime = true;
				if (strId != R.string.today)
					srtBuf.append(BaseApplication.Companion.getINSTANCE()
							.getApplicationContext().getString(strId));
			}
			int stampHour = localCalendar.get(Calendar.HOUR_OF_DAY);
			int stampMin = localCalendar.get(Calendar.MINUTE);
			if (formatTime) {
				boolean is24HourFormat = DateFormat
						.is24HourFormat(BaseApplication.Companion.getINSTANCE()
								.getApplicationContext());
				if (strId == R.string.today) {
					if (!is24HourFormat) {// 12灏忔椂鍒?
						if (stampHour >= 0 && stampHour < 12) {
							srtBuf.append(BaseApplication.Companion.getINSTANCE()
									.getApplicationContext()
									.getString(R.string.shangwu));
						} else {
							srtBuf.append(BaseApplication.Companion.getINSTANCE()
									.getApplicationContext()
									.getString(R.string.afternoon));
						}

						srtBuf.append(' ');
						int disHour = stampHour == 12 ? 12 : stampHour % 12;
						if (disHour < 10) {
							srtBuf.append('0');
						}
						srtBuf.append(disHour);
						srtBuf.append(':');
						if (stampMin < 10) {
							srtBuf.append('0');
						}
						srtBuf.append(stampMin);
					} else {// 24灏忔椂鍒?
						if (stampHour < 10) {
							srtBuf.append('0');
						}
						srtBuf.append(stampHour);
						srtBuf.append(':');
						if (stampMin < 10) {
							srtBuf.append('0');
						}
						srtBuf.append(stampMin);
					}
				} else if (!isShortTime) {
					srtBuf.append(' ');
					int disHour = stampHour;
					if (disHour < 10) {
						srtBuf.append('0');
					}
					srtBuf.append(disHour);
					srtBuf.append(':');
					if (stampMin < 10) {
						srtBuf.append('0');
					}
					srtBuf.append(stampMin);
				}
			} else {
				SimpleDateFormat dateFormat = null;
				if (TextUtils.isEmpty(formatStr)) {
					formatStr = "yyyy-MM-dd";
				}
				try {
					dateFormat = new SimpleDateFormat(formatStr);
				} catch (Exception e) {
					Log.printErrStackTrace("TimeFormatterUtils", e, null, null);
					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				}
				String dayString = dateFormat.format(localCalendar.getTime());
				srtBuf.append(dayString);
				if (!isShortTime) {
					srtBuf.append(' ');
					srtBuf.append(stampHour);
					srtBuf.append(':');
					if (stampMin < 10) {
						srtBuf.append('0');
					}
					srtBuf.append(stampMin);
				}
			}
			return srtBuf.toString();
		} else {
			return null;
		}
	}

	public static String getMessageDateTime(long timeStamp, boolean isShortTime) {
		dateStrBuf.setLength(0);
		stampCal.setTimeInMillis(timeStamp);

		// int nowdate = (int )(now/86400000);
		// //int nowdate = (int) (((float)now) / 86400000F+0.5);// 86400000 = 24
		// * 60 * 60 * 1000
		// int yesterday = nowdate - 1;
		// int daybefoyes = nowdate - 2;
		// int stampday = (int) ((timeStamp+timeOffset) /86400000);
		// int stampday = (int) (((float)timeStamp) / 86400000F+0.5);

		boolean formatTime = false;
		// if (stampday == nowdate) {
		// formatTime = true;
		// //
		// dateStrBuf.append(QQApplication.getContext().getString(R.string.today));//shawn闇?姹傚幓闄や粖澶?
		// } else if (stampday == yesterday) {
		// formatTime = true;
		// dateStrBuf.append(BaseApplication.getContext().getString(R.string.yesterday));
		// } else if (stampday == daybefoyes) {
		// formatTime = true;
		// dateStrBuf.append(BaseApplication.getContext().getString(R.string.before_yesterday));
		// }
		int strId = getDateName(timeStamp);
		if (strId != -1) {
			formatTime = true;
			if (strId != R.string.today)
				dateStrBuf.append(BaseApplication.Companion.getINSTANCE()
						.getApplicationContext().getString(strId));
		}

		int stampHour = stampCal.get(Calendar.HOUR_OF_DAY);
		int stampMin = stampCal.get(Calendar.MINUTE);
		if (formatTime) {
			// 鏄ㄥぉ涓婂崃锛屾槰澶╀笅鍗堢粺涓?鏀规垚锛氭槰澶?
			// 鍓嶅ぉ涓婂崃锛屽墠澶╀笅鍗堢粺涓?鏀规垚锛氩墠澶?
			if (strId == R.string.today) {
				// if (stampHour >= 0 && stampHour < 6) {
				// dateStrBuf.append(BaseApplication.getContext().getString(R.string.daybreak));
				// } else if (stampHour >= 6 && stampHour < 9) {
				// dateStrBuf.append(BaseApplication.getContext().getString(R.string.morning));
				// } else if (stampHour >= 9 && stampHour < 12) {
				// dateStrBuf.append(BaseApplication.getContext().getString(R.string.shangwu));
				// } else if (stampHour >= 12 && stampHour < 18) {
				// dateStrBuf.append(BaseApplication.getContext().getString(R.string.afternoon));
				// } else if (stampHour >= 18 && stampHour < 24) {
				// dateStrBuf.append(BaseApplication.getContext().getString(R.string.evening));
				// }

				if (stampHour >= 0 && stampHour < 12) {
					dateStrBuf.append(BaseApplication.Companion.getINSTANCE()
							.getApplicationContext()
							.getString(R.string.shangwu));
				} else {
					dateStrBuf.append(BaseApplication.Companion.getINSTANCE()
							.getApplicationContext()
							.getString(R.string.afternoon));
				}

				dateStrBuf.append(' ');
				int disHour = stampHour == 12 ? 12 : stampHour % 12;
				if (disHour < 10) {
					dateStrBuf.append('0');
				}
				dateStrBuf.append(disHour);
				dateStrBuf.append(':');
				if (stampMin < 10) {
					dateStrBuf.append('0');
				}
				dateStrBuf.append(stampMin);

			} else if (!isShortTime) {
				dateStrBuf.append(' ');
				int disHour = stampHour;
				if (disHour < 10) {
					dateStrBuf.append('0');
				}
				dateStrBuf.append(disHour);
				dateStrBuf.append(':');
				if (stampMin < 10) {
					dateStrBuf.append('0');
				}
				dateStrBuf.append(stampMin);
			}
		} else {
			// dateStrBuf.append(stampCal.get(Calendar.MONTH) +
			// 1).append(BaseApplication.getContext().getString(R.string.month))
			// .append(stampCal.get(Calendar.DAY_OF_MONTH)).append(BaseApplication.getContext().getString(R.string.day))
			// .append(' ');
			dateStrBuf.append(stampCal.get(Calendar.YEAR)).append(timeSplit)
					.append(stampCal.get(Calendar.MONTH) + 1).append(timeSplit)
					.append(stampCal.get(Calendar.DAY_OF_MONTH));
			if (!isShortTime) {
				dateStrBuf.append(' ');
				dateStrBuf.append(stampHour);
				dateStrBuf.append(':');
				if (stampMin < 10) {
					dateStrBuf.append('0');
				}
				dateStrBuf.append(stampMin);
			}
		}
		return dateStrBuf.toString();
	}

	/**
	 * 榛樿镙煎纺 绯荤粺榛樿Date +Time ,浣跨敤绯荤粺镄凩ocalizedPattern
	 */
	public static final int FORMAT_DEFAULT = 0;
	/**
	 * 鍖呭惈鏄ㄥぉ
	 */
	public static final int FORMAT_SHOW_YESTERDAY = 0x00001;
	/**
	 * 鍖呭惈WeekDay镙煎纺(涓?锻ㄥ唴鏄剧ず鏄熸湡鍑?)
	 */
	public static final int FORMAT_SHOW_WEEKDAY = 0x00002;
	/**
	 * 鍚屾椂鏀寔鏄ㄥぉ鍜学eekday 锛团ORMAT_SHOW_YESTERDAY|FORMAT_SHOW_WEEKDAY锛?
	 */
	public static final int FORMAT_SHOW_YESTERDAY_WEEKDAY = 0x00003;

	// FIXME 涓嶆敮鎸佸浗闄呭寲
	// private static final String STR_YESTERDAY="鏄ㄥぉ";
	private static final String STR_WEEKDAY = "EEEE";
	private static final int INTEVEL_YESTERDAY = 1;
	private static final int INTEVEL_WEEKDAY = 7;

	/***
	 * 镙煎纺鍖栨棩链?+镞堕棿
	 * 
	 * @param context
	 * @param dateType
	 *            闇?瑕佸悓镞舵敮鎸丗ORMAT_SHOW_YESTERDAY 鍜? FORMAT_SHOW_WEEKDAY 锛岄?氲绷
	 *            FORMAT_SHOW_YESTERDAY|FORMAT_SHOW_WEEKDAY
	 * @param when
	 * @return
	 */
	public static CharSequence formatDateTime(Context context, int dateType,
			long when) {
		return formatDateTime(context, dateType, when, true);
	}

	/**
	 * 镙煎纺鍖栨棩链?+镞堕棿
	 * 
	 * @param context
	 * @param dateType
	 *            闇?瑕佸悓镞舵敮鎸丗ORMAT_SHOW_YESTERDAY 鍜? FORMAT_SHOW_WEEKDAY 锛岄?氲绷
	 *            FORMAT_SHOW_YESTERDAY|FORMAT_SHOW_WEEKDAY
	 * @param when
	 * @param forceFull
	 *            寮哄埗杈揿嚭FULL锛图ate +Time锛? 镙煎纺
	 * @return
	 */
	public static CharSequence formatDateTime(Context context, int dateType,
			long when, boolean forceFull) {
		StringBuilder formatSB = new StringBuilder();
		java.text.SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
				.getDateFormat(context);
		if (dateType == FORMAT_DEFAULT) {
			formatSB.append(dateFormat.toLocalizedPattern());
			return DateFormat.format(formatSB.append(" ").toString(), when)
					+ DateFormat.getTimeFormat(context).format(when);
		} else {
			return getDateTimeFormatStr(context, when, formatSB, dateFormat,
					dateType, forceFull);
		}
	}

	/**
	 * 杩斿洖镞ユ湡+镞堕棿镄勭殑format镙煎纺涓?
	 * 
	 * @param when
	 * @param format
	 * @param dateFormat
	 * @param dateType
	 *            闇?瑕佸悓镞舵敮鎸丗ORMAT_SHOW_YESTERDAY 鍜? FORMAT_SHOW_WEEKDAY 锛岄?氲绷
	 *            FORMAT_SHOW_YESTERDAY|FORMAT_SHOW_WEEKDAY
	 * @param forceFull
	 *            寮哄埗杈揿嚭FULL锛图ate +Time锛? 镙煎纺
	 * @return
	 */
	private static CharSequence getDateTimeFormatStr(Context context,
			long when, StringBuilder formatSB,
			java.text.SimpleDateFormat dateFormat, int dateType,
			boolean forceFull) {
		Time then = new Time();
		then.set(when);
		Time now = new Time();
		now.setToNow();
		boolean needTime = true;

		boolean showWeekDay = (dateType & FORMAT_SHOW_WEEKDAY) != 0;
		boolean showYesterday = (dateType & FORMAT_SHOW_YESTERDAY) != 0;
		// If the message is from a different year, show the date and year.
		if (then.year != now.year) {
			formatSB.append(dateFormat.toLocalizedPattern()).append(" ");
			needTime = false;
		} else if (then.yearDay != now.yearDay)// not today
		{
			int days = Math.abs(now.yearDay - then.yearDay);
			boolean past = (now.yearDay > then.yearDay);
			if (!past) {
				formatSB.append(dateFormat.toLocalizedPattern()).append(" ");
				needTime = false;
			} else if (days == INTEVEL_YESTERDAY && showYesterday) {
				formatSB.append(context.getString(R.string.aio_yesterday))
						.append(" ");
				needTime = false;
				// 鏄ㄥぉ鐗规畩澶勭悊锛岄伩鍏峐esterday 瀛楃涓插湪dateFormat涓殑鎸夊瓧姣嶈璁や綔鏄椂闂存牸寮?
				if (!forceFull) {
					return formatSB.toString().trim();
				} else {
					return formatSB.toString()
							+ DateFormat.getTimeFormat(context).format(when);
				}
			} else if (days > INTEVEL_YESTERDAY && days < INTEVEL_WEEKDAY
					&& showWeekDay) {
				formatSB.append(STR_WEEKDAY).append(" ");
				needTime = false;
			} else if (then.year == now.year) {
				formatSB.append("MM-dd").append(" ");
				needTime = false;
			} else {// use defaut DATE TIME
				formatSB.append(dateFormat.toLocalizedPattern()).append(" ");
				needTime = false;
			}
		} else {
			// today ,no need to show date;
		}
		if (!needTime && !forceFull) {
			return DateFormat.format(formatSB.toString().trim(), when);
		}
		return DateFormat.format(formatSB.toString(), when)
				+ DateFormat.getTimeFormat(context).format(when);
	}

	/**
	 * 镙煎纺鍖栵细XX链圶X镞?
	 * 
	 * @param context
	 * @param when
	 * @return
	 */
	public static CharSequence formatDateTimeMonthly(Context context, long when) {
		// TODO:锲介台鍖?
		stampCal.setTimeInMillis(when);
		int nowMonth = stampCal.get(Calendar.MONTH) + 1;
		int nowDay = stampCal.get(Calendar.DATE);
		return String.format("%s%s%s%s", nowMonth,
				context.getString(R.string.month), nowDay,
				context.getString(R.string.day));
	}

	/**
	 * 镙煎纺鍖栨椂闂?
	 * 
	 * @param context
	 * @param when
	 * @return
	 */
	public static CharSequence formatTime(Context context, long when) {
		return DateFormat.getTimeFormat(context).format(new Date(when));
	}

	public static String formatRefreshTime(Context context, long timeStamp) {
		stampCal.setTimeInMillis(System.currentTimeMillis());
		int nowYear = stampCal.get(Calendar.YEAR);
		int nowMonth = stampCal.get(Calendar.MONTH);
		int nowDay = stampCal.get(Calendar.DATE);
		stampCal.setTimeInMillis(timeStamp);
		int targetYear = stampCal.get(Calendar.YEAR);
		int targetMonth = stampCal.get(Calendar.MONTH);
		int targetDay = stampCal.get(Calendar.DATE);
		int dayDiff = calDayDiff(nowYear, nowMonth, nowDay, targetYear,
				targetMonth, targetDay);
		if (dayDiff == 0) {
			int timeFormat12 = 0;
			try {
				timeFormat12 = Settings.System.getInt(
						context.getContentResolver(),
						Settings.System.TIME_12_24);
			} catch (SettingNotFoundException e) {
				Log.printErrStackTrace("TimeFormatterUtils", e, null, null);
				// System.out.println("error:"+e.toString());
			}
			if (timeFormat12 == 12) {
				int prefix = stampCal.get(Calendar.HOUR_OF_DAY) < 12 ? R.string.shangwu
						: R.string.afternoon;
				String timePrefix = context.getString(prefix);
				SimpleDateFormat format = new SimpleDateFormat("hh:mm", context
						.getResources().getConfiguration().locale);
				return String.format("%s %s", timePrefix,
						format.format(new Date(timeStamp)));
			} else {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm", context
						.getResources().getConfiguration().locale);
				return format.format(new Date(timeStamp));
			}
		} else if (dayDiff == 1) {
			return context.getString(R.string.yesterday);
		} else if (dayDiff < 7 && dayDiff > 1) {
			SimpleDateFormat format = new SimpleDateFormat("E", context
					.getResources().getConfiguration().locale);
			return format.format(new Date(timeStamp));
		} else {
			return android.text.format.DateFormat.getDateFormat(context)
					.format(new Date(timeStamp));
		}
	}

	public static String formatBackupTime(Context context, long timeStamp) {
		stampCal.setTimeInMillis(System.currentTimeMillis());
		int nowYear = stampCal.get(Calendar.YEAR);
		int nowMonth = stampCal.get(Calendar.MONTH);
		int nowDay = stampCal.get(Calendar.DATE);
		stampCal.setTimeInMillis(timeStamp);
		int targetYear = stampCal.get(Calendar.YEAR);
		int targetMonth = stampCal.get(Calendar.MONTH);
		int targetDay = stampCal.get(Calendar.DATE);
		int dayDiff = calDayDiff(nowYear, nowMonth, nowDay, targetYear,
				targetMonth, targetDay);
		if (dayDiff == 0) {
			int timeFormat12 = 0;
			try {
				timeFormat12 = Settings.System.getInt(
						context.getContentResolver(),
						Settings.System.TIME_12_24);
			} catch (SettingNotFoundException e) {
				Log.printErrStackTrace("TimeFormatterUtils", e, null, null);
				// System.out.println("error:"+e.toString());
			}
			/* 鍦ㄦ椂闂村墠闱㈡坊锷犫?滀粖澶┾?濇爣璇? */
			if (timeFormat12 == 12) {
				int prefix = stampCal.get(Calendar.HOUR_OF_DAY) < 12 ? R.string.shangwu
						: R.string.afternoon;
				String timePrefix = context.getString(prefix);
				SimpleDateFormat format = new SimpleDateFormat("hh:mm", context
						.getResources().getConfiguration().locale);
				return context.getString(R.string.today)
						+ " "
						+ String.format("%s %s", timePrefix,
								format.format(new Date(timeStamp)));
			} else {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm", context
						.getResources().getConfiguration().locale);
				return context.getString(R.string.today) + " "
						+ format.format(new Date(timeStamp));
			}
		} else if (dayDiff == 1) {
			int timeFormat12 = 0;
			try {
				timeFormat12 = Settings.System.getInt(
						context.getContentResolver(),
						Settings.System.TIME_12_24);
			} catch (SettingNotFoundException e) {
				Log.printErrStackTrace("TimeFormatterUtils", e, null, null);
				// System.out.println("error:"+e.toString());
			}
			if (timeFormat12 == 12) {
				int prefix = stampCal.get(Calendar.HOUR_OF_DAY) < 12 ? R.string.shangwu
						: R.string.afternoon;
				String timePrefix = context.getString(prefix);
				SimpleDateFormat format = new SimpleDateFormat("hh:mm", context
						.getResources().getConfiguration().locale);
				return context.getString(R.string.yesterday)
						+ " "
						+ String.format("%s %s", timePrefix,
								format.format(new Date(timeStamp)));
			} else {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm", context
						.getResources().getConfiguration().locale);
				return context.getString(R.string.yesterday) + " "
						+ format.format(new Date(timeStamp));
			}
		} else if (dayDiff < 7 && dayDiff > 1) {
			SimpleDateFormat format = new SimpleDateFormat("E", context
					.getResources().getConfiguration().locale);
			return format.format(new Date(timeStamp));
		} else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
					context.getResources().getConfiguration().locale);
			return format.format(new Date(timeStamp));
		}
	}

	private static int calDayDiff(int lhsYear, int lhsMonth, int lhsDay,
			int rhsYear, int rhsMonth, int rhsDay) {
		GregorianCalendar lhs = new GregorianCalendar(lhsYear, lhsMonth, lhsDay);
		GregorianCalendar rhs = new GregorianCalendar(rhsYear, rhsMonth, rhsDay);
		return (int) ((lhs.getTimeInMillis() - rhs.getTimeInMillis()) / 1000 / 60 / 60 / 24);
	}

	/**
	 * 鏂版坊锷犵殑镟存柊镞堕棿璁＄畻鏂规硶 2013/5/30 瑕佹眰鏄剧ず镞堕棿镄勫洓绉嶆牸寮忎负濡备笅锛? 浠婂ぉ锛?09:44锛?
	 * 鏄ㄥぉ锛氭槰澶? 09:44锛? 涓?骞村唴锛?05-12 09:44 涓?骞村锛?13-05-21 09:44
	 * 
	 * @param context
	 * @param timeStamp
	 * @return
	 */
	public static String formatNewRefreshTime(Context context, long timeStamp) {
		stampCal.setTimeInMillis(System.currentTimeMillis());
		int nowYear = stampCal.get(Calendar.YEAR);
		int nowMonth = stampCal.get(Calendar.MONTH);
		int nowDayOfYear = stampCal.get(Calendar.DAY_OF_YEAR);
		stampCal.setTimeInMillis(timeStamp);
		int targetYear = stampCal.get(Calendar.YEAR);
		int targetMonth = stampCal.get(Calendar.MONTH);
		int targetDayOfYear = stampCal.get(Calendar.DAY_OF_YEAR);
		TimeInterval interval = calTimeInterval(nowYear, nowMonth,
				nowDayOfYear, targetYear, targetMonth, targetDayOfYear);
		SimpleDateFormat format = null;
		switch (interval) {
		case TODAY:
			format = new SimpleDateFormat("HH:mm", context.getResources()
					.getConfiguration().locale);
			return format.format(new Date(timeStamp));
		case YESTERDAY:
			format = new SimpleDateFormat("HH:mm", context.getResources()
					.getConfiguration().locale);
			return String.format("%s %s", "鏄ㄥぉ",
					format.format(new Date(timeStamp)));
		case THE_DAY_BEFORE_YESTERDAY:
			format = new SimpleDateFormat("HH:mm", context.getResources()
					.getConfiguration().locale);
			return String.format("%s %s", "鍓嶅ぉ",
					format.format(new Date(timeStamp)));
		case WITHIN_YEAR:
			format = new SimpleDateFormat("MM-dd HH:mm", context.getResources()
					.getConfiguration().locale);
			return format.format(new Date(timeStamp));
		case WITHOUT_YEAR:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm", context
					.getResources().getConfiguration().locale);
			return format.format(new Date(timeStamp));
		}
		return null;
	}

	public static enum TimeInterval {
		TODAY, // 鍚屼竴澶?
		YESTERDAY, // 鏄ㄥぉ
		THE_DAY_BEFORE_YESTERDAY, // 鍓嶅ぉ
		WITHIN_YEAR, // 浠婂勾鍐?
		WITHOUT_YEAR // 浠婂勾澶?
	}

	/**
	 * 杩斿洖镙煎纺锛氢粖澶╋细00:00锛涙槰澶╋细鏄ㄥぉ锛涘墠澶╋细鍓嶅ぉ锛涙湰锻ㄦ洿镞╋细鏄熸湡*锛涙湰骞存洿镞╋细MM-dd锛涘幓骞翠互鍓嶏细yy
	 * -MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDatetime(long time) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(time);
		calendar.setTime(date);
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTimeInMillis(System.currentTimeMillis());
		if (calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)
				&& calendar.get(Calendar.DAY_OF_YEAR) == nowCalendar
						.get(Calendar.DAY_OF_YEAR)) {
			return "浠婂ぉ";
		} else if (calendar.get(Calendar.YEAR) == nowCalendar
				.get(Calendar.YEAR)
				&& calendar.get(Calendar.DAY_OF_YEAR) + 1 == nowCalendar
						.get(Calendar.DAY_OF_YEAR)) {
			return "鏄ㄥぉ";
		} else if (calendar.get(Calendar.YEAR) == nowCalendar
				.get(Calendar.YEAR)
				&& calendar.get(Calendar.DAY_OF_YEAR) > nowCalendar
						.get(Calendar.DAY_OF_YEAR) - 7) {
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case 1:
				return "鏄熸湡镞?";
			case 2:
				return "鏄熸湡涓?";
			case 3:
				return "鏄熸湡浜?";
			case 4:
				return "鏄熸湡涓?";
			case 5:
				return "鏄熸湡锲?";
			case 6:
				return "鏄熸湡浜?";
			case 7:
				return "鏄熸湡鍏?";
			default:
				break;
			}
		} else if (calendar.get(Calendar.YEAR) == nowCalendar
				.get(Calendar.YEAR)) {
			return new SimpleDateFormat("MM-dd").format(date);
		}
		return new SimpleDateFormat("yy-MM-dd").format(date);
	}

	private static TimeInterval calTimeInterval(int lhsYear, int lhsMonth,
			int lhsDayOfYear, int rhsYear, int rhsMonth, int rhsDayOfYear) {
		if (lhsYear != rhsYear) {
			return TimeInterval.WITHOUT_YEAR;
		} else if (lhsDayOfYear == rhsDayOfYear) {
			return TimeInterval.TODAY;
		} else if (lhsDayOfYear == rhsDayOfYear + 1) {
			return TimeInterval.YESTERDAY;
		} else if (lhsDayOfYear == rhsDayOfYear + 2) {
			return TimeInterval.THE_DAY_BEFORE_YESTERDAY;
		} else {
			return TimeInterval.WITHIN_YEAR;
		}
	}

	public static String getFormatTime(long time, String format) {
		if (time <= 0) {
			return null;
		}
		Date data = new Date(time);
		SimpleDateFormat dateformat1 = new SimpleDateFormat(format);
		return dateformat1.format(data);
	}

	public static TimeInterval calTimeInterval(long timeStamp) {

		stampCal.setTimeInMillis(System.currentTimeMillis());
		int nowYear = stampCal.get(Calendar.YEAR);
		int nowMonth = stampCal.get(Calendar.MONTH);
		int nowDayOfYear = stampCal.get(Calendar.DAY_OF_YEAR);
		stampCal.setTimeInMillis(timeStamp);
		int targetYear = stampCal.get(Calendar.YEAR);
		int targetMonth = stampCal.get(Calendar.MONTH);
		int targetDayOfYear = stampCal.get(Calendar.DAY_OF_YEAR);

		return calTimeInterval(nowYear, nowMonth, nowDayOfYear, targetYear,
				targetMonth, targetDayOfYear);
	}
}
