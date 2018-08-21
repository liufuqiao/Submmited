package com.giiso.submmited.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.giiso.submmited.R;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatSupportable;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ProportionImageView extends ImageView implements SkinCompatSupportable {
    int proportionNum = 4;
    int proportion = 3;


    public ProportionImageView(Context context) {
        this(context, null);
    }

    public ProportionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProportionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

   /*     TypedArray a = context.obtainStyledAttributes(attrs, android.support.design.R.styleable.TabLayout,
                defStyleAttr, android.support.design.R.style.Widget_Design_TabLayout);*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProportionImageView,
                defStyleAttr, 0);
        proportionNum = typedArray.getInt(R.styleable.ProportionImageView_proportionNum, 0);
        proportion = typedArray.getInt(R.styleable.ProportionImageView_proportion, 0);
        if (proportionNum > 0) {
            init();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(SkinCompatResources.getColor(getContext(), R.color.layer_gradual));
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    public void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                int height = getWidth() * proportion / proportionNum;
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = height;
                setLayoutParams(layoutParams);
                setBackground( SkinCompatResources.getDrawable(getContext(), R.mipmap.image_default));
            }
        });
    }

    @Override
    public void applySkin() {
        invalidate();
    }
}
