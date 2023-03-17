package com.zerolouis.practice03;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zerolouis.practice03.bean.UserInfo;
import com.zerolouis.practice03.database.UserDBHelper;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserActivity extends AppCompatActivity {

	private TextView userInfoTextView;  // 文本框
	private Context mContext;   // 内容
	private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象
	private EditText passwordEditText;
	private EditText confirmEditText;
	private EditText ageEditText;
	private EditText phoneEditText;
	UserInfo userInfo;

	public static boolean isValidPhoneNumber(String phoneNumber) {
		if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
			return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		Intent intent = this.getIntent();
		userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
		// 绑定编辑框
		userInfoTextView = findViewById(R.id.userInfoText);
		passwordEditText = findViewById(R.id.passwordEditText);
		confirmEditText = findViewById(R.id.confirmEditText);
		ageEditText = findViewById(R.id.ageEditText);
		phoneEditText = findViewById(R.id.phoneEditText);
		// 绑定Context
		mContext = UserActivity.this;
		userInfoTextView.setText(userInfo.toString());

		// 获得数据库帮助器的实例
		mHelper = UserDBHelper.getInstance(this, 1);
		mHelper.openReadLink(); // 打开数据库帮助器的读连接
	}

	public void onLogout(View view) {
		// 创建AlertDialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		AlertDialog dialog = null;
		dialog = builder.setTitle("提示:")
				.setMessage("是否注销，这将会删除用户数据!")
				.setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(mContext, "删除数据", Toast.LENGTH_SHORT).show();
						mHelper.deleteAll();
						Intent intent = new Intent(mContext,MainActivity.class);
						startActivity(intent);
					}
				}).setNegativeButton("取消",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}

	public void onChange(View view){
		// 获取数据
		String password = passwordEditText.getText().toString().trim();
		String confirm = confirmEditText.getText().toString().trim();
		String age = ageEditText.getText().toString().trim();
		String phone = phoneEditText.getText().toString().trim();

		// 判断并写入
		if (!password.isEmpty()&&!confirm.isEmpty()){
			if(password.equals(confirm)){
				userInfo.setPassword(password);
			}else {
				Toast.makeText(mContext, "密码输入错误!", Toast.LENGTH_SHORT).show();
				return;
			}
		}

		if(!age.isEmpty()&&age.length()<=3){
			userInfo.setAge(Integer.parseInt(age));
		}else if(age.length()>3){
			Toast.makeText(mContext, "年龄输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}

		if(!phone.isEmpty()){
			if(isValidPhoneNumber(phone)){
				userInfo.setPhone(phone);
			}else{
				Toast.makeText(mContext, "电话号码输入有误", Toast.LENGTH_SHORT).show();
				return;
			}
		}

		mHelper.update(userInfo);
		Toast.makeText(mContext, "更新成功", Toast.LENGTH_SHORT).show();
		userInfoTextView.setText(userInfo.toString());
	}

}