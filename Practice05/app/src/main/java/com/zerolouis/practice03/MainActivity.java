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
import com.zerolouis.practice03.bean.UserInfo;
import com.zerolouis.practice03.database.UserDBHelper;

public class MainActivity extends AppCompatActivity {

	private Button registerBtn; // 注册按钮
	private Button loginBtn;    // 登录按钮
	private EditText uesrnameEditText;  // 用户名编辑框
	private EditText passwordEditText;  // 密码框
	private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象
	private String Log_TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerBtn = findViewById(R.id.registerBtn);
		loginBtn = findViewById(R.id.loginBtn);
		// 获取文本输入框
		uesrnameEditText = findViewById(R.id.usernameEditText);
		passwordEditText = findViewById(R.id.passwordEditText);

		// 获得数据库帮助器的实例
		mHelper = UserDBHelper.getInstance(this, 1);
		mHelper.openReadLink(); // 打开数据库帮助器的读连接
	}

	public void toRegister(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}


	public void toLogin(View view) {
		String username = uesrnameEditText.getText().toString().trim();
		String password = "";
		UserInfo info = null;
		if(!username.equals("")){
			info = mHelper.queryByUsername(username);
		}

		if(info!=null){
			password = info.getPassword();
			if (login(password)) {
				Intent intent = new Intent(this,UserActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("userInfo",info);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}else {
				Toast.makeText(this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
			}
		}else {
			Toast.makeText(this, "用户不存在!", Toast.LENGTH_SHORT).show();
		}
	}

	// 判断方法
	public boolean login(String password){
		String reallyPasswd = passwordEditText.getText().toString().trim();
		Log.d(Log_TAG,"进行判断");
		return reallyPasswd.equals(password);
	}
}