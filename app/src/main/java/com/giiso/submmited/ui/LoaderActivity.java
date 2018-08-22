package com.giiso.submmited.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.bean.Menus;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.ui.fragment.child.ExceptionFragment;
import com.giiso.submmited.ui.fragment.child.SubmmitedFragment;
import com.giiso.submmited.ui.fragment.child.TaskFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class LoaderActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Menus.MenuItem item;
    @Override
    protected int getContentView() {
        return R.layout.giiso_loader;
    }

    public static void show(Context context, Menus.MenuItem item){
        Intent intent = new Intent(context, LoaderActivity.class);
        intent.putExtra("item", item);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        super.initView();
        item = (Menus.MenuItem) getIntent().getSerializableExtra("item");
        tvTitle.setText(item.getName());
        if(item.getId() == 43){//任务列表
            addFragment(R.id.fl_content, TaskFragment.newInstance());
        } else if(item.getId() == 44){
            addFragment(R.id.fl_content, SubmmitedFragment.newInstance());
        } else if(item.getId() == 55){
            addFragment(R.id.fl_content, ExceptionFragment.newInstance());
        }
    }

    @OnClick({R.id.ib_close, R.id.ib_search})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ib_close:
                finish();
                break;
            case R.id.ib_search:
                break;
        }
    }

}
