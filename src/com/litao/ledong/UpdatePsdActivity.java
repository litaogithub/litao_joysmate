package com.litao.ledong;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.dawei.joysmate.R;

public class UpdatePsdActivity extends Activity implements OnClickListener{

	private EditText edit_psd,edit_psd2;
	private ImageView back;
	private Button button;
	private String phoneNum;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_mypassword);
		back = (ImageView) findViewById(R.id.update_back);
		edit_psd = (EditText) findViewById(R.id.update_password);
		edit_psd2 = (EditText) findViewById(R.id.update_check_password);
		button = (Button) findViewById(R.id.update_btn);
		button.setOnClickListener(this);
		back.setOnClickListener(this);
		Intent it = getIntent();
		phoneNum = it.getStringExtra("phoneNum");
		edit_psd2.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				signUpBtnCheck();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	//激活下一步按钮
		private void signUpBtnCheck() {
			if(edit_psd2.getText().toString().length()>=1){
				button.setEnabled(true);
				button.setBackgroundColor(Color.parseColor("#C0272A"));
			}else{
				button.setEnabled(false);
				button.setBackgroundColor(Color.GRAY);
			}
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.update_back:
			finish();
			break;
		case R.id.update_btn:
			if(edit_psd.getText().toString().trim().equals(edit_psd2.getText().toString().trim())){
					AVQuery<AVObject> query = AVQuery.getQuery("_User");
					query.whereEqualTo("username",phoneNum);
					query.findInBackground(new FindCallback<AVObject>() {
						
						@Override
						public void done(List<AVObject> arg0, AVException arg1) {
							// TODO Auto-generated method stub
							AVUser user = (AVUser) arg0.get(0);
							user.setPassword(edit_psd.getText().toString());
							user.saveInBackground(new SaveCallback() {
								
								@Override
								public void done(AVException arg0) {
									// TODO Auto-generated method stub
									Log.i("aaa", "111");
									if(arg0==null){
										Toast.makeText(UpdatePsdActivity.this, "修改成功", 0).show();
										Intent it = new Intent(UpdatePsdActivity.this,LoginActivity.class);
										startActivity(it);
									}else{
										Toast.makeText(UpdatePsdActivity.this, "修改失败", 0).show();
									}
								}
							});
						}
					});
			}else{
				Toast.makeText(UpdatePsdActivity.this, "两次密码不一致", 0).show();
			}
			
			break;

		default:
			break;
		}
	}
}
