package com.giiso.submmited.ui.widget.swipetoloadlayout;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giiso.submmited.R;


/**
 * Created by xingwt on 2015/9/2.
 */
public class ClassicLoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView tvLoadMore;
    private ImageView ivSuccess;
    //    private ProgressBar progressBar;
    private TextView tvRefreshNoMore;
    private RelativeLayout rlRefresh;
    private boolean noMore = false;
    private int mFooterHeight;
    private AnimationDrawable rotate;

    public ClassicLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = 60;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
//        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvRefreshNoMore = (TextView) findViewById(R.id.tvRefreshNoMore);
        rlRefresh = (RelativeLayout) findViewById(R.id.rlRefresh);
        rotate = (AnimationDrawable) getResources().getDrawable(
                R.drawable.refresh_animation_list);
        ivSuccess.setImageDrawable(rotate);
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
    public void onPrepare() {
//        ivSuccess.setVisibility(GONE);
    }

    @Override
    public void onSwipe(int y, boolean isComplete) {
        if (!isComplete) {
//            ivSuccess.setVisibility(GONE);
//            progressBar.setVisibility(GONE);
            stop();
            Matrix matrix = new Matrix();
            ivSuccess.setScaleType(ImageView.ScaleType.MATRIX);   //required
            matrix.postRotate((float) -y * 2, ivSuccess.getWidth() / 2, ivSuccess.getHeight() / 2);
            ivSuccess.setImageMatrix(matrix);
            if (!noMore) {
                rlRefresh.setVisibility(VISIBLE);
                tvRefreshNoMore.setVisibility(GONE);
                if (-y >= mFooterHeight) {
                    tvLoadMore.setText(R.string.release_to_loadmore);
                } else {
                    tvLoadMore.setText(R.string.swipe_to_loadmore);
                }
            } else {
                rlRefresh.setVisibility(INVISIBLE);
                tvRefreshNoMore.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void onLoadMore() {
        if (!noMore) {
            tvLoadMore.setText(R.string.now_loadmore);
//            progressBar.setVisibility(VISIBLE);
            start();
            rlRefresh.setVisibility(VISIBLE);
            tvRefreshNoMore.setVisibility(GONE);
        } else {
            rlRefresh.setVisibility(INVISIBLE);
            tvRefreshNoMore.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void complete() {
//        progressBar.setVisibility(GONE);
//        ivSuccess.setVisibility(VISIBLE);
        stop();
    }

    @Override
    public void onReset() {
//        ivSuccess.setVisibility(GONE);
        stop();
    }

    public void setNoMore(boolean noMore) {
        this.noMore = noMore;
    }
}
