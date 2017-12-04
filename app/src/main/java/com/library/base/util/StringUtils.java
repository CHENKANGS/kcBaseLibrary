package com.library.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * 
 * @author allen
 * @version 1.0
 * @created 2014-4-16
 */
@SuppressLint("SimpleDateFormat")
public class StringUtils {



	/**
	 * picasso中获取图片url，若没有，将其返回为error
	 *
	 * @param url
	 * @return
	 */
//	public static String getImgUrl(String url) {
//		if (isEmpty(url)) {
//			return "error_img";
//		} else
//			return Urls.IMAGE_01 + url.trim();
//	}


	public static Pattern shuzi = Pattern.compile("[0-9]*");
	public static Pattern zimu = Pattern.compile("[a-zA-Z]*");
	public static Pattern hanzi = Pattern.compile("[\u4e00-\u9fa5]*");
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	// 只能是数字,英文字母和中文
	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(Date sdate) {
		Date time = sdate;
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	public static boolean isNoEmpty(String input) {
		if (input == null || "".equals(input)){

			return false;
		}

		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 字符串数组转List
	 * 
	 * @param data
	 * @return
	 */
	public static List<String> stringToList(String[] data) {
		if (data == null || data.length == 0) {
			return null;
		}
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			res.add(data[i]);
		}

		return res;
	}

	public static String getIMEI(Context context) {
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * 判别手机是否为正确手机号码； 号码段分配如下：
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
	 */
	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";

		String expression2 = "^\\(?(\\d{2})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";

		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		Pattern pattern2 = Pattern.compile(expression2);

		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumberValid2(String phoneNumber) {
		boolean isValid = false;

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

	/**
	 * 把毫秒转化为制定格式的时间
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String getTime(long time, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 把毫秒转化为制定格式的时间
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String getTime(String time, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();

		try {
			calendar.setTimeInMillis(Long.valueOf(time));
			return formatter.format(calendar.getTime());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 时间转化毫秒
	 * 
	 * @param str
	 * @return
	 */
	public static long getMillisecond(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = null;
		try {
			d = sdf.parse(str);
			return d.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/** 字符串是否为空或者为"" */
	public static boolean isStringNone(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str.trim())) {
			return true;
		}

		return false;
	}

	// 格式：2014-05-29 08:00:00
	public static String formetDateTime(int year, int mounth, int day, int hour, int minute) {
		StringBuffer sb = new StringBuffer();
		sb.append(year + "-");
		if (mounth < 10) {
			sb.append("0" + mounth + "-");
		} else {
			sb.append(mounth + "-");
		}

		if (day < 10) {
			sb.append("0" + day + " ");
		} else {
			sb.append(day + " ");
		}

		if (hour < 10) {
			sb.append("0" + hour + ":");
		} else {
			sb.append(hour + ":");
		}

		if (minute < 10) {
			sb.append("0" + hour + ":00");
		} else {
			sb.append(hour + ":00");
		}
		return sb.toString();

	}

	/**
	 * 将null类型的String改成""
	 * @param str
	 * @return String
	 */
	public static String setTextWithOutNull(String str) {
		if(str == null){
			str = "";
		}
		return  str;
	}

	/**
	 * 是否需要加载更多：若限制页面长度大于当前页面长度，说明不再需要加载更多了
	 *
	 * @param listSize
	 * @param pageSize
	 * @return
	 */
	public static boolean isNeedLoadMore(int listSize, int pageSize) {
		if (listSize >0 && listSize % pageSize == 0)
			return true;
		else
			return false;
	}

	/**
	 * 返回当次请求的page号,默认从1开始
	 *
	 * @param listSize
	 * @param pageSize
	 * @return
	 */
	public static int getPageNum(int listSize, int pageSize) {
		int num;
		if (listSize == 0) {
			num = 1;
			return num;
		}
		if (listSize >= pageSize) {
			if (listSize % pageSize == 0) {
				num = listSize / pageSize + 1;
			} else {
				num = listSize / pageSize + 2;
			}
		} else {
			num = 2;
		}
		return num;
	}

	/**
	 * byte[]数组转换为16进制的字符串
	 *
	 * @param data 要转换的字节数组
	 * @return 转换后的结果
	 */
	public static final String byteArrayToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (byte b : data) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase(Locale.getDefault());
	}

	/**
	 * 16进制表示的字符串转换为字节数组
	 *
	 * @param s 16进制表示的字符串
	 * @return byte[] 字节数组
	 */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] d = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return d;
	}

