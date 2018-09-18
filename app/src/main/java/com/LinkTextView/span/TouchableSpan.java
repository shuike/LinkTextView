package com.LinkTextView.span;

import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.LinkTextView.listener.ITouchableSpan;


/**
 * 可 Touch 的 Span，在 {@link #setPressed(boolean)} 后根据是否 pressed 来触发不同的UI状态
 * <p>
 * 提供设置 span 的文字颜色和背景颜色的功能, 在构造时传入
 * </p>
 */
public abstract class TouchableSpan extends ClickableSpan implements ITouchableSpan {
    private boolean mIsPressed;
   
	@ColorInt private int mNormalTextColor=0xff1B88EE;//高亮正常颜色
    @ColorInt private int mPressedTextColor=0x801B88EE;//高亮点击颜色
	@ColorInt private int mNormalBackgroundColor=0x00000000;//背景正常颜色
	@ColorInt private int mPressedBackgroundColor=0x00000000;//背景点击颜色
	
    private boolean mIsNeedUnderline = false;

    public abstract void onSpanClick(View widget);

    @Override
    public final void onClick(View widget) {
        if (ViewCompat.isAttachedToWindow(widget)) {
            onSpanClick(widget);
        }
    }
	public TouchableSpan(){
    }
	

    public TouchableSpan(@ColorInt int normalTextColor,
                             @ColorInt int pressedTextColor,
                             @ColorInt int normalBackgroundColor,
                             @ColorInt int pressedBackgroundColor) {
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mNormalBackgroundColor = normalBackgroundColor;
        mPressedBackgroundColor = pressedBackgroundColor;
    }
	
	
	

    public int getNormalBackgroundColor() {
        return mNormalBackgroundColor;
    }

    public int getNormalTextColor() {
        return mNormalTextColor;
    }

    public int getPressedBackgroundColor() {
        return mPressedBackgroundColor;
    }

    public int getPressedTextColor() {
        return mPressedTextColor;
    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }

    public boolean isPressed() {
        return mIsPressed;
    }

    public void setIsNeedUnderline(boolean isNeedUnderline) {
        mIsNeedUnderline = isNeedUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
        ds.bgColor = mIsPressed ? mPressedBackgroundColor
			: mNormalBackgroundColor;
        ds.setUnderlineText(mIsNeedUnderline);
    }
}
