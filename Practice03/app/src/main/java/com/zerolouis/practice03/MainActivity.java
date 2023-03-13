package com.zerolouis.practice03;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	private Button registerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerBtn = findViewById(R.id.registerBtn);
	}

	public void toRegister(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
}