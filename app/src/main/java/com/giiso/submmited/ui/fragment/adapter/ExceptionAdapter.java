package com.giiso.submmited.ui.fragment.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.bean.Submmited;
import com.giiso.submmited.ui.AddSbmmitedActivity;
import com.giiso.submmited.ui.AddTaskActivity;
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
                showDetail(item);
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

    @SuppressLint("SetTextI18n")
    private void showDetail(Submmited submmited) {
        //准备PopupWindow的布局View
        View view = LayoutInflater.from(mContext).inflate(R.layout.submmited_detail, null);
        ((TextView)view.findViewById(R.id.tv_item)).setText(submmited.getProjectName());
        ((TextView)view.findViewById(R.id.tv_task_desc)).setText(submmited.getName());
        ((TextView)view.findViewById(R.id.tv_task_status)).setText(submmited.getTaskStatus());
        ((TextView)view.findViewById(R.id.tv_task_type)).setText(submmited.getComment_status());
        ((TextView)view.findViewById(R.id.tv_task_predict_hour)).setText(submmited.getTaskTime() + "小时");
        ((TextView)view.findViewById(R.id.tv_task_progress)).setText(submmited.getPercentComplete() + "%");
        ((TextView)view.findViewById(R.id.tv_task_responseible)).setText(submmited.getUserName());
        ((TextView)view.findViewById(R.id.tv_task_create)).setText(submmited.getCreateName());
        ((TextView)view.findViewById(R.id.tv_task_create_time)).setText("");
        ((TextView)view.findViewById(R.id.tv_task_star_time)).setText(submmited.getExpectStartTime());
        ((TextView)view.findViewById(R.id.tv_task_end_time)).setText(submmited.getExpectFinishTime());

        //初始化一个PopupWindow，width和height都是WRAP_CONTENT
        final PopupWindow popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow的视图内容
        popupWindow.setContentView(view);
        //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00FFFFFF));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //设置PopupWindow动画
        popupWindow.setAnimationStyle(R.style.AnimHorizontal);
        //设置是否允许PopupWindow的范围超过屏幕范围
        popupWindow.setClippingEnabled(true);
        setBackgroundAlpha(0.5f);
        view.findViewById(R.id.ib_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //设置PopupWindow消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        //PopupWindow在targetView下方弹出
        popupWindow.showAtLocation(((Activity)mContext).getWindow().getDecorView(), Gravity.BOTTOM,0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
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
