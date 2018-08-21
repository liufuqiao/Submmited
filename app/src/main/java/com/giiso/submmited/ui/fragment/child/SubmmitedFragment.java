package com.giiso.submmited.ui.fragment.child;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.giiso.submmited.R;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.bean.base.PageBean;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.ui.base.fragment.BaseGeneralRecyclerFragment;
import com.giiso.submmited.ui.fragment.adapter.SubmmitedAdapter;
import com.giiso.submmited.ui.fragment.presenter.SubmmitedPresenter;
import com.giiso.submmited.ui.fragment.presenter.SubmmitedView;
import com.giiso.submmited.ui.widget.DividerItemDecoration;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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
        return new SubmmitedAdapter(mContext, mode);
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
    public void onDelete(int id) {

    }

    @Override
    public void onDetail(int id) {

    }

    @Override
    public void onUpdate(int id) {

    }
}
