package com.giiso.submmited.ui.fragment.child;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.giiso.submmited.R;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.bean.base.PageBean;
import com.giiso.submmited.http.rxbus.RxBus;
import com.giiso.submmited.http.rxbus.RxBusMessage;
import com.giiso.submmited.ui.AddSbmmitedActivity;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.ui.base.fragment.BaseGeneralRecyclerFragment;
import com.giiso.submmited.ui.fragment.adapter.SubmmitedAdapter;
import com.giiso.submmited.ui.fragment.presenter.SubmmitedPresenter;
import com.giiso.submmited.ui.fragment.presenter.SubmmitedView;
import com.giiso.submmited.ui.widget.DividerItemDecoration;
import com.giiso.submmited.utils.DialogUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.reactivex.functions.Consumer;

/**
 * Created by LiuRiZhao on 2018/8/21.
 */

public class SubmmitedFragment extends BaseGeneralRecyclerFragment<Submmited> implements SubmmitedView, SubmmitedAdapter.OnSubmmitListener {

    private int pageNum = 0;
    private SubmmitedPresenter mPersenter;
    public static SubmmitedFragment newInstance() {
        return new SubmmitedFragment();
    }

    @Override
    protected BaseRecyclerAdapter<Submmited> getRecyclerAdapter() {
        int mode = BaseRecyclerAdapter.NEITHER;
        SubmmitedAdapter adapter = new SubmmitedAdapter(mContext, mode);
        adapter.setOnSubmmitListener(this);
        return adapter;
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
    protected void initWidget(View root) {
        super.initWidget(root);
        RxBus.getInstance().subscribe(RxBusMessage.class, new Consumer<RxBusMessage>() {
            @Override
            public void accept(RxBusMessage rxBusMessage) throws Exception {
                if(rxBusMessage.getMessage().equals(RxBusMessage.SUBMMITED_REFRESH)){
                    isRefreshing = true;
                    requestData();
                }
            }
        });
        mPersenter = new SubmmitedPresenter(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST, R.drawable.shape_list_divider_4dp));
    }

    @Override
    protected void requestData() {
        super.requestData();
        if(isRefreshing){
            pageNum = 1;
            getSubmmitedList();
        } else {
            pageNum ++;
            getSubmmitedList();
        }
    }

    private void getSubmmitedList(){
        String startTime = null;
        String endTime = null;
        String projectId = null;
        String name = null;
        mPersenter.getSubmmitedList(startTime, endTime, name, projectId, pageNum, Constants.PAGE_SIZE, mCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPersenter != null){
            mPersenter.detachView();
        }
    }

    @Override
    public void onDelete(final int id) {
      /*
      这里使用了 android.support.v7.app.AlertDialog.Builder
      可以直接在头部写 import android.support.v7.app.AlertDialog
      那么下面就可以写成 AlertDialog.Builder
      */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setMessage("是否确认删除当前日报");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPersenter.deleteSubmmited(id);
            }
        });
        builder.show();
    }

    @Override
    public void onUpdate(Submmited submmited) {
        AddSbmmitedActivity.show(mContext, submmited, true);
    }

    @Override
    public void deleteSuccess() {
        isRefreshing = true;
        requestData();
    }

    @Override
    public void showLoading() {
        super.showLoading();
        DialogUtil.showProgressDialog(mContext);
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
        DialogUtil.dismissDialog();
    }
}