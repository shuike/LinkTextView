package com.LinkTextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.Toast;
import com.LinkTextView.span.TouchableSpan;
import com.LinkTextView.widget.LinkTextView;
import com.LinkTextView.widget.SpanTouchFixTextView;
import android.support.v4.content.ContextCompat;

	/*
	*提取的QMUI控件
	*author:睡客
	*
	*/
public class MainActivity extends AppCompatActivity {
	
	private int highlightTextNormalColor=0xff2288EE;//高亮正常颜色
    private int highlightTextPressedColor=0x801B88EE;//高亮点击颜色
    private int highlightBgNormalColor=0x00556622;//背景正常颜色
    private int highlightBgPressedColor=0x00664455;//背景点击颜色
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
		//颜色值设定
		//highlightTextNormalColor=ContextCompat.getColor(this,R.color.colorAccent);
		highlightTextNormalColor=getResources().getColor(R.color.colorAccent);
		
		//链接文字自动识别控件
		LinkTextView text=(LinkTextView)findViewById(R.id.link_text_view);
		text.setOnLinkClickListener(mOnLinkClickListener);
		
		//span文字触碰问题修复控件
		SpanTouchFixTextView fixtext=(SpanTouchFixTextView)findViewById(R.id.fix_text);
		//text.setOnLinkClickListener(mOnLinkClickListener);
		fixtext.setMovementMethodDefault();
		fixtext.setText(generateSp(getResources().getString(R.string.span_touch_fix_1)));
		
    }
	private LinkTextView.OnLinkClickListener mOnLinkClickListener = new LinkTextView.OnLinkClickListener() {
        @Override
        public void onTelLinkClick(String phoneNumber) {
            Toast.makeText(MainActivity.this, "识别到电话号码是：" + phoneNumber, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMailLinkClick(String mailAddress) {
            Toast.makeText(MainActivity.this, "识别到邮件地址是：" + mailAddress, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onWebUrlLinkClick(String url) {
            Toast.makeText(MainActivity.this, "识别到网页链接是：" + url, Toast.LENGTH_SHORT).show();
        }
    };
	
	
	private SpannableString generateSp(String text) {
        String highlight1 = "@qmui";
        String highlight2 = "#qmui#";
        SpannableString sp = new SpannableString(text);
    	final int start , end;
	 	start=0;
     	final int index;
        while ((index = text.indexOf(highlight1, start)) > -1) {
            end = index + highlight1.length();
			/*
			sp.setSpan(new TouchableSpan(){
				@Override
				public void onSpanClick(View widget)
				{
					String spanString = ((SpanTouchFixTextView)widget).getText().toString().substring(start, end);
					
					Toast.makeText(MainActivity.this, spanString, Toast.LENGTH_SHORT).show();
				}
			},index,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			*/
			
			//自定义颜色
            sp.setSpan(new TouchableSpan(highlightTextNormalColor, highlightTextPressedColor,
											 highlightBgNormalColor, highlightBgPressedColor) {
					@Override
					public void onSpanClick(View widget) {
						String spanString = ((SpanTouchFixTextView)widget).getText().toString().substring(start, end);
						Toast.makeText(MainActivity.this, spanString, Toast.LENGTH_SHORT).show();
					}
				}, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
				
            start = end;
        }

        start = 0;
		
        while ((index = text.indexOf(highlight2, start)) > -1) {
            end = index + highlight2.length();
			
			//用默认颜色
			sp.setSpan(new TouchableSpan(){
					@Override
					public void onSpanClick(View widget)
					{
						String spanString = ((SpanTouchFixTextView)widget).getText().toString().substring(index, end);
						Toast.makeText(MainActivity.this, spanString, Toast.LENGTH_SHORT).show();
					}
				},index,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			
				/*
            sp.setSpan(new TouchableSpan(highlightTextNormalColor, highlightTextPressedColor,
											 highlightBgNormalColor, highlightBgPressedColor) {
					@Override
					public void onSpanClick(View widget) {
						Toast.makeText(MainActivity.this, "click #qmui#", Toast.LENGTH_SHORT).show();
					}
				}, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
				*/
            start = end;
        }
        return sp;
    }
	
}
