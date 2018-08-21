package com.giiso.submmited.ui.fragment.child;

import android.support.v7.widget.RecyclerView;

import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.ui.base.fragment.BaseGeneralRecyclerFragment;
import com.giiso.submmited.ui.fragment.adapter.ExceptionAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ExceptionFragment extends BaseGeneralRecyclerFragment<Submmited> {


    public static ExceptionFragment newInstance() {
        return new ExceptionFragment();
    }

    @Override
    protected BaseRecyclerAdapter<Submmited> getRecyclerAdapter() {
        int mode = BaseRecyclerAdapter.NEITHER;
        return new ExceptionAdapter(mContext, mode);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }

    @Override
    protected Type getType() {
        return new TypeToken<List<Submmited>>(){}.getType();
    }

    @Override
    public void initData() {
        super.initData();
    }


}
