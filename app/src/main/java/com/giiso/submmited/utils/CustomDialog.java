package com.giiso.submmited.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.giiso.submmited.R;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CustomDialog extends Dialog {
    private Context context;
    private static CustomDialog customDialog;
    private View layout;
    private TextView tv_title;
    private TextView tv_msg;
    private TextView btn_left;
    private FrameLayout fl_right;
    private TextView btn_right;
    private String title;
    private String message;
    private String leftText;
    private String rightText;
    private OnClickListener leftClickListener, rightClickListener;
    private OnBackListener onBackListener;
    private boolean isSingleBtn = false;

    public CustomDialog(Context context) {
        super(context, R.style.MyDialogStyle1);
        this.context = context;
    }

    public void create() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.layout_dialog, null);
        tv_title = (TextView) layout.findViewById(R.id.tv_title);
        tv_msg = (TextView) layout.findViewById(R.id.tv_msg);
        btn_left = (TextView) layout.findViewById(R.id.btn_left);
        btn_right = (TextView) layout.findViewById(R.id.btn_right);
        View view = (View) layout.findViewById(R.id.v_divider);
        fl_right = (FrameLayout) layout.findViewById(R.id.fl_right);
        addContentView(layout, new ViewGroup.LayoutParams(TDevice.dip2px(context, 300), ViewGroup.LayoutParams.WRAP_CONTENT));
        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(message)) {
            tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(message);
        } else {
            tv_msg.setVisibility(View.GONE);
        }
        setLeftBtn();
        if (isSingleBtn) {
            view.setVisibility(View.GONE);
            fl_right.setVisibility(View.GONE);
        } else {
            setRightBtn();
        }
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (onBackListener != null) {
                        onBackListener.back();
                    }
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void show() {
        create();
        super.show();
    }

    private void setLeftBtn() {
        if (leftText != null) {
            btn_left.setText(leftText);
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (leftClickListener != null) {
                        leftClickListener.onClick(CustomDialog.this, DialogInterface.BUTTON_NEGATIVE);
                    }
                    CustomDialog.this.cancel();
                }
            });
        } else {
            layout.findViewById(R.id.btn_right).setVisibility(View.GONE);
        }
    }

    private void setRightBtn() {
        if (rightText != null) {
            btn_right.setText(rightText);
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightClickListener != null) {
                        rightClickListener.onClick(CustomDialog.this, DialogInterface.BUTTON_POSITIVE);
                    }
                    CustomDialog.this.cancel();
                }
            });
        } else {
            layout.findViewById(R.id.btn_right).setVisibility(View.GONE);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(int titleId) {
        this.title = context.getString(titleId);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(int msgId) {
        this.message = context.getString(msgId);
    }

    public void setLeftbtn(String text, OnClickListener listener) {
        leftText = text;
        leftClickListener = listener;
    }

    public void setLeftbtn(int textId, OnClickListener listener) {
        leftText = context.getString(textId);
        leftClickListener = listener;
    }

    public void setRightbtn(String text, OnClickListener listener) {
        rightText = text;
        rightClickListener = listener;
    }

    public void setRightbtn(int textId, OnClickListener listener) {
        rightText = context.getString(textId);
        rightClickListener = listener;
    }

    public void setIsSingleBtn(boolean isSingleBtn) {
        this.isSingleBtn = isSingleBtn;
    }

    public void setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }

    public interface OnBackListener {
        void back();
    }
}
