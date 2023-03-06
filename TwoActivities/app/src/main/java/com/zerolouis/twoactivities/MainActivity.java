package com.zerolouis.twoactivities;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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
		setContentView(R.layout.activity_main);
		// 获取文本框
		mMessageEditText = findViewById(R.id.editText_main);
		// 获取回复文本;
		mReplyHeadTextView = findViewById(R.id.text_header_reply);
		mReplyTextView = findViewById(R.id.text_message_reply);
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