package com.giiso.submmited.ui.widget.swipetoloadlayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.giiso.submmited.R;


/**
 * Created by xingwt on 2015/9/9.
 */
public class TwitterRefreshHeaderView extends SwipeRefreshHeaderLayout {

//    private ImageView ivArrow;

    private ImageView ivSuccess;

    private TextView tvRefresh;

//    private ProgressBar progressBar;

    private int mHeaderHeight;

//    private Animation rotateUp;
//
//    private Animation rotateDown;

    private AnimationDrawable rotate;


    private boolean rotated = false;

    public TwitterRefreshHeaderView(Context context) {
        this(context, null);
    }

    public TwitterRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwitterRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = 72;
//        rotateUp = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
//        rotateDown = AnimationUtils.loadAnimation(context, R.anim.rotate_down);
        rotate = (AnimationDrawable) getResources().getDrawable(
                R.drawable.refresh_animation_list);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
//        ivArrow = (ImageView) findViewById(R.id.ivArrow);
//        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
//        ivSuccess.setImageDrawable(rotate);
//        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        ivArrow.setImageDrawable(rotate);
    }

    @Override
    public void onRefresh() {
//        ivSuccess.setVisibility(GONE);
//        progressBar.setVisibility(VISIBLE);
//        ivArrow.setVisibility(GONE);
        start();
        tvRefresh.setText(R.string.now_refreshing);
    }

    @Override
    public void onPrepare() {

    }

    public void start() {
        if (rotate != null && !rotate.isRunning()) {
            rotate.start();
        }
    }

    public void stop() {
//        ivArrow.clearAnimation();
        if (rotate != null && rotate.isRunning()) {
            rotate.stop();
        }
    }

    @Override
    public void onSwipe(int y, boolean isComplete) {
        if (!isComplete) {
//            ivArrow.setVisibility(VISIBLE);
//            progressBar.setVisibility(GONE);
//            ivSuccess.setVisibility(GONE);
            //Matrix matrix = new Matrix();
            //ivSuccess.setScaleType(ImageView.ScaleType.MATRIX);   //required
            //matrix.postRotate((float) y * 2, ivSuccess.getWidth() / 2, ivSuccess.getHeight() / 2);
            //ivSuccess.setImageMatrix(matrix);

            if (y > mHeaderHeight) {
                tvRefresh.setText(R.string.release_to_refresh);
                if (!rotated) {
//                    stop();
//                    ivArrow.startAnimation(rotateUp);
                    rotated = true;
                }
            } else if (y < mHeaderHeight) {
                if (rotated) {
//                    stop();
//                    ivArrow.startAnimation(rotateDown);
                    rotated = false;
                }

                tvRefresh.setText(R.string.swipe_to_refresh);
            }
        }
    }

    @Override
    public void onRelease() {


    }

    @Override
    public void complete() {
        rotated = false;
//        ivSuccess.setVisibility(VISIBLE);
//        ivArrow.setVisibility(GONE);
//        progressBar.setVisibility(GONE);
        stop();
        tvRefresh.setText(R.string.refresh_complete);
    }

    @Override
    public void onReset() {
        rotated = false;
//        ivSuccess.setVisibility(GONE);
//        ivArrow.setVisibility(GONE);
//        progressBar.setVisibility(GONE);
        stop();
    }

}
