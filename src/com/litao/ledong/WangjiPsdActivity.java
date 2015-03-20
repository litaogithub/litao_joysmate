package com.litao.ledong;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.dawei.joysmate.R;
import com.litao.config.AppConfig;

public class WangjiPsdActivity extends Activity implements OnClickListener{

	private ImageView back;
	private EditText edit_num,edit_code;
	private Button button;
	private TextView pull_code;
	private CountDownTimer mTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wangji_activity);
		back = (ImageView) findViewById(R.id.wangji_back);
		edit_num = (EditText) findViewById(R.id.wangji_phoneNum);
		edit_code = (EditText) findViewById(R.id.wangji_yazhengma);
		button = (Button) findViewById(R.id.xiayibu_btn);
		pull_code = (TextView) findViewById(R.id.wangji_pull_code);
		pull_code.setOnClickListener(this);
		back.setOnClickListener(this);
		edit_num.setOnClickListener(this);
		edit_code.setOnClickListener(this);
		button.setOnClickListener(this);
		edit_num.addTextChangedListener(new TextWatcher() {

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
		edit_code.addTextChangedListener(new TextWatcher() {

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
	//检查验证码
	private void idCodeBtnCheck() {
		if(edit_num.getText().toString().length()==11){
			pull_code.setEnabled(true);
			pull_code.setTextColor(Color.parseColor("#C0272A"));
		}else{	
			pull_code.setEnabled(false);
			pull_code.setTextColor(Color.GRAY);
		}
	}
	//激活下一步按钮
	private void signUpBtnCheck() {
		if(edit_code.getText().toString().length()>=6){
			button.setEnabled(true);
			button.setBackgroundColor(Color.parseColor("#C0272A"));
		}else{
			button.setEnabled(false);
			button.setBackgroundColor(Color.GRAY);
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
				pull_code.setTextColor(Color.parseColor("#C0272A"));
			}
		}.start();
		AVOSCloud.requestSMSCodeInBackgroud(edit_num.getText().toString(), new RequestMobileCodeCallback() {

			@Override
			public void done(AVException arg0) {
				// TODO Auto-generated method stub
				if(arg0==null){
					Toast.makeText(WangjiPsdActivity.this, "验证码已发送，请注意查收！", 0).show();
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wangji_back:
			finish();
			break;
		case R.id.xiayibu_btn:
			
			AVOSCloud.verifySMSCodeInBackground(edit_code.getText().toString(),edit_num.getText().toString(),new AVMobilePhoneVerifyCallback() {

				@Override
				public void done(AVException arg0) {
					// TODO Auto-generated method stub
					if(arg0==null){
						Intent it = new Intent(WangjiPsdActivity.this,UpdatePsdActivity.class);
						it.putExtra("phoneNum", edit_num.getText().toString());
						startActivity(it);
					}else{
						Toast.makeText(WangjiPsdActivity.this, "验证码有误，请重新输入", 0).show();
					}
				}
			});
			break;
		case R.id.wangji_pull_code:
			AVQuery<AVObject> query = AVQuery.getQuery("_User");
			query.whereEqualTo("username",edit_num.getText().toString());
			query.findInBackground(new FindCallback<AVObject>() {
				
				@Override
				public void done(List<AVObject> arg0, AVException arg1) {
					// TODO Auto-generated method stub
					if(arg0.size()!=0){
						obtainIdCode();
					}else{
						Toast.makeText(WangjiPsdActivity.this,"手机号不存在", 0).show();
					}
				}
			});
			
			break;

		default:
			break;
		}
	}
}
