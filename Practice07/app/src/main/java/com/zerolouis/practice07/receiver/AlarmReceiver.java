package com.zerolouis.practice07.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

	public static final String ALARM_ACTION = "com.zerolouis.practice07.alarm";
	private final Context mContext;
	private Calendar calendar;
	private String LOG_TAG = AlarmReceiver.class.getSimpleName();

	public AlarmReceiver(Context context) {
		super();
		this.mContext = context;
		calendar = Calendar.getInstance();
	}

	/**
	 * 接收事件
	 *
	 * @param context
	 * @param intent
	 * @author wxjju
	 * @date 15:04 2023/3/31
	 **/
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null && intent.getAction().equals(ALARM_ACTION)) {
			Log.d(LOG_TAG, "收到闹钟广播");
		}
	}

	/**
	 * 发送闹钟广播
	 *
	 * @param rec 接收到的时间
	 * @author wxjju
	 * @date 20:01 2023/3/30
	 **/
	public void sendAlarm(Calendar rec) {
		calendar = rec;
		Intent intent = new Intent(ALARM_ACTION);
		// 创建一个用于广播的延迟意图
		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);
		// 从系统服务中获取闹钟管理器
		AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		Log.d(LOG_TAG, String.format("设置了闹钟:%s", formatDate(calendar, "yyyy年MM月dd日 HH:mm:ss")));
		// 允许在空闲时发送广播，Android6.0之后新增的方法
		alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}

	public String formatDate(Calendar calendar, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
		return simpleDateFormat.format(calendar.getTimeInMillis());
	}

	;
}