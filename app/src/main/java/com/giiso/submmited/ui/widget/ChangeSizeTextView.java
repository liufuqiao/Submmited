package com.giiso.submmited.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.giiso.submmited.R;
import com.giiso.submmited.base.AppConfig;
import com.giiso.submmited.base.BaseApplication;

import skin.support.widget.SkinCompatTextView;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ChangeSizeTextView extends SkinCompatTextView {
    float textSize;

    public ChangeSizeTextView(Context context) {
        super(context);
        init();
    }

    public ChangeSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChangeSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
//        setTextChangeSize();
    }

    public void setTextChangeSize() {
        if (textSize <= 0) {
            textSize = getTextSize();
        }
        float sizeType = BaseApplication.get(AppConfig.TEXT_SIZE_MULTIPLE, 1.0f);
        if (sizeType != AppConfig.TEXT_SIZE_LARGE) {
            int size = (int) (textSize + 0.5);
            if (size == (int) (getResources().getDimension(R.dimen.textfont14) + 0.5)) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textfont14));
            } else if (size == (int) (getResources().getDimension(R.dimen.textfont16) + 0.5)) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textfont16));
            } else if (size == (int) (getResources().getDimension(R.dimen.textfont18) + 0.5)) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textfont18));
            }

        } else {
            int size = (int) (textSize + 0.5);
            if (size == (int) (getResources().getDimension(R.dimen.textfont14) + 0.5)) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textfont20));
            } else if (size == (int) (getResources().getDimension(R.dimen.textfont16) + 0.5)) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textfont22));
            } else if (size == (int) (getResources().getDimension(R.dimen.textfont18) + 0.5)) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textfont24));
            }
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
//        setTextChangeSize();
    }
}
