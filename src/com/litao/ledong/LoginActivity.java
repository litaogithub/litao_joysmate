package com.litao.ledong;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.dawei.joysmate.R;

public class LoginActivity extends Activity implements OnClickListener{

	private TextView zhuce,wangji,zhuxiao;
	private ImageView mView;
	private EditText phoneNum;
	private EditText password;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		mView = (ImageView) findViewById(R.id.login_back);
		zhuce = (TextView) findViewById(R.id.register_user);
		phoneNum = (EditText) findViewById(R.id.input_phoneNum);
		password = (EditText) findViewById(R.id.input_password);
		wangji = (TextView) findViewById(R.id.wangji_password);
		button = (Button) findViewById(R.id.login_btn);
		zhuxiao = (TextView) findViewById(R.id.zhuxiao_user);
		mView.setOnClickListener(this);
		zhuce.setOnClickListener(this);
		wangji.setOnClickListener(this);
		button.setOnClickListener(this);
		zhuxiao.setOnClickListener(this);
		password.addTextChangedListener(new TextWatcher() {

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

	//激活登录按钮
	private void signUpBtnCheck() {
		if(password.getText().toString().length()>0){
			button.setEnabled(true);
			button.setBackgroundColor(Color.parseColor("#C0272A"));
		}else{
			button.setEnabled(false);
			button.setBackgroundColor(Color.parseColor("#cccccc"));
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_back:
			finish();
			break;
		case R.id.register_user:
			Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(it);
			break;
		case R.id.wangji_password:
			Intent intent = new Intent(LoginActivity.this,WangjiPsdActivity.class);
			startActivity(intent);
			break;
		case R.id.zhuxiao_user:
			new AlertDialog.Builder(this)
			.setTitle("注销")
			.setMessage("确定注销当前账号吗？")
			.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					AVUser.logOut();
					Intent it = new Intent(LoginActivity.this,LoginActivity.class);
					startActivity(it);
					Toast.makeText(LoginActivity.this,"已注销账号，跳转登录页面", 0).show();
				}
			}).setNegativeButton("取消",null)
			.create().show();
			break;
		case R.id.login_btn:
			AVQuery<AVObject> query = AVQuery.getQuery("_User");
			query.whereEqualTo("username",phoneNum.getText().toString());
			query.findInBackground(new FindCallback<AVObject>() {

				@Override
				public void done(List<AVObject> arg0, AVException arg1) {
					// TODO Auto-generated method stub
					if(arg0.size()!=0){
						AVUser.logInInBackground(phoneNum.getText().toString(), password.getText().toString(), new LogInCallback<AVUser>() {
							
							@Override
							public void done(AVUser arg0, AVException arg1) {
								// TODO Auto-generated method stub
								if(arg1==null){
									Toast.makeText(LoginActivity.this,"登录成功", 0).show();
								}else{
									Toast.makeText(LoginActivity.this,"密码不正确", 0).show();
								}
							}
						});
					}else{
						Toast.makeText(LoginActivity.this,"手机号不存在，请注册", 0).show();
					}
				}
			});

			break;

		default:
			break;
		}
	}
}
