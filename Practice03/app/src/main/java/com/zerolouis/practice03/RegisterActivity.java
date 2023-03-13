package com.zerolouis.practice03;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

	private EditText usernameEditText;  // 用户名输入框
	private EditText passwordEditText;  // 密码输入框
	private EditText confirmEditText;   // 确认密码输入框
	private Button registerBtn; // 注册按钮
	private Context mContext;   // 内容

	private final static String LOG_TAG = RegisterActivity.class.getSimpleName();

	// 判断数据是否合法
	public static int validate(String username,String password,String confirm){
		if(username.length()<=3){
			Log.d(LOG_TAG,"用户名长度小于3");
			return 1;
		} else if (password.length()<=8) {
			Log.d(LOG_TAG,"密码长度小于8");
			return 2;
		}else if (!password.equals(confirm)){
			Log.d(LOG_TAG,"两次输入的密码不正确");
			return 3;
		}else
			return 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// 获取输入框
		usernameEditText = findViewById(R.id.usernameEditText);
		passwordEditText = findViewById(R.id.passwordEditText);
		confirmEditText = findViewById(R.id.confirmEditText);
		registerBtn = findViewById(R.id.toRegister);
		// 绑定Context
		mContext = RegisterActivity.this;
	}

	public void onRegister(View view) {
		// 获取三个输入框的内容
		String username = usernameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();
		String confirm = confirmEditText.getText().toString().trim();
		Log.d(LOG_TAG,"获取了表单内容");

		// 判断输入信息是否合法
		int validate = validate(username, password, confirm);
		// 创建AlertDialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		AlertDialog dialog = null;

		// 判断合法状态
		if(validate==0){
			// 输入合法，跳转到成功页面
			Intent intent = new Intent(this,SuccessActivity.class);
			startActivity(intent);
			finish();
		}else if (validate==1){
			// 错误1
			dialog = builder.setTitle("用户名错误:")
					.setMessage("用户名长度不能少于3位")
					.setCancelable(true)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(mContext, "继续注册", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}
					}).create();
			dialog.show();

		}else if(validate==2){
			// 错误2
			dialog = builder.setTitle("密码错误:")
					.setMessage("密码不能少于8位")
					.setCancelable(true)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(mContext, "继续注册", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}
					}).create();
			dialog.show();

		}else if(validate==3){
			// 错误3
			dialog = builder.setTitle("密码错误:")
					.setMessage("两次输入的密码不一致")
					.setCancelable(true)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(mContext, "继续注册", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}
					}).create();
			dialog.show();
		}

	}
}