package com.giiso.submmited.ui.fragment.child;


import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.bean.base.PageBean;
import com.giiso.submmited.http.rxbus.RxBus;
import com.giiso.submmited.http.rxbus.RxBusMessage;
import com.giiso.submmited.ui.DetailActivity;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.ui.base.fragment.BaseGeneralRecyclerFragment;
import com.giiso.submmited.ui.fragment.adapter.TaskAdapter;
import com.giiso.submmited.ui.fragment.presenter.TaskItemPresenter;
import com.giiso.submmited.ui.fragment.presenter.TaskItemView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 任务列表
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends BaseGeneralRecyclerFragment<Submmited> implements TaskAdapter.OnTaskListener, TaskItemView{

    private int pageNum = 0;
    private TaskItemPresenter mPresenter;
    private List<Submmited> submmitedList;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public void initView() {
        super.initView();
        RxBus.getInstance().subscribe(RxBusMessage.class, new Consumer<RxBusMessage>() {
            @Override
            public void accept(RxBusMessage rxBusMessage) throws Exception {
                if(rxBusMessage.getMessage().equals(RxBusMessage.TASK_LIST_REFRESH)){
                    requestData();
                }
            }
        });
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST, R.drawable.shape_list_divider_4dp));
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPresenter = new TaskItemPresenter(this);
    }

    @Override
    protected BaseRecyclerAdapter<Submmited> getRecyclerAdapter() {
        int mode = BaseRecyclerAdapter.NEITHER;
        TaskAdapter taskAdapter = new TaskAdapter(mContext, mode);
        taskAdapter.setOnTaskListener(this);
        return taskAdapter;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        super.onItemClick(position, itemId);
        DetailActivity.show(mContext, submmitedList.get(position).getId(), true);
    }

    @Override
    protected void setListDataBefore(List<Submmited> lists) {
        super.setListDataBefore(lists);
        submmitedList = lists;
    }

    @Override
    protected void requestData() {
        super.requestData();
        if(isRefreshing){
            pageNum = 1;
            getAllList();
        } else {
            pageNum ++;
            getAllList();
        }
    }

    private void getAllList(){
        String type = "0";
        String typeStatus = null;
        String userName = null;
        String createId = null;
        String startTime = null;
        String endTime = null;
        String projectId = null;
        mPresenter.getTaskList(type, typeStatus, userName, createId, startTime, endTime, projectId, pageNum, Constants.PAGE_SIZE, mCallback);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }

    @Override
    protected Type getType() {
        return new TypeToken<PageBean<Submmited>>(){}.getType();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubcribe();
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }

    @Override
    public void onTaskStop(int id) {
        mPresenter.startOrStopTask(id, "3");
    }

    @Override
    public void onTaskStart(int id) {
        mPresenter.startOrStopTask(id, "0");
    }

    @Override
    public void onSuccess() {
        requestData();
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
    }
}
