package com.litao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dawei.joysmate.R;
import com.litao.ledong.LoginActivity;

public class FiveFragment extends Fragment implements OnClickListener{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.welcome_img5, container,false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ImageView mImageView = (ImageView) getView().findViewById(R.id.login_phone);
		ImageView mImageView2 = (ImageView) getView().findViewById(R.id.login_wechat);
		ImageView mImageView3 = (ImageView) getView().findViewById(R.id.login_weibo);
		ImageView mImageView4 = (ImageView) getView().findViewById(R.id.login_qq);
		mImageView.setOnClickListener(this);
		mImageView2.setOnClickListener(this);
		mImageView3.setOnClickListener(this);
		mImageView4.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_phone:
			Intent it = new Intent(getActivity(),LoginActivity.class);
			getActivity().startActivity(it);
			break;
		case R.id.login_wechat:
			Toast.makeText(getActivity(),"΢��",0).show();
			break;
		case R.id.login_weibo:
			Toast.makeText(getActivity(),"΢��",0).show();
			break;
		case R.id.login_qq:
			Toast.makeText(getActivity(),"QQ",0).show();
			break;

		default:
			break;
		}
	}
}
