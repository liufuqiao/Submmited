package com.giiso.submmited.ui.base.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BasePresenter;
import com.giiso.submmited.http.presenter.BaseView;
import com.giiso.submmited.utils.CommonUtil;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基础类
 */

@SuppressWarnings("WeakerAccess")
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{
    public static final int statusBarType_none = 1;
    public static final int statusBarType_Immerse = 0;

    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;
    protected LayoutInflater mInflater;
    protected final String TAG = this.getClass().getSimpleName();
    InputMethodManager inputMethodManager;
    Unbinder unbinder;
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public int getStatusBarType() {
        return statusBarType_Immerse;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onMyHide();
        } else {
            onMyShow();
        }
    }

    // FragmentTransaction 调用show 回调
    protected void onMyShow() {

    }

    // FragmentTransaction 调用hide 回调
    protected void onMyHide() {
    }


    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            // Do something
            onBindViewBefore(mRoot);
            // Bind view
            unbinder = ButterKnife.bind(this, mRoot);
            // 获取Presenter基类
            mPresenter = getPresenter();
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }


    protected void onBindViewBefore(View root) {
        // ...
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBundle = null;
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected abstract int getLayoutId();

    /**
     * 返回Presenter基类
     * @return
     */
    protected T getPresenter() {
        return mPresenter;
    }

    protected void initBundle(Bundle bundle) {

    }

    public void setStatusBar(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View v_status = getView().findViewById(R.id.v_status);
            if (v_status != null) {
                int statusBarHeight = CommonUtil.getStatusBarHeight(getActivity());
                v_status.getLayoutParams().height = statusBarHeight;
            }
//            if (type == statusBarType_none) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getActivity().getWindow().setStatusBarColor(TDevice.getColor(getResources(), R.color.colorPrimaryDarkNone));
//                    return;
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                    ViewGroup contentView = (ViewGroup) getActivity().findViewById(android.R.id.content);
//                    View statusBarView = new View(getActivity());
//                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                            CommonUtil.getStatusBarHeight(getActivity()));
//                    statusBarView.setBackgroundColor(TDevice.getColor(getResources(), R.color.colorPrimaryDarkNone));
//                    contentView.addView(statusBarView, lp);
//                }
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Window window = getActivity().getWindow();
//                    window.setStatusBarColor(TDevice.getColor(getResources(), R.color.colorPrimaryDark));
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                    ViewGroup contentView = (ViewGroup) getActivity().findViewById(android.R.id.content);
//                    View statusBarView = new View(getActivity());
//                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                            CommonUtil.getStatusBarHeight(getActivity()));
//                    statusBarView.setBackgroundColor(TDevice.getColor(getResources(), R.color.colorPrimaryDark));
//                    contentView.addView(statusBarView, lp);
//                }
//            }
        }

    }

    public void initView() {
        setStatusBar(getStatusBarType());
    }

    protected void initWidget(View root) {

    }

    protected void initData() {

    }

    //刷新数据
    public void updateData() {

    }

    //皮肤切换刷新
    public void checkoutSkin() {

    }

    protected <T extends View> T findView(int viewId) {
        return (T) mRoot.findViewById(viewId);
    }

    protected <T extends Serializable> T getBundleSerializable(String key) {
        if (mBundle == null) {
            return null;
        }
        return (T) mBundle.getSerializable(key);
    }

    protected void setText(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
    }

    protected void setText(int viewId, String text, String emptyTip) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setText(emptyTip);
            return;
        }
        textView.setText(text);
    }

    protected void setTextEmptyGone(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setText(text);
    }

    protected <T extends View> T setGone(int id) {
        T view = findView(id);
        view.setVisibility(View.GONE);
        return view;
    }

    protected <T extends View> T setVisibility(int id) {
        T view = findView(id);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    protected void setInVisibility(int id) {
        findView(id).setVisibility(View.INVISIBLE);
    }

    protected void onRestartInstance(Bundle bundle) {

    }

    protected void hideSoftKeyboard() {
        if (getActivity() != null && getActivity().getWindow() != null && getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void noNetwork() {
    }

    public void yesNetwork() {
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
