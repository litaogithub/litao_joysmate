package com.dawei.joysmate;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.litao.ledong.LoginActivity;

/**
 * Created by wdxu on 3/6/2015.
 */
public class JoymateApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		AVOSCloud.initialize(this,"nixsfqsrxp3bllx6rzakxn2jeckwrmkr9qgdy5vb15jp1dsa", "xlwd0z1ahsg2wmbb7ye47uis5eittfx7hhg1202h0xy738dw");
	}
}