	/**
	 * 将给定的字符串中所有给定的关键字标红
	 *
	 * @param sourceString 给定的字符串
	 * @param keyword      给定的关键字
	 * @return 返回的是带Html标签的字符串，在使用时要通过Html.fromHtml()转换为Spanned对象再传递给TextView对象
	 */
	public static String keywordMadeRed(String sourceString, String keyword) {
		String result = "";
		if (sourceString != null && !"".equals(sourceString.trim())) {
			if (keyword != null && !"".equals(keyword.trim())) {
				result = sourceString.replaceAll(keyword,
						"<font color=\"red\">" + keyword + "</font>");
			} else {
				result = sourceString;
			}
		}
		return result;
	}

	/**
	 * 为给定的字符串添加HTML红色标记，当使用Html.fromHtml()方式显示到TextView 的时候其将是红色的
	 *
	 * @param string 给定的字符串
	 * @return
	 */
	public static String addHtmlRedFlag(String string) {
		return "<font color=\"red\">" + string + "</font>";
	}


	/**
	 * 验证手机号
	 *
	 * @param mobiles
	 * @return
	 */
//	public static boolean isMobileNO(String mobiles) {
////        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//		Matcher m = p.matcher(mobiles);
//		return m.matches();
//	}
	/**
	 * 验证手机号码
	 * @param phoneStr
	 * @return
	 */
	public static String checkPhone(String phoneStr) {
		String resultStr = "0";// 返回
		// 进行正则匹配是否密码格式
		Pattern p = Pattern
				.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher m = p.matcher(phoneStr);
		if (null == phoneStr.trim() || "".equals(phoneStr.trim())
				|| "请输入手机号".equals(phoneStr.trim())) {
			resultStr = "1";// 1代表为空
		} else if (!m.matches()) {
			resultStr = "2";// 2为格式不正确
		}
		return resultStr;
	}
	/**
	 * 验证输入的密码格式
	 * @param passStr
	 * @return
	 */
	public static String checkPassWord(String passStr) {
		String resultStr = "0";// 返回
		// 进行正则匹配是否密码格式
		Pattern p = Pattern.compile("[a-zA-Z0-9_]{6,20}$");
		Matcher m = p.matcher(passStr);
		if (null == passStr.trim() || "".equals(passStr.trim())) {
			resultStr = "1";// 1代表为空
		} else if (!m.matches()) {
			resultStr = "2";// 2为格式不正确
		}

		return resultStr;
	}
	//判断email格式是否正确
	public boolean isEmailes(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * 判断邮编
	 *
	 * @param zipString
	 * @return
	 */
	public static boolean isZipNO(String zipString) {
		String str = "^[1-9][0-9]{5}$";
		return Pattern.compile(str).matcher(zipString).matches();
	}

	/**
	 * 判断邮箱是否合法
	 *
	 * @param email
	 * @return
	 */
	public static boolean isEmails(String email) {
		if (null == email || "".equals(email)) return false;
		//Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}


	/**
	 * dp2px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px2dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	/**
	 *根据设备信息获取当前分辨率下指定单位对应的像素大小；
	 * px,dip,sp -> px
	 */
	public float getRawSize(Context c, int unit, float size) {
		Resources r;
		if (c == null){
			r = Resources.getSystem();
		}else{
			r = c.getResources();
		}
		return TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
	}
	/**
	 * 反正返回   文本框  或   编辑框  的内容
	 */
	public static String getContent(View content) {
		if (content instanceof EditText)
			return ((EditText) content).getText().toString().trim();
		if (content instanceof TextView)
			return ((TextView) content).getText().toString().trim();
		return null;
	}

}
