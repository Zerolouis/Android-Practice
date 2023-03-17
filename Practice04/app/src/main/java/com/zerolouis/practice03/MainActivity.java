package com.zerolouis.practice03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	private Button registerBtn; // 注册按钮
	private Button loginBtn;    // 登录按钮
	private SharedPreferences mShare;   // 共享参数
	private EditText uesrnameEditText;  // 用户名编辑框
	private EditText passwordEditText;  // 密码框
	private String Log_TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerBtn = findViewById(R.id.registerBtn);
		loginBtn = findViewById(R.id.loginBtn);
		// 获取共享参数
		mShare = getSharedPreferences("userInfo",MODE_PRIVATE);
		// 获取文本输入框
		uesrnameEditText = findViewById(R.id.usernameEditText);
		passwordEditText = findViewById(R.id.passwordEditText);
	}

	public void toRegister(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}


	public void toLogin(View view) {
		String username = mShare.getString("username", null);
		String password = mShare.getString("password",null);

		if (login(username,password)) {
			Intent intent = new Intent(this,UserActivity.class);
			startActivity(intent);
		}else {
			Toast.makeText(this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
		}
	}

	// 判断方法
	public boolean login(String username,String password){
		String reallyUser = uesrnameEditText.getText().toString().trim();
		String reallyPasswd = passwordEditText.getText().toString().trim();
		Log.d(Log_TAG,"进行判断");
		return reallyUser.equals(username) && reallyPasswd.equals(password);
	}
}