package com.giiso.submmited.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.http.rxbus.RxBus;
import com.giiso.submmited.http.rxbus.RxBusMessage;
import com.giiso.submmited.ui.adapter.SpinnerAdapter;
import com.giiso.submmited.ui.adapter.SpinnerBean;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.ui.presenter.SubmmitedPresenter;
import com.giiso.submmited.ui.presenter.SubmmitedView;
import com.giiso.submmited.utils.StringUtils;
import com.giiso.submmited.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class AddSbmmitedActivity extends BaseActivity<SubmmitedPresenter> implements SubmmitedView{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;
    @BindView(R.id.ed_date)
    EditText edDate;
    @BindView(R.id.et_task_affiliation)
    EditText et_task_affiliation;
    @BindView(R.id.et_task)
    EditText etTask;
    @BindView(R.id.et_task_type)
    EditText etTaskType;
    @BindView(R.id.spinner_status)
    Spinner spinnerStatus;
    @BindView(R.id.et_hours)
    EditText etHours;
    @BindView(R.id.et_complete_progress)
    EditText etCompleteProgress;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.ll_task_status)
    LinearLayout ll_task_status;
    @BindView(R.id.ll_task_type)
    LinearLayout ll_task_type;

    private Submmited submmited;
    private String status;
    private boolean isUpdate;
    private int mYear;
    private int mMonth;
    private int mDay;

    public static void show(Context context, Submmited submmited, boolean isUpdate){
        Intent intent = new Intent(context, AddSbmmitedActivity.class);
        intent.putExtra("submmited", submmited);
        intent.putExtra("isUpdate", isUpdate);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.giiso_add_sbmmited;
    }

    @Override
    protected SubmmitedPresenter getPresenter() {
        return new SubmmitedPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        submmited = (Submmited) getIntent().getSerializableExtra("submmited");
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        if(!isUpdate){
            mTvTitle.setText("写日报");
        } else {
            mTvTitle.setText("修改日报");
            ll_task_status.setVisibility(View.GONE);
            ll_task_type.setVisibility(View.GONE);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initSpinner();
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void initData() {
        super.initData();
        edDate.setText(StringUtils.formatDate(mYear, mMonth, mDay));
        if(submmited != null){
            et_task_affiliation.setText(submmited.getProjectName());
            etTask.setText(submmited.getName());
            etTaskType.setText(submmited.getComment_status());
            etHours.setText(submmited.getTaskTime());
            etCompleteProgress.setText(submmited.getPercentComplete());
            etRemark.setText(submmited.getDescription());
        }
    }

    private void initSpinner() {
        final ArrayList<SpinnerBean> plans = new ArrayList<>();
        //status : 任务状态 0进行中 1已完成 2延期 3暂停 4未启动 5 已确认
        plans.add(new SpinnerBean(0, "进行中"));
        plans.add(new SpinnerBean(1, "已完成"));
        plans.add(new SpinnerBean(2, "延期"));
        plans.add(new SpinnerBean(3, "暂停"));
        plans.add(new SpinnerBean(4, "未启动"));
        plans.add(new SpinnerBean(5, "已确认"));
        SpinnerAdapter adapter = new SpinnerAdapter(AddSbmmitedActivity.this, plans);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = plans.get(position).getId() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int k= adapter.getCount();
        for(int i=0;i<k;i++){
            if(adapter.getItem(i).getId() == submmited.getStatus()){
                spinnerStatus.setSelection(i,true);
                break;
            }
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ensure, R.id.ed_date})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_ensure:
                int id = submmited.getId();
                String date = edDate.getText().toString().trim();
                String hours = etHours.getText().toString().trim();
                String progress = etCompleteProgress.getText().toString().trim();
                String remark = etRemark.getText().toString().trim();
                if(isUpdate){
                    mPresenter.updateSubmmited(id, progress, hours, remark, date);
                } else {
                    mPresenter.addSubmmited(id, date, hours, status, progress, remark);
                }
                break;
            case R.id.ed_date:
                new DatePickerDialog(AddSbmmitedActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
        }
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            edDate.setText(StringUtils.formatDate(year, monthOfYear, dayOfMonth));
        }
    };

    @Override
    public void showLoading() {
        super.showLoading();
        showDialog();
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
        dismissDialog();
    }

    @Override
    public void addSubmmitedSuccess() {
        ToastUtil.showToast("添加日报成功");
        finish();
    }

    @Override
    public void updateSuccess() {
        RxBus.getInstance().send(new RxBusMessage(0, RxBusMessage.SUBMMITED_REFRESH));
        finish();
    }
}
