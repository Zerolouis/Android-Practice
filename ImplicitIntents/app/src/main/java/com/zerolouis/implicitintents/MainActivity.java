package com.zerolouis.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ShareCompat;

public class MainActivity extends AppCompatActivity {

	private EditText mWebsiteEditText;
	private EditText mLocationEditText;
	private EditText mShareTextEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWebsiteEditText = findViewById(R.id.website_edittext);
		mLocationEditText = findViewById(R.id.location_edittext);
		mShareTextEditText = findViewById(R.id.share_edittext);
	}

	public void openWebsite(View view) {
		// 获取uri
		String url = mWebsiteEditText.getText().toString();
		Uri webpage = Uri.parse(url);
		// 创建intent
		Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
		// 寻找一个活动处理这个请求，若不能处理打印日志
		if(intent.resolveActivity(getPackageManager()) != null){
			startActivity(intent);
		}else {
			Log.d("ImplicitIntents","无法处理");
		}
	}

	public void openLocation(View view) {
		// 获取位置文本
		String loc = mLocationEditText.getText().toString();
		// 转换为uri
		Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
		Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

		// 找到一个活动启动
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		} else {
			Log.d("ImplicitIntents", "不能打开位置");
		}
	}

	public void shareText(View view) {
		String txt = mShareTextEditText.getText().toString();
		String mimeType = "text/plain";
		ShareCompat.IntentBuilder
				.from(this)
				.setType(mimeType)
				.setChooserTitle(R.string.share_text_with)
				.setText(txt)
				.startChooser();
	}
}