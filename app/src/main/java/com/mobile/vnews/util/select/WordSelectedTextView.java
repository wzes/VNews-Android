package com.mobile.vnews.util.select;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.mobile.vnews.R;

import java.util.List;

/**
 * WordSelectedView
 */
public class WordSelectedTextView extends TextView {

    private static final int DEFAULT_SELECTED_COLOR = Color.parseColor( "#757575");
    private CharSequence mText;
    private BufferType mBufferType;

    private OnWordSelectedClickListener mOnWordSelectedClickListener;
    private SpannableString mSpannableString;

    private BackgroundColorSpan mSelectedBackSpan;
    private ForegroundColorSpan mSelectedForeSpan;

    private int highlightColor;
    private String highlightText;
    private int selectedColor;

    public WordSelectedTextView(Context context) {
        this(context, null);
    }

    public WordSelectedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WordSelectedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WordSelectedTextView);
        highlightColor = ta.getColor(R.styleable.WordSelectedTextView_highlightColor, Color.RED);
        highlightText = ta.getString(R.styleable.WordSelectedTextView_highlightText);
        selectedColor = ta.getColor(R.styleable.WordSelectedTextView_selectedColor, DEFAULT_SELECTED_COLOR);
        ta.recycle();
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        this.mText = text;
        mBufferType = type;
        setHighlightColor(Color.TRANSPARENT);
        setMovementMethod(LinkMovementMethod.getInstance());
        setText();
    }

    private void setText() {
        mSpannableString = new SpannableString(mText);
        //set highlight text
        setHighLightSpan(mSpannableString);
        //separate word
        doEnglish();
        super.setText(mSpannableString, mBufferType);
    }


    private void doEnglish() {
        List<WordInfo> wordInfoList = SelectedUtils.getEnglishWordIndices(mText.toString());
        for (WordInfo wordInfo : wordInfoList) {
            mSpannableString.setSpan(getClickableSpan(), wordInfo.getStart(),
                    wordInfo.getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     *
     * @param spannableString
     */
    private void setHighLightSpan(SpannableString spannableString) {
        if (TextUtils.isEmpty(highlightText)) {
            return;
        }
        int hIndex = mText.toString().indexOf(highlightText);
        while (hIndex != -1) {
            spannableString.setSpan(new ForegroundColorSpan(highlightColor), hIndex,
                    hIndex + highlightText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            hIndex += highlightText.length();
            hIndex = mText.toString().indexOf(highlightText, hIndex);
        }
    }

    /**
     *
     * @param tv
     */
    private void setSelectedSpan(TextView tv) {
        if (mSelectedBackSpan == null || mSelectedForeSpan == null) {
            mSelectedBackSpan = new BackgroundColorSpan(selectedColor);
            mSelectedForeSpan = new ForegroundColorSpan(Color.WHITE);
        } else {
            mSpannableString.removeSpan(mSelectedBackSpan);
            mSpannableString.removeSpan(mSelectedForeSpan);
        }
        mSpannableString.setSpan(mSelectedBackSpan, tv.getSelectionStart(), tv.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpannableString.setSpan(mSelectedForeSpan, tv.getSelectionStart(), tv.getSelectionEnd(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordSelectedTextView.super.setText(mSpannableString, mBufferType);
    }

    /**
     *
     */
    public void dismissSelected() {
        mSpannableString.removeSpan(mSelectedBackSpan);
        mSpannableString.removeSpan(mSelectedForeSpan);
        WordSelectedTextView.super.setText(mSpannableString, mBufferType);
    }

    /**
     * add click
     * @return
     */
    private ClickableSpan getClickableSpan() {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;
                String word = tv
                        .getText()
                        .subSequence(tv.getSelectionStart(),
                                tv.getSelectionEnd()).toString();
                setSelectedSpan(tv);

                if (mOnWordSelectedClickListener != null) {
                    mOnWordSelectedClickListener.onClick(word);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // super.updateDrawState(ds);
            }
        };
    }

    /**
     * set listener
     * @param listener
     */
    public void setOnWordSelectedClickListener(OnWordSelectedClickListener listener) {
        this.mOnWordSelectedClickListener = listener;
    }

    public void setHighLightText(String text) {
        highlightText = text;
    }

    public void setHighLightColor(int color) {
        highlightColor = color;
    }

    public interface OnWordSelectedClickListener {
        void onClick(String word);
    }
}
