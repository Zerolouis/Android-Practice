package com.zerolouis.practice07;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.zerolouis.practice07.receiver.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {

	private Button btn_set;
	private Button btn_cancel;
	private Context mContext;
	private int year, month, day, hour, minute;
	private String LOG_TAG = MainActivity.class.getSimpleName();
	private Calendar calendar;
	private AlarmReceiver alarmReceiver;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_cancel = findViewById(R.id.btn_cancel);
		btn_set = findViewById(R.id.btn_set);
		initDateTime();
		setClick();
		mContext = this;
	}

	/**
	 * 初始化时间
	 *
	 * @author wxjju
	 * @date 20:04 2023/3/30
	 **/
	private void initDateTime() {
		// 设置时区并获取当前时间
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		Log.d(LOG_TAG, String.format("当前的时间:%s", formatDate(calendar, "yyyy年MM月dd日 HH:mm:ss")));
	}

	/**
	 * 配置触发器
	 *
	 * @author wxjju
	 * @date 20:04 2023/3/30
	 **/
	public void setClick() {
		btn_set.setOnClickListener(v -> setTime());
		btn_cancel.setOnClickListener(v -> {
			unregisterReceiver(alarmReceiver);
			Toast.makeText(mContext, "取消了闹钟", Toast.LENGTH_SHORT).show();
		});
	}

	/**
	 * 构造时间选择器，当用户点击确定后设置闹钟
	 *
	 * @author wxjju
	 * @date 20:04 2023/3/30
	 **/
	public void setTime() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 设置闹钟
				alarmReceiver.sendAlarm(calendar);
				Toast.makeText(mContext, "设置成功!", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 取消设置
				Toast.makeText(mContext, "取消设置", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		View setTimeView = View.inflate(mContext, R.layout.date_picker, null);
		initDateTime();
		// 获取时间选择器
		TimePicker timePicker = setTimeView.findViewById(R.id.timePicker);
		// 设置选择器为当前时间
		timePicker.setHour(hour);
		timePicker.setMinute(minute);
		timePicker.setOnTimeChangedListener(this);
		timePicker.setIs24HourView(true);
		dialog.setTitle("设置时间");
		dialog.setView(setTimeView);
		dialog.show();
	}

	/**
	 * timePicker的时间变化事件
	 *
	 * @param view      视图
	 * @param hourOfDay 小时
	 * @param minute    分钟
	 * @author wxjju
	 * @date 20:05 2023/3/30
	 **/
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		this.hour = hourOfDay;
		this.minute = minute;
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
	}

	/**
	 * 动态注册广播
	 *
	 * @author wxjju
	 * @date 14:52 2023/3/31
	 **/
	@Override
	protected void onStart() {
		super.onStart();
		alarmReceiver = new AlarmReceiver(getApplicationContext()) {
			// 由于AlterDialog的限制，必须写在Activity中，因此重写接受函数
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent != null && intent.getAction().equals(ALARM_ACTION)) {
					Log.d(LOG_TAG, "收到闹钟广播");
					showMessage();
				}
			}
		};
		IntentFilter filter = new IntentFilter(AlarmReceiver.ALARM_ACTION);
		registerReceiver(alarmReceiver, filter);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(alarmReceiver);
	}

	/**
	 * 收到闹钟后展示消息
	 *
	 * @author wxjju
	 * @date 14:49 2023/3/31
	 **/
	public void showMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 设置闹钟
						dialog.dismiss();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消设置
						Toast.makeText(mContext, "取消设置", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				})
				.setMessage(String.format(Locale.CHINA, "收到了闹钟:%s\n小猪小猪快起床", formatDate(calendar, "yyyy年MM月dd日 HH:mm:ss")))
				.setTitle("闹钟");
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * @param calendar 日期
	 * @param format   格式
	 * @return java.lang.String
	 * @author wxjju
	 * @date 15:17 2023/3/31
	 **/
	public String formatDate(Calendar calendar, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
		return simpleDateFormat.format(calendar.getTimeInMillis());
	}

	;
}