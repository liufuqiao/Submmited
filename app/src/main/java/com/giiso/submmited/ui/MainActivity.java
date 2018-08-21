package com.giiso.submmited.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.giiso.submmited.R;
import com.giiso.submmited.http.rxbus.RxBus;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.ui.fragment.ItemsFragment;
import com.giiso.submmited.ui.fragment.MemberFragment;
import com.giiso.submmited.ui.fragment.MyFragment;
import com.giiso.submmited.ui.fragment.SubmmitedFragment;
import com.giiso.submmited.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity{

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rl_first_layout)
    RelativeLayout rlFirstLayout;
    @BindView(R.id.rl_second_layout)
    RelativeLayout rlSecondLayout;
    @BindView(R.id.rl_third_layout)
    RelativeLayout rlThirdLayout;
    @BindView(R.id.rl_four_layout)
    RelativeLayout rlFourLayout;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private SubmmitedFragment mSubmmitedFragment;
    private ItemsFragment mItemsFragment;
    private MemberFragment mMemberFragment;
    private MyFragment mMyFragment;

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.giiso_main;
    }

    @Override
    public void initView() {
        init();
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        //初始化默展示
        rlFirstLayout.setSelected(true);
        mSubmmitedFragment = SubmmitedFragment.newInstance();
        mTransaction.replace(R.id.fl_content, mSubmmitedFragment);
        mTransaction.commit();

    }

    @OnClick({R.id.rl_first_layout, R.id.rl_second_layout, R.id.rl_third_layout, R.id.rl_four_layout})
    public void onClick(View view){
        mTransaction = mFragmentManager.beginTransaction(); //开启事务
        hideAllFragment(mTransaction);
        selectedView();
        switch (view.getId()){
            case R.id.rl_first_layout:
                rlFirstLayout.setSelected(true);
                if(mSubmmitedFragment == null){
                    mSubmmitedFragment = SubmmitedFragment.newInstance();
                    mTransaction.add(R.id.fl_content, mSubmmitedFragment);
                } else {
                    mTransaction.show(mSubmmitedFragment);
                }
                break;
            case R.id.rl_second_layout:
                rlSecondLayout.setSelected(true);
                if(mItemsFragment == null){
                    mItemsFragment = ItemsFragment.newInstance();
                    mTransaction.add(R.id.fl_content, mItemsFragment);
                } else {
                    mTransaction.show(mItemsFragment);
                }
                break;
            case R.id.rl_third_layout:
                rlThirdLayout.setSelected(true);
                if(mMemberFragment == null){
                    mMemberFragment = MemberFragment.newInstance();
                    mTransaction.add(R.id.fl_content, mMemberFragment);
                } else {
                    mTransaction.show(mMemberFragment);
                }
                break;
            case R.id.rl_four_layout:
                rlFourLayout.setSelected(true);
                if(mMyFragment == null){
                    mMyFragment = MyFragment.newInstance();
                    mTransaction.add(R.id.fl_content, mMyFragment);
                } else {
                    mTransaction.show(mMyFragment);
                }
                break;
        }
        mTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mSubmmitedFragment != null) {
            transaction.hide(mSubmmitedFragment);
        }
        if (mItemsFragment != null) {
            transaction.hide(mItemsFragment);
        }
        if (mMemberFragment != null) {
            transaction.hide(mMemberFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
    }

    private void selectedView(){
        rlFirstLayout.setSelected(false);
        rlSecondLayout.setSelected(false);
        rlThirdLayout.setSelected(false);
        rlFourLayout.setSelected(false);
    }

    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        long currentTimeMillis = System.currentTimeMillis();
        if ((currentTimeMillis - firstTime) > 2000) {
            ToastUtil.showToast(R.string.tip_double_click_exit);
            firstTime = currentTimeMillis;
        } else {
            finish();
        }
    }

}
