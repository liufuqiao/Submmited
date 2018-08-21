package com.giiso.submmited.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.bean.Submmited;

/**
 * Created by Administrator on 2018/8/21.
 */

public class DetailInfoDialog {

    @SuppressLint("SetTextI18n")
    public static void showDetail(final Context mContext, Submmited submmited) {
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
        setBackgroundAlpha(mContext, 0.5f);
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
                setBackgroundAlpha(mContext, 1.0f);
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
    private static void setBackgroundAlpha(Context mContext, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
