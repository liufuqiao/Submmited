package com.giiso.submmited.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.giiso.submmited.R;


/**
 * 加载等待Dialog（典型的菊花等待动效） 工具类
 * @author lyb 2017-08-20
 */
public class DialogUtil {

	private static Dialog progressDialog=null;
	private static DialogUtil mDialogUtil = null;
	
	public static synchronized  DialogUtil getInstance(){
		if (mDialogUtil == null) {
			mDialogUtil = new DialogUtil();
		}
		return mDialogUtil;
	}

	public static boolean isShowing(){
		if(progressDialog != null && progressDialog.isShowing()){
			return true;
		}
		return false;
	}
	
	/**
	 * 取消所有弹出的对话框
	 */
	public static void dismissDialog() {
		if (progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
		progressDialog = null;
	}

	/**
	 * 显示默认加载对话框（不带文字，可取消）
	 * 
	 * @param
	 * @param
	 * @param
	 */
	public  static void showProgressDialog(Context context) {
		showProgressDialog(context,null, Gravity.CENTER,true);
//		showFocusableProgressDialog(context,null,Gravity.CENTER);
	}

	public static void showProgressDialogWidthNoFocus(Context context){
		showProgressDialog(context,null, Gravity.CENTER,false);
	}

	public static void showProgressDialog(Context context, int gravity){
		showProgressDialog(context,null,gravity,true);
	}
	/**
	 * 显示进度对话框（带文字，不可取消）
	 * 
	 * @param
	 * @param message
	 * @param
	 */
	public static void showProgressDialog(Context context, String message) {
		showProgressDialog(context,message, Gravity.CENTER,true);
	}

	public static void showProgressDialog(Context context, String message, int gravity, boolean focusable){
		dismissDialog();
		if (context == null) {
			return ;
		}
		progressDialog = new Dialog(context, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.loading_dialog_layout);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(true);

		if(!focusable) {
			progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		}

		WindowManager.LayoutParams lp = progressDialog.getWindow().getAttributes();
		if(lp != null) {
			lp.gravity = gravity;
		}
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		if(!TextUtils.isEmpty(message)) {
			msg.setVisibility(View.VISIBLE);
			msg.setText(message);
		}else{
//			msg.setVisibility(View.GONE);
			msg.setVisibility(View.VISIBLE);
			msg.setText(context.getString(R.string.add_keyword_load));
		}
		progressDialog.show();
	}

	/**
	 * 显示提示语的对话框
	 * @param context
	 * @param message
     */
	public static void showTipDialog(Context context, String message){
//		dismissDialog();
//		progressDialog = new Dialog(context,R.style.dialog_with_alpha);
//		progressDialog.setContentView(R.layout.dialog_showtip);
////		progressDialog.setCancelable(false);
////		progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
////				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//		progressDialog.setCanceledOnTouchOutside(false);
//		progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//		TextView msg = (TextView) progressDialog.findViewById(R.id.tv_collect_success);
//		msg.setText(message);
//		progressDialog.show();
	}

}
