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
import com.giiso.submmited.ui.DetailActivity;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/15.
 */

public class SubmmitedAdapter extends BaseRecyclerAdapter<Submmited> {

    //status : 任务状态 0进行中 1已完成 2延期 3暂停 4未启动 5 已确认
    // 1 计划 2 临时任务

    public SubmmitedAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SubmmitedHolder(mInflater.inflate(R.layout.item_submmited, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final Submmited item, int position) {
        SubmmitedHolder submmitedHolder = (SubmmitedHolder) holder;
        submmitedHolder.tvTask.setText(item.getName());
        submmitedHolder.tvTaskAffiliation.setText(item.getProjectName());
        submmitedHolder.tvTaskHours.setText(item.getTaskTime());
        submmitedHolder.tvTaskProgress.setText(TextUtils.isEmpty(item.getPercentComplete()) ? "0" : item.getPercentComplete() + "%");
        submmitedHolder.tvTaskTime.setText(item.getRealFinishTime());
        submmitedHolder.tvTaskRemark.setText(item.getDescription());

        submmitedHolder.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnSubmmitedListener != null){
                    mOnSubmmitedListener.onUpdate(item);
                }
            }
        });

        submmitedHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnSubmmitedListener != null){
                    mOnSubmmitedListener.onDelete(item.getId());
                }
            }
        });

        submmitedHolder.tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.show(mContext, item.getId(), false);
            }
        });
    }

    class SubmmitedHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_task)
        TextView tvTask;
        @BindView(R.id.tv_task_affiliation)
        TextView tvTaskAffiliation;
        @BindView(R.id.tv_task_hours)
        TextView tvTaskHours;
        @BindView(R.id.tv_task_progress)
        TextView tvTaskProgress;
        @BindView(R.id.tv_task_time)
        TextView tvTaskTime;
        @BindView(R.id.tv_task_remark)
        TextView tvTaskRemark;
        @BindView(R.id.tv_update)
        TextView tvUpdate;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_details)
        TextView tvDetails;

        SubmmitedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnSubmmitListener mOnSubmmitedListener;

    public void setOnSubmmitListener(OnSubmmitListener mOnSubmmitedListener) {
        this.mOnSubmmitedListener = mOnSubmmitedListener;
    }

    public interface OnSubmmitListener {
        void onDelete(int id);

        void onUpdate(Submmited submmited);
    }
}
