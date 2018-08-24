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
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/15.
 */

public class ExceptionAdapter extends BaseRecyclerAdapter<Submmited> {

    //status : 任务状态 0进行中 1已完成 2延期 3暂停 4未启动 5 已确认
    // 1 计划 2 临时任务

    public ExceptionAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SubmmitedHolder(mInflater.inflate(R.layout.item_exception, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final Submmited item, int position) {
        SubmmitedHolder holder1 = (SubmmitedHolder) holder;
        holder1.tvTaskAffiliation.setText(item.getProjectName());
        holder1.tvTask.setText(item.getName());
        holder1.tvTaskHours.setText(item.getTaskTime());
        holder1.tvTaskPracticalHours.setText(item.getTaskTimeCount());
        holder1.tvTaskProgress.setText(TextUtils.isEmpty(item.getPercentComplete()) ? "0" : item.getPercentComplete() + "%");
        holder1.tvTaskStatus.setText(item.getTaskStatus());
        holder1.tvTaskStartTime.setText(item.getExpectStartTime());
        holder1.tvTaskEndTime.setText(item.getExpectFinishTime());
        holder1.tvTaskPerson.setText(item.getUserName());
    }

    class SubmmitedHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_task_affiliation)
        TextView tvTaskAffiliation;
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.tv_task)
        TextView tvTask;
        @BindView(R.id.tv_task_hours)
        TextView tvTaskHours;
        @BindView(R.id.tv_task_practical_hours)
        TextView tvTaskPracticalHours;
        @BindView(R.id.tv_task_progress)
        TextView tvTaskProgress;
        @BindView(R.id.tv_task_status)
        TextView tvTaskStatus;
        @BindView(R.id.tv_task_start_time)
        TextView tvTaskStartTime;
        @BindView(R.id.tv_task_end_time)
        TextView tvTaskEndTime;
        @BindView(R.id.tv_task_person)
        TextView tvTaskPerson;
        public SubmmitedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
