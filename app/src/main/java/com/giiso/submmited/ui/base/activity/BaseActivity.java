package com.giiso.submmited.ui.base.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.giiso.submmited.R;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.http.presenter.BaseView;
import com.giiso.submmited.utils.ActivityUtil;
import com.giiso.submmited.utils.CommonUtil;
import com.giiso.submmited.utils.DialogUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import skin.support.content.res.SkinCompatResources;

/**
 * Created by xwt
 * on 2017/12/25.
 */

public abstract class BaseActivity <T extends BasePresenter>  extends AppCompatActivity implements BaseView {
    public static final int unknown_application_installation = 0x998;
    public static final int statusBarType_Immerse = 0;
    public static final int statusBarType_none = 1;
    private boolean mIsDestroy;
    private Fragment mFragment;
    protected final String TAG = getClass().getSimpleName();
    protected boolean isUpdate = false;//是否刷新数据
    InputMethodManager inputMethodManager;

    protected Activity mContext;
    protected T mPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 返回子类布局设置
        if(getContentView() != 0){
            setContentView(getContentView());
        }
        //当前Activity Context
        mContext = this;
        // 初始化ButterKnife 注解框架
        unbinder = ButterKnife.bind(this);
        // 获取Presenter基类
        mPresenter = getPresenter();
        // 添加当前activity
        ActivityUtil.addActivity(this);
        //初始化视图
        initView();
        try {
            initBundle(savedInstanceState);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (!NetworkUtils.isConnected()) {
//            SimplexToast.showShort(this, getString(R.string.operate_error_hint_network));
//        }
    }

    /**
     * 获取布局View ID
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 返回Presenter基类
     * @return
     */
    protected T getPresenter() {
        return mPresenter;
    }

    /**
     * 返回Bundle数据对象还原数据
     * @param bundle
     * @throws JSONException
     */
    protected void initBundle(Bundle bundle) throws JSONException {}

    /**
     * 初始化数据
     */
    protected void initData() {}

//    @Override
//    public Resources getResources() {
//        Resources resources = super.getResources();
//       //全局字体设置
//        Configuration configuration = resources.getConfiguration();
//        if (BaseApplication.get(AppConfig.TEXT_SIZE_MULTIPLE, AppConfig.TEXT_SIZE) == AppConfig.TEXT_SIZE_HUGE) {
//            configuration.fontScale = AppConfig.TEXT_SIZE_HUGE;
//        } else {
//            configuration.fontScale = AppConfig.TEXT_SIZE;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////            DisplayMetrics displayMetrics = new DisplayMetrics();
////            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
////            displayMetrics.scaledDensity = configuration.fontScale*displayMetrics.density;
////            getBaseContext().getResources().updateConfiguration(configuration,displayMetrics);
//////            createConfigurationContext(configuration);
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        } else {
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
//        return resources;
//    }

    public int getStatusBarType() {
        return statusBarType_Immerse;
    }

    /**
     * 设置手机系统状态栏样式
     * @param type
     */
    public void setStatusBar(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View v_status = findViewById(R.id.v_status);
            if (v_status != null) {
                int statusBarHeight = CommonUtil.getStatusBarHeight(this);
                v_status.getLayoutParams().height = statusBarHeight;
            }
            if (type == statusBarType_none) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    initStatusBar();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
                    View statusBarView = new View(this);
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            CommonUtil.getStatusBarHeight(this));
                    statusBarView.setBackgroundColor(SkinCompatResources.getColor(this, R.color.colorPrimary));
                    contentView.addView(statusBarView, lp);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    initStatusBar();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
                    View statusBarView = new View(this);
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            CommonUtil.getStatusBarHeight(this));
                    statusBarView.setBackgroundColor(SkinCompatResources.getColor(this, R.color.colorPrimary));
                    contentView.addView(statusBarView, lp);
                }
            }
        }
    }

    /**
     * 初始化View
     */
    public void initView() {
        setStatusBar(getStatusBarType());
    }

    /**
     * 添加Fragment
     * @param frameLayoutId
     * @param fragment
     */
    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (mFragment == null) {
                transaction.add(frameLayoutId, fragment).commit();
            } else if (mFragment != fragment) {
                if (!fragment.isAdded()) {
                    transaction.hide(mFragment).add(frameLayoutId, fragment).commit();
                } else {
                    transaction.hide(mFragment).show(fragment).commit();
                }
            }
            mFragment = fragment;
        }
    }

    /**
     * 替换Fragment
     * @param frameLayoutId
     * @param fragment
     */
    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onPageResumeStatistics();
        if (isUpdate) {
            isUpdate = false;
            updateData();
        }
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    //刷新数据
    public void updateData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        onPagePauseStatistics();
    }

    public void onPageResumeStatistics() {
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    public void onPagePauseStatistics() {
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        mIsDestroy = true;
        super.onDestroy();
        dismissDialog();
        Handler handler = new Handler();
        handler.removeCallbacksAndMessages(null);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        ActivityUtil.removeActivity(this);
        unbinder.unbind();
    }

    protected void showDialog(){
        DialogUtil.showProgressDialog(this);
    }

    protected void dismissDialog() {
        DialogUtil.dismissDialog();
    }

    protected void hideSoftKeyboard() {
        if (getWindow() != null && getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(SkinCompatResources.getColor(this, R.color.colorPrimary));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void onErrorCode(ResultResponse resultResponse) {

    }

    @Override
    public void showLoadingFileDialog() {

    }

    @Override
    public void hideLoadingFileDialog() {

    }

    @Override
    public void onProgress(long totalSize, long downSize) {

    }
}
