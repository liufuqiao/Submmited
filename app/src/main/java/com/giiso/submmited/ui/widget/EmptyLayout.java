package com.giiso.submmited.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giiso.submmited.R;

public class EmptyLayout extends LinearLayout implements
        View.OnClickListener {// , ISkinUIObserver {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_NO = 7;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;

    private final Context context;
    public ImageView img;
    private Loading loading;
    private OnClickListener listener;
    private int mErrorState;
    private String strNoDatatitle = "";
    private String strNoDatainfo = "";
    private TextView tv_error_title;
    private TextView tv_error_info;
    private TextView tv_error_btn;
    Boolean flag=false;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_error_layout, this, false);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv_error_title = (TextView) view.findViewById(R.id.tv_error_title);
        tv_error_info = (TextView) view.findViewById(R.id.tv_error_info);
        tv_error_btn = (TextView) view.findViewById(R.id.tv_error_btn);
        loading = (Loading) view.findViewById(R.id.animProgress);
//        setBackgroundColor(-1);
        setOnClickListener(this);
        tv_error_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // setErrorType(NETWORK_LOADING);
                if (listener != null)
                    listener.onClick(v);
//                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        addView(view);
        changeErrorLayoutBgMode(context);
    }

    public void changeErrorLayoutBgMode(Context context1) {
        // mLayout.setBackgroundColor(SkinsUtil.getColor(context1,
        // "bgcolor01"));
        // tv_hint1.setTextColor(SkinsUtil.getColor(context1, "textcolor05"));
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        // setErrorType(NETWORK_LOADING);
        if (listener != null&&flag)
            listener.onClick(v);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // MyApplication.getInstance().getAtSkinObserable().registered(this);
        onSkinChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // MyApplication.getInstance().getAtSkinObserable().unregistered(this);
    }

    public void onSkinChanged() {
        // mLayout.setBackgroundColor(SkinsUtil
        // .getColor(getContext(), "bgcolor01"));
        // tv_hint1.setTextColor(SkinsUtil.getColor(getContext(), "textcolor05"));
    }

    public void setErrorMessage(String msg) {
        tv_error_title.setText(msg);
    }

    public void setErrorImag(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_NO:
                mErrorState = NETWORK_NO;
                tv_error_title.setText(R.string.network_error_hint1);
                tv_error_info.setText(R.string.click_to_refresh);
                img.setBackgroundResource(R.mipmap.icon_lost_connect);
                img.setVisibility(View.VISIBLE);
                loading.stop();
                flag=false;
                loading.setVisibility(View.GONE);
                tv_error_title.setVisibility(VISIBLE);
                tv_error_info.setVisibility(VISIBLE);
                tv_error_btn.setVisibility(View.VISIBLE);
                break;
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                tv_error_title.setText(R.string.error_view_loading_fail);
                tv_error_info.setText(R.string.click_to_retry);
                img.setBackgroundResource(R.mipmap.icon_lost_connect_fail);
                img.setVisibility(View.VISIBLE);
                loading.stop();
                flag=false;
                loading.setVisibility(View.GONE);
                tv_error_title.setVisibility(VISIBLE);
                tv_error_info.setVisibility(VISIBLE);
                tv_error_btn.setVisibility(View.VISIBLE);
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                loading.setVisibility(View.VISIBLE);
                loading.start();
                img.setVisibility(View.GONE);
                tv_error_btn.setVisibility(GONE);
                flag=false;
                tv_error_title.setVisibility(GONE);
                tv_error_info.setVisibility(VISIBLE);
                tv_error_info.setText(R.string.error_view_loading);
                break;
            case NODATA:
                mErrorState = NODATA;
                img.setBackgroundResource(R.mipmap.icon_no_data);
                img.setVisibility(View.VISIBLE);
                loading.stop();
                loading.setVisibility(View.GONE);
                tv_error_btn.setVisibility(GONE);
                flag=true;
                tv_error_title.setVisibility(VISIBLE);
                tv_error_info.setVisibility(GONE);
                tv_error_title.setText(R.string.no_track_news_hint1);
                setTvNoDataContent();
                break;
            case HIDE_LAYOUT:
                loading.stop();
                setVisibility(View.GONE);
                flag=false;
                break;
            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                img.setBackgroundResource(R.mipmap.icon_no_data);
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                img.setVisibility(View.VISIBLE);
                loading.stop();
                loading.setVisibility(View.GONE);
                tv_error_title.setVisibility(VISIBLE);
                tv_error_info.setVisibility(GONE);
                flag=false;
//                tv_error_btn.setVisibility(VISIBLE);
                tv_error_btn.setVisibility(GONE);
                setTvNoDataContent();
                tv_error_title.setText(R.string.no_track_news_hint1);
                break;
            default:
                break;
        }
    }

    public void setStrNoDataHint1(String noDataContent) {
        strNoDatatitle = noDataContent;
    }

    public void setStrNoDataHint2(String noDataContent) {
        strNoDatainfo = noDataContent;
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (!strNoDatatitle.equals("")) {
            tv_error_info.setText(strNoDatatitle);
        } else {
            tv_error_info.setVisibility(GONE);
            tv_error_title.setVisibility(VISIBLE);
            tv_error_title.setText(R.string.error_view_no_data);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}
