package com.litao.ledong;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.dawei.joysmate.R;
import com.litao.adapter.WelcomeAdapter;
import com.litao.fragment.FiveFragment;
import com.litao.fragment.FourFragment;
import com.litao.fragment.OneFragment;
import com.litao.fragment.ThreeFragment;
import com.litao.fragment.TwoFragment;

public class MainActivity extends FragmentActivity{

	private ViewPager mPager;
	private ArrayList<View> dots;
	private View view1, view2, view3,view4,view5;
	private int oldPosition = 0;// 记录上一次点的位置
	private int currentItem; // 当前页面

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//	requestWindowFeature(Window.FEATURE_NO_TITLE);//设置去标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(new OneFragment());
		list.add(new TwoFragment());
		list.add(new ThreeFragment());
		list.add(new FourFragment());
		list.add(new FiveFragment());
		// 显示的点
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));
		dots.add(findViewById(R.id.dot_3));
		dots.add(findViewById(R.id.dot_4));
		dots.add(findViewById(R.id.dot_5));

		mPager = (ViewPager) findViewById(R.id.viewpager);
		mPager.setAdapter(new WelcomeAdapter(getSupportFragmentManager(), list));
		dots.get(0).setBackgroundResource(R.drawable.dot_focused);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				dots.get(oldPosition).setBackgroundResource(
						R.drawable.dot_normal);
				dots.get(arg0)
						.setBackgroundResource(R.drawable.dot_focused);

				oldPosition = arg0;
				currentItem = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
