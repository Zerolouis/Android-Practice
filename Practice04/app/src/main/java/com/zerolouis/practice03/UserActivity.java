package com.zerolouis.practice03;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Map;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {

	private TextView userInfoTextView;  // 文本框
	private SharedPreferences mShare;   // 共享参数
	private Context mContext;   // 内容
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		userInfoTextView = findViewById(R.id.userInfoText);
		mShare = getSharedPreferences("userInfo",MODE_PRIVATE);
		// 绑定Context
		mContext = UserActivity.this;
		showUserInfo();
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
						SharedPreferences.Editor editor = mShare.edit();    // 获取editor
						editor.clear();     // 清除所有数据
						editor.commit();    // 提交
						dialog.dismiss();
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

	public void showUserInfo(){
		String username = mShare.getString("username", null);
		String password = mShare.getString("password",null);

		String info = "用户名:"+username+"\n密码:"+password;
		userInfoTextView.setText(info);
	}
}