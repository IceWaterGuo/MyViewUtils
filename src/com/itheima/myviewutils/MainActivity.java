package com.itheima.myviewutils;

import com.itheima.viewutils.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	@ViewInject(R.id.tv1)
	private TextView tv1;
	
	@ViewInject(R.id.tv2)
	private TextView tv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		
		Toast.makeText(this, "tv1="+tv1.getText(), Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "tv2="+tv2.getText(), Toast.LENGTH_SHORT).show();
		Log.d("I'm jack","fix a bug,no thanks!");
	}

	@OnClick({R.id.btn1,R.id.btn2})
	private void test(View view){
		switch (view.getId()) {
		case R.id.btn1:
			Toast.makeText(this, "bt1", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn2:
			Toast.makeText(this, "bt2", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
}
