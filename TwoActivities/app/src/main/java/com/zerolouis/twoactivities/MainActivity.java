package com.zerolouis.twoactivities;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	public static final String EXTRA_MESSAGE= "com.zerolouis.twoactivities.extra.MESSAGE";
	public static final int TEXT_REQUEST = 1;
	private TextView mReplyHeadTextView;
	private TextView mReplyTextView;

	private EditText mMessageEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(LOG_TAG,"------------");
		Log.d(LOG_TAG,"onCreate");
		setContentView(R.layout.activity_main);
		// 获取文本框
		mMessageEditText = findViewById(R.id.editText_main);
		// 获取回复文本;
		mReplyHeadTextView = findViewById(R.id.text_header_reply);
		mReplyTextView = findViewById(R.id.text_message_reply);

		// 回复状态
		if (savedInstanceState != null) {
			Log.d(LOG_TAG,"MainActivity恢复了状态");
			// 获取显示状态
			boolean isVisible = savedInstanceState.getBoolean("reply_visible");
			// 回复文本
			if (isVisible) {
				mReplyHeadTextView.setVisibility(View.VISIBLE);
				mReplyTextView.setText(savedInstanceState
						.getString("reply_text"));
				mReplyTextView.setVisibility(View.VISIBLE);
			}
		}
	}

	// 使用onSaveInstanceState保存Activity实例状态
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 若有回复文本就将显示状态传入
		if (mReplyHeadTextView.getVisibility() == View.VISIBLE) {
			outState.putBoolean("reply_visible", true);
		}
		// 将回复文本保存
		outState.putString("reply_text",mReplyTextView.getText().toString());
	}

	public void launchSecondActivit(View view) {
		// add an explicit Intent to the main Activity
		Intent intent = new Intent(this, SecondActivity.class);
		String message = mMessageEditText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE,message);
		Log.d(LOG_TAG, "Button clicked!");
		// activate the second Activity
		// startActivity(intent);
		startActivityForResult(intent,TEXT_REQUEST);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(LOG_TAG,"onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(LOG_TAG,"onStop");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(LOG_TAG,"onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(LOG_TAG, "onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(LOG_TAG, "onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG,"onDestroy");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TEXT_REQUEST) {
			if (resultCode == RESULT_OK) {
				String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
				mReplyHeadTextView.setVisibility(View.VISIBLE);
				mReplyTextView.setText(reply);
				mReplyTextView.setVisibility(View.VISIBLE);
			}
		}
	}
}