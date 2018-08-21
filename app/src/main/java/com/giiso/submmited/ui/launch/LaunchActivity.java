package com.giiso.submmited.ui.launch;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.giiso.submmited.R;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.bean.Version;
import com.giiso.submmited.ui.LoginActivity;
import com.giiso.submmited.ui.MainActivity;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.ui.presenter.LoginPresenter;
import com.giiso.submmited.ui.presenter.LoginView;
import com.giiso.submmited.update.CheckUpdateManager;
import com.giiso.submmited.utils.CustomDialog;
import com.giiso.submmited.utils.PermissionUtils;
import com.giiso.submmited.utils.SingleDialogUtil;

import pub.devrel.easypermissions.EasyPermissions;
import skin.support.content.res.SkinCompatResources;

/**
 * 应用启动界面
 * 功能简介：处理6.0以上系统的权限适配
 *
 * @author lrz 2018-08-07
 */
public class LaunchActivity extends BaseActivity implements CheckUpdateManager.RequestPermissions{

    private static final int RC_READ_MOBILE_STATE = 123;

    boolean gotoSetting = false;
    private PermissionUtils permissionUtils;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        permissionUtils = new PermissionUtils(this);
        applyForRight();//6.0以上权限适配
    }

    /**
     * 获取权限后进行用户初始化相关操作
     */
    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userName = BaseApplication.get(Constants.USERNAME, null);
                String password = BaseApplication.get(Constants.PASSWORD, null);
                if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)){
                    MainActivity.show(LaunchActivity.this);
                } else {
                    LoginActivity.show(LaunchActivity.this);
                }
            }
        }, 2000);
    }

    protected void initStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(SkinCompatResources.getColor(this, R.color.transparent));
        }
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    //    @AfterPermissionGranted(RC_READ_MOBILE_STATE)
    public void applyForRight() {
        if (permissionUtils.checkPermissionMain(RC_READ_MOBILE_STATE)) {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionUtils == null) {
            return;
        }
        if (requestCode == RC_READ_MOBILE_STATE) {
            if (permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                init();
            } else {
                CustomDialog customDialog;
                customDialog = new CustomDialog(LaunchActivity.this);
                customDialog.setTitle(getString(R.string.reminder));
                customDialog.setMessage(getString(R.string.rationale_read));
                customDialog.setRightbtn(getString(R.string.go_open), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gotoSetting = true;
//                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", LaunchActivity.this.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", LaunchActivity.this.getPackageName());
                        }
                        startActivity(localIntent);
                    }
                });
                customDialog.setLeftbtn(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //AppRegister(true);
                    }
                });
                SingleDialogUtil.getInstance().show(customDialog);
            }
        }
    }

    @Override
    public void call(Version version) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gotoSetting) {
            gotoSetting = false;
            String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, perms)) {//用户已经授权
                init();
            }
        }
    }
}
