package com.giiso.submmited.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.giiso.submmited.R;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.EmployeeBean;
import com.giiso.submmited.bean.ProjectBean;
import com.giiso.submmited.http.rxbus.RxBus;
import com.giiso.submmited.http.rxbus.RxBusMessage;
import com.giiso.submmited.ui.adapter.SpinnerAdapter;
import com.giiso.submmited.ui.adapter.SpinnerBean;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.ui.presenter.TaskPresenter;
import com.giiso.submmited.ui.presenter.TaskView;
import com.giiso.submmited.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加或修改任务
 */
public class AddTaskActivity extends BaseActivity<TaskPresenter> implements TaskView{

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.spinner_type)
    Spinner spinnerType;
    @BindView(R.id.spinner_person)
    Spinner spinnerPerson;
    @BindView(R.id.ed_date_start)
    EditText ed_date_start;
    @BindView(R.id.ed_date_end)
    EditText ed_date_end;
    @BindView(R.id.et_task)
    EditText etTask;
    @BindView(R.id.et_hours)
    EditText etHours;

    private int mYear;
    private int mMonth;
    private int mDay;
    private ArrayList<SpinnerBean> plans = new ArrayList<>();
    private ArrayList<SpinnerBean> singles = new ArrayList<>();
    private ArrayList<SpinnerBean> employees = new ArrayList<>();
    private SpinnerAdapter singleAdapter;
    private SpinnerAdapter employeeAdapter;

    private boolean isUpdate = false;
    private String planType;
    private int userId;
    private String name;
    private int projectId;
    private String percentComplete;
    public static void show(Context context, int id, boolean isUpdate){
        Intent intent = new Intent(context, AddTaskActivity.class);
        intent.putExtra("projectId", id);
        intent.putExtra("isUpdate", isUpdate);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.giiso_add_task;
    }

    @Override
    protected TaskPresenter getPresenter() {
        return new TaskPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        initSpinner();
        projectId = getIntent().getIntExtra("projectId", 0);
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mPresenter.getSingleProjectList();
        mPresenter.getEmployeeList(projectId);
    }

    private void initSpinner() {
        //计划适配器
        plans.add(new SpinnerBean(1, "计划"));
        plans.add(new SpinnerBean(2, "临时任务"));
        SpinnerAdapter adapter = new SpinnerAdapter(AddTaskActivity.this, plans);
        //加载适配器
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                planType = plans.get(position).getId() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //项目选择适配器
        singleAdapter = new SpinnerAdapter(AddTaskActivity.this, singles);
        //加载适配器
        spinner.setAdapter(singleAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = singles.get(position).getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //负责人适配器
        employeeAdapter = new SpinnerAdapter(AddTaskActivity.this, employees);
        //加载适配器
        spinnerPerson.setAdapter(employeeAdapter);
        spinnerPerson.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userId = employees.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ensure, R.id.ed_date_start, R.id.ed_date_end})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_ensure:
                String name = etTask.getText().toString().trim();
                String taskTime = etHours.getText().toString().trim();
                String expectStartTime = ed_date_start.getText().toString().trim();
                String expectFinishTime = ed_date_end.getText().toString().trim();
                break;
            case R.id.ed_date_start:
                new DatePickerDialog(AddTaskActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.ed_date_end:
                new DatePickerDialog(AddTaskActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
        }
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    if(TextUtils.isEmpty(ed_date_start.getText().toString().trim())){
                        ed_date_start.setText(StringUtils.formatDate(year, monthOfYear, dayOfMonth));
                    } else {
                        ed_date_end.setText(StringUtils.formatDate(year, monthOfYear, dayOfMonth));
                    }
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
    public void resultProject(ArrayList<ProjectBean> list) {
        for (ProjectBean bean : list){
            singles.add(new SpinnerBean(bean.getProjectId(), bean.getProjectName()));
        }
        singleAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultEmployee(ArrayList<EmployeeBean> list) {
        for (EmployeeBean bean : list){
            employees.add(new SpinnerBean(bean.getId(), bean.getName()));
        }
        employeeAdapter.notifyDataSetChanged();
        int k= employeeAdapter.getCount();
        for(int i=0;i<k;i++){
            if(employeeAdapter.getItem(i).getId() == BaseApplication.get(Constants.USER_ID, -1)){
                spinnerPerson.setSelection(i,true);
                break;
            }
        }
    }

    @Override
    public void addTaskSuccess() {
        RxBusMessage message = new RxBusMessage(0, RxBusMessage.TASK_LIST_REFRESH);
        RxBus.getInstance().send(message);
    }
}
