package com.litao.ledong;


import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.dawei.joysmate.R;
import com.litao.config.AppConfig;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{


	private EditText regist_num,regis_psd,regis_psd2,regis_nicheng,regis_code;
	private TextView pull_code,user_xieyi,user_tiaokuan;
	private ImageView mView;
	private Button regist_btn;
	private CountDownTimer mTime;
	private RadioGroup radio;
	private String sexMsg = "";
	private AVUser user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		mView = (ImageView) findViewById(R.id.register_back);
		regist_num = (EditText) findViewById(R.id.register_phoneNum);
		regis_psd = (EditText) findViewById(R.id.register_password);
		regis_psd2 = (EditText) findViewById(R.id.check_password);
		regis_nicheng = (EditText) findViewById(R.id.register_nicheng);
		regis_code = (EditText) findViewById(R.id.register_yazhengma);
		pull_code = (TextView) findViewById(R.id.pull_code);
		user_xieyi = (TextView) findViewById(R.id.register_xieyi);
		user_tiaokuan = (TextView) findViewById(R.id.register_tiaokuan);
		regist_btn = (Button) findViewById(R.id.regis_submit);
		radio = (RadioGroup) findViewById(R.id.regist_radiogroup);
		radio.setOnClickListener(this);
		mView.setOnClickListener(this);
		pull_code.setOnClickListener(this);
		user_xieyi.setOnClickListener(this);
		user_tiaokuan.setOnClickListener(this);
		regist_btn.setOnClickListener(this);
		regis_code.addTextChangedListener(new TextWatcher() {

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
		regist_num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				idCodeBtnCheck();
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
		signUpBtnCheck();
		idCodeBtnCheck();
	}
	//检查验证码
	private void idCodeBtnCheck() {
		if(regist_num.getText().toString().length()==11){
			pull_code.setEnabled(true);
			pull_code.setTextColor(Color.RED);
		}else{	
			pull_code.setEnabled(false);
			pull_code.setTextColor(Color.GRAY);
		}
	}
	//激活注册按钮
	private void signUpBtnCheck() {
		if(regis_code.getText().toString().length()>=6){
			regist_btn.setEnabled(true);
			regist_btn.setBackgroundColor(Color.parseColor("#C0272A"));
		}else{
			regist_btn.setEnabled(false);
			regist_btn.setBackgroundColor(Color.GRAY);
		}
	}
	//获取验证码
	private void obtainIdCode(){
		mTime = new CountDownTimer(AppConfig.ID_CODE_COUNT_DOWN * 1000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				pull_code.setEnabled(false);
				pull_code.setTextColor(Color.GRAY);
				pull_code.setText("等待验证码 "+millisUntilFinished / 1000);

			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				pull_code.setEnabled(true);
				pull_code.setText("获取验证码");
				pull_code.setTextColor(Color.RED);
			}
		}.start();
		AVOSCloud.requestSMSCodeInBackgroud(regist_num.getText().toString(), new RequestMobileCodeCallback() {

			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if(arg0==null){
					Toast.makeText(RegisterActivity.this, "验证码已发送，请注意查收！", 0).show();
				}
			}
		});
	}

	//性别选择
		private void checkSex(){
			int len = radio.getChildCount();
			for(int i=0;i<len;i++){
				RadioButton radiobutton = (RadioButton) radio.getChildAt(i);
				if (radiobutton.isChecked()) { 
	                //如果被选中，则break循环，并且记录选中信息 
	                sexMsg = radiobutton.getText().toString(); 
	            } 
			}
		}
	/**
	 * 检查数据的规范性
	 * @return
	 */
	protected boolean checkDate() {
		if(regis_psd.getText().toString().trim()==null){
			Toast.makeText(RegisterActivity.this, "亲，没有设置密码哦", 0).show();
			return false;
		}else if(regis_psd.getText().toString().trim().length()<6||regis_psd.getText().toString().length()>12){
			Toast.makeText(RegisterActivity.this, "密码长度为6-12位之间", 0).show();
			return false;
		}else if(regis_psd2.getText().toString().trim()==null||!regis_psd2.getText().toString().trim().equals(regis_psd.getText().toString().trim())){
			Toast.makeText(RegisterActivity.this, "亲，两次密码不一致呦", 0).show();
			return false;
		}else if(regis_nicheng.getText().toString().trim()==null){
			Toast.makeText(RegisterActivity.this, "请输入您的个性昵称", 0).show();
			return false;
		}else if(regis_nicheng.getText().toString().length()>10){
			Toast.makeText(RegisterActivity.this, "昵称太长咯！", 0).show();
			return false;
		}else if(sexMsg.equals("")){
			Toast.makeText(RegisterActivity.this, "还不知道你是男是女哦", 0).show();
			return false;
		}
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_back:
			finish();
			break;
		case R.id.pull_code:
			AVQuery<AVObject> query = AVQuery.getQuery("_User");
			query.whereEqualTo("username",regist_num.getText().toString());
			query.findInBackground(new FindCallback<AVObject>() {
				
				@Override
				public void done(List<AVObject> arg0, AVException arg1) {
					// TODO Auto-generated method stub
					if(arg0.size()!=0){
						Toast.makeText(RegisterActivity.this,"手机号已注册", 0).show();
					}else{
						obtainIdCode();
					}
				}
			});
			break;
		case R.id.register_xieyi:
			Toast.makeText(RegisterActivity.this, "协议", 0).show();
			break;
		case R.id.register_tiaokuan:
			Toast.makeText(RegisterActivity.this, "条款", 0).show();
			break;
		case R.id.regis_submit:
			checkSex();
			if(checkDate()){
				AVOSCloud.verifySMSCodeInBackground(regis_code.getText().toString(),regist_num.getText().toString(),new AVMobilePhoneVerifyCallback() {
					
					@Override
					public void done(AVException arg0) {
						// TODO Auto-generated method stub
						if(arg0==null){
							AVUser user = new AVUser();
							user.setUsername(regist_num.getText().toString());
							user.setPassword(regis_psd.getText().toString());
							user.put("nickname",regis_nicheng.getText().toString());
							user.put("sex",sexMsg);
							user.signUpInBackground(new SignUpCallback() {
								
								@Override
								public void done(AVException arg0) {
									// TODO Auto-generated method stub
									if(arg0==null){
										Toast.makeText(RegisterActivity.this,"跳转到主页面", 0).show();
									}else{
										Toast.makeText(RegisterActivity.this,"手机号已注册", 0).show();
									}
								}
							});
							Toast.makeText(RegisterActivity.this, "注册成功",0).show();
						}else{
							Toast.makeText(RegisterActivity.this, "验证码有误，请重新输入", 0).show();
						}
					}
				});
			}
			break;
		default:
			break;
		}
	}
}
