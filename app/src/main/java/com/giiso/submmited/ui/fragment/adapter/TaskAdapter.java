package com.giiso.submmited.ui.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.ui.AddSbmmitedActivity;
import com.giiso.submmited.ui.AddTaskActivity;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.ui.fragment.DetailInfoDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/15.
 */

public class TaskAdapter extends BaseRecyclerAdapter<Submmited> {

    //status : 任务状态 0进行中 1已完成 2延期 3暂停 4未启动 5 已确认
    // 1 计划 2 临时任务

    public TaskAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SubmmitedHolder(mInflater.inflate(R.layout.item_task, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final Submmited item, int position) {
        SubmmitedHolder submmitedHolder = (SubmmitedHolder) holder;
        submmitedHolder.tv_item.setText(item.getProjectName());
        submmitedHolder.tv_task_desc.setText(item.getName());
        submmitedHolder.tv_task_status.setText(item.getTaskStatus());
        submmitedHolder.tv_task_progress.setText(TextUtils.isEmpty(item.getPercentComplete()) ? "0" : item.getPercentComplete() + "%");
        submmitedHolder.tv_task_time.setText(item.getExpectFinishTime());
        submmitedHolder.tv_task_responseible.setText(item.getUserName());
        if(item.getStatus() == Submmited.IN_PROGRESS || item.getStatus() == Submmited.POSTPONE){
            submmitedHolder.tv_stop.setVisibility(View.VISIBLE);
            submmitedHolder.tv_start.setVisibility(View.GONE);
        } else if(item.getStatus() == Submmited.STOP){
            submmitedHolder.tv_start.setVisibility(View.VISIBLE);
            submmitedHolder.tv_stop.setVisibility(View.GONE);
        } else if(item.getStatus() == Submmited.NO_START){
            submmitedHolder.tv_stop.setVisibility(View.GONE);
            submmitedHolder.tv_start.setVisibility(View.VISIBLE);
            submmitedHolder.tv_update.setVisibility(View.VISIBLE);
            submmitedHolder.tv_delete.setVisibility(View.VISIBLE);
        }

        submmitedHolder.tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailInfoDialog.showDetail(mContext, item);
            }
        });
        submmitedHolder.tv_submmited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSbmmitedActivity.show(mContext, item, false);
            }
        });
        submmitedHolder.tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskActivity.show(mContext, item.getProjectId(), true);
            }
        });
        submmitedHolder.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTaskListener != null){
                    mOnTaskListener.onConfirm(item.getId());
                }
            }
        });
        submmitedHolder.tv_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTaskListener != null) {
                    mOnTaskListener.onTaskStop(item.getId());
                }
            }
        });
        submmitedHolder.tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTaskListener != null) {
                    mOnTaskListener.onTaskStart(item.getId());
                }
            }
        });
    }


    class SubmmitedHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item)
        TextView tv_item;
        @BindView(R.id.tv_task_desc)
        TextView tv_task_desc;
        @BindView(R.id.tv_task_status)
        TextView tv_task_status;
        @BindView(R.id.tv_task_time)
        TextView tv_task_time;
        @BindView(R.id.tv_delete)
        TextView tv_delete;
        @BindView(R.id.tv_update)
        TextView tv_update;
        @BindView(R.id.tv_task_responseible)
        TextView tv_task_responseible;
        @BindView(R.id.tv_task_progress)
        TextView tv_task_progress;
        @BindView(R.id.tv_submmited)
        TextView tv_submmited;
        @BindView(R.id.tv_start)
        TextView tv_start;
        @BindView(R.id.tv_stop)
        TextView tv_stop;
        @BindView(R.id.tv_confirm)
        TextView tv_confirm;
        @BindView(R.id.tv_details)
        TextView tv_details;

        public SubmmitedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnTaskListener mOnTaskListener;

    public void setOnTaskListener(OnTaskListener mOnTaskListener) {
        this.mOnTaskListener = mOnTaskListener;
    }

    public interface OnTaskListener{
        void onTaskStop(int id);
        void onTaskStart(int id);
        void onConfirm(int id);
    }
}
