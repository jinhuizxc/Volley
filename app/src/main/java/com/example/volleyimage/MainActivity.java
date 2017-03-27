package com.example.volleyimage;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@ViewInject(R.id.imageView1)
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ViewUtils.inject(this);
	}

	@OnClick(R.id.button1)
	public void OnClick(View v) {
		// 加载图片
		/** 网址可以为jsp的网址，图片复制到jsp,添加链接即可！ */
		String url = "http://www.nowamagic.net/librarys/images/random/rand_11.jpg";
		ImageRequest request = new ImageRequest(url, new Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				Log.d("Tag", "请求成功");
				imageView.setImageBitmap(response);
			}
		}, 0, 0, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("Tag", "请求失败" + error.toString());
				imageView.setImageResource(R.drawable.ic_launcher);
			}
		});

		request.setTag("image");
		MyApp.getRequestQueue().add(request);

	}

	@OnClick(R.id.button2)
	public void onClickImageLoader(View v) {
		// 缓存
		MyImageCathe cathe = new MyImageCathe(this);

		// ImageLoader对象，指定请求队列，缓存对象
		ImageLoader load = new ImageLoader(MyApp.getRequestQueue(), cathe);
		// 设置下载过程中的参数
		// 1.显示图片的控件
		// 2.下载过程中的图片id
		// 3.下载失败的图片id
		/**监听回调*/
		ImageListener listener = ImageLoader.getImageListener(imageView,
				R.drawable.ic_launcher, R.drawable.fail);

		String url = "http://www.nowamagic.net/librarys/images/random/rand_11.jpg";
		// 启动下载
		load.get(url, listener);
	}

	/** 销毁Activity时取消请求。 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 取消请求
		MyApp.getRequestQueue().cancelAll("iamge");

	}
}
