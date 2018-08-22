package com.giiso.submmited.update;

import android.app.ProgressDialog;
import android.content.Context;

import com.giiso.submmited.R;
import com.giiso.submmited.base.AppConfig;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.bean.Version;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.retrofit.RetrofitServiceManager;
import com.giiso.submmited.ui.api.SubmmitedApi;
import com.giiso.submmited.utils.ChannelUtil;
import com.giiso.submmited.utils.CustomDialog;
import com.giiso.submmited.utils.DialogHelper;
import com.giiso.submmited.utils.SingleDialogUtil;
import com.giiso.submmited.utils.TDevice;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by xwt
 * on 2017/8/20.
 */
public class CheckUpdateManager {
    public static final long TIME = 1000 * 60 * 60 * 24;

    private ProgressDialog mWaitDialog;
    private Context mContext;
    private boolean mIsShowDialog;
    private RequestPermissions mCaller;
    private OnUpdate onUpdate;


    public CheckUpdateManager(Context context, boolean showWaitingDialog) {
        this.mContext = context;
        mIsShowDialog = showWaitingDialog;
        if (mIsShowDialog) {
            mWaitDialog = DialogHelper.getProgressDialog(mContext);
            mWaitDialog.setMessage("正在检查中...");
            mWaitDialog.setCancelable(true);
            mWaitDialog.setCanceledOnTouchOutside(true);
        }
    }

    public boolean checkCodeName(String versionName) {
        String[] strings = versionName.split("[.]");
        String[] myStrings = TDevice.getVersionName().split("[.]");
        int size = strings.length > myStrings.length ? strings.length : myStrings.length;
        for (int i = 0; i < size; i++) {
            int m, n;
            if (strings.length > i) {
                m = Integer.valueOf(strings[i]);
            } else {
                m = 0;
            }
            if (myStrings.length > i) {
                n = Integer.valueOf(myStrings[i]);
            } else {
                n = 0;
            }
            if (n > m) {
                return false;
            }
            if (i == size - 1 && n == m) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCode(int versionCode) {
        if (versionCode > TDevice.getVersionCode()) {
            return true;
        }
        return false;
    }

    public void checkUpdate(String version, final String app_name, String app_id) {
        if (mIsShowDialog) {
            mWaitDialog.show();
        }
        String marketId = ChannelUtil.getAppMetaData(mContext, ChannelUtil.UMENG_CHANNEL);
        SubmmitedApi serviceManager = RetrofitServiceManager.getInstance().create(SubmmitedApi.class);
        serviceManager.checkUpdate(version, app_name, app_id, marketId)
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())//请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new BaseObserver<ResultResponse>() {

                    @Override
                    public void onSuccess(ResultResponse resultResponse) {
                        if (resultResponse.isSuccess()) {
                            final Version version = (Version) resultResponse.getData();
                            if (checkCode(version.getVersionCode())) {
                                Version versionOld = VersionHelper.getInstance().getVersion(mContext);
                                if (versionOld == null || version.getVersionCode() > versionOld.getVersionCode()) {
                                    BaseApplication.set(AppConfig.UPDATE_TYPE, true);
                                }
                                VersionHelper.getInstance().updateVersionCache(mContext, version);
                                if (onUpdate != null) {
                                    onUpdate.upadte(version);
                                }
                            } else {
                                VersionHelper.getInstance().clearVersionCache(mContext);
                                if (mIsShowDialog) {
                                    CustomDialog customDialog;
                                    customDialog = new CustomDialog(mContext);
                                    customDialog.setTitle("已经是新版本了");
                                    customDialog.setLeftbtn(mContext.getString(R.string.ensure), null);
                                    customDialog.setIsSingleBtn(true);
                                    SingleDialogUtil.getInstance().show(customDialog);
                                }
                            }
                        } else {
                            if (mIsShowDialog) {
                                CustomDialog customDialog;
                                customDialog = new CustomDialog(mContext);
                                customDialog.setTitle("已经是新版本了");
                                customDialog.setLeftbtn(mContext.getString(R.string.ensure), null);
                                customDialog.setIsSingleBtn(true);
                                SingleDialogUtil.getInstance().show(customDialog);
                            }
                        }

                        if (mWaitDialog != null) {
                            mWaitDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        if (mIsShowDialog) {
                            CustomDialog customDialog;
                            customDialog = new CustomDialog(mContext);
                            customDialog.setTitle("已经是新版本了");
                            customDialog.setLeftbtn(mContext.getString(R.string.ensure), null);
                            customDialog.setIsSingleBtn(true);
                            SingleDialogUtil.getInstance().show(customDialog);
                        }
                        if (mWaitDialog != null) {
                            mWaitDialog.dismiss();
                        }
                    }
                });
    }

    public void setCaller(RequestPermissions caller) {
        this.mCaller = caller;
    }

    public void setOnUpdate(OnUpdate onUpdate) {
        this.onUpdate = onUpdate;
    }

    public interface RequestPermissions {
        void call(Version version);
    }

    public interface OnUpdate {
        void upadte(Version version);
    }
}
