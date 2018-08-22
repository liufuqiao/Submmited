package com.giiso.submmited.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.ui.presenter.DetailPresenter;
import com.giiso.submmited.ui.presenter.DetailView;

import butterknife.BindView;

/**
 * Created by LiuRiZhao on 2018/8/22.
 */

public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailView{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.et_task_desc)
    EditText etTaskDesc;
    @BindView(R.id.tv_task_status)
    TextView tvTaskStatus;
    @BindView(R.id.tv_task_type)
    TextView tvTaskType;
    @BindView(R.id.tv_task_predict_hour)
    TextView tvTaskPredictHour;
    @BindView(R.id.tv_task_progress)
    TextView tvTaskProgress;
    @BindView(R.id.tv_task_responseible)
    TextView tvTaskResponseible;
    @BindView(R.id.tv_task_create)
    TextView tvTaskCreate;
    @BindView(R.id.tv_task_create_time)
    TextView tvTaskCreateTime;
    @BindView(R.id.tv_task_star_time)
    TextView tvTaskStarTime;
    @BindView(R.id.tv_task_end_time)
    TextView tvTaskEndTime;
    @BindView(R.id.ll_start_date)
    LinearLayout ll_start_date;
    @BindView(R.id.ll_end_date)
    LinearLayout ll_end_date;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    private boolean isTask;
    private ProgressDialog progressDialog;

    public static void show(Context mContext, int id, boolean isTask){
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", id);
        intent.putExtra("isTask", isTask);
        mContext.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.submmited_detail;
    }

    @Override
    protected DetailPresenter getPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        int id = getIntent().getIntExtra("id", 0);
        isTask = getIntent().getBooleanExtra("isTask", false);
        if(isTask){
            mTvTitle.setText(R.string.title_task_detail);
        } else {
            mTvTitle.setText(R.string.title_submmited_detail);
        }
        mPresenter.getTaskDetail(id);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        progressDialog = ProgressDialog.show(this, "", "加载中...", false, true);
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showDetail(Submmited submmited) {
        if(submmited != null){
            tvItem.setText(submmited.getProjectName());
            etTaskDesc.setText(submmited.getName());
            tvTaskStatus.setText(submmited.getTaskStatus());
            tvTaskType.setText(submmited.getTypeName());
            tvTaskPredictHour.setText(submmited.getTaskTime() + "小时");
            tvTaskProgress.setText(submmited.getPercentComplete() + "%");
            tvTaskResponseible.setText(submmited.getUserName());
            tvTaskCreate.setText(submmited.getCreateName());
            tvTaskCreateTime.setText(submmited.getCreateTime());
            if(isTask){
                tvTaskStarTime.setText(submmited.getExpectStartTime());
                tvTaskEndTime.setText(submmited.getExpectFinishTime());
            } else {
                ll_start_date.setVisibility(View.GONE);
                ll_end_date.setVisibility(View.GONE);
            }
        }
    }
}
