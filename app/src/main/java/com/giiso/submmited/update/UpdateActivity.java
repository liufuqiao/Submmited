package com.giiso.submmited.update;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.giiso.submmited.R;
import com.giiso.submmited.base.AppConfig;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.bean.Version;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.utils.CustomDialog;
import com.giiso.submmited.utils.DialogUtil;
import com.giiso.submmited.utils.FileUtil;
import com.giiso.submmited.utils.PermissionUtils;
import com.giiso.submmited.utils.SingleDialogUtil;
import com.giiso.submmited.utils.TDevice;
import com.giiso.submmited.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 在线更新对话框
 * Created by lyb on 2017/8/30.
 */

public class UpdateActivity extends BaseActivity implements View.OnClickListener {
    public final static String FORCE = "0";//0 非强制升级
    public final static String VERSION = "version";//版本跟新参数
    public final static String TYPE = "type";//是否是手动跟新

    //    private String saveFileName = AppConfig.DEFAULT_SAVE_FILE_PATH;
    private String saveFileName = AppConfig.getApkFile(this);
    private String name = AppConfig.APP_FILE_NAME_TITLE;
    private String fileName;

    @BindView(R.id.tv_update_info)
    TextView mTextUpdateInfo;
    @BindView(R.id.btn_update)
    TextView btn_update;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_version_text)
    TextView tv_version_text;
    @BindView(R.id.iv_break)
    ImageView iv_break;

    private Version mVersion;
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    private static final int RC_EXTERNAL_STORAGE2 = 0x05;//存储权限
    private static final int RC_EXTERNAL_STORAGE3 = 0x06;//存储权限
    private int position = 0;//0本地没有跟新包，1 本地已经存在跟新包
    private int type;
    boolean isInstall;
    String version;

    private PermissionUtils permissionUtils;

    public static void show(Activity activity, Version version) {
        Intent intent = new Intent(activity, UpdateActivity.class);
        intent.putExtra(VERSION, version);
        activity.startActivityForResult(intent, 0x01);
        activity.overridePendingTransition(R.anim.out_anim, R.anim.in_anim);
    }

    public static void show(Activity activity, Version version, int type) {
        Intent intent = new Intent(activity, UpdateActivity.class);
        intent.putExtra(VERSION, version);
        intent.putExtra(TYPE, type);
        activity.startActivityForResult(intent, 0x01);
        activity.overridePendingTransition(R.anim.out_anim, R.anim.in_anim);
    }

    public static void show(Context context, Version version) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(VERSION, version);
        context.startActivity(intent);
    }

    @Override
    public int getStatusBarType() {
        return statusBarType_none;
    }

    @Override
    protected int getContentView() {
        return R.layout.giiso_update;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initData() {
        super.initData();
        permissionUtils = new PermissionUtils(this);
        setTitle("");
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mVersion = (Version) getIntent().getSerializableExtra(VERSION);
        type = getIntent().getIntExtra(TYPE, 0);
        if (!TextUtils.isEmpty(mVersion.getRemark())) {
//            mTextUpdateInfo.setText(Html.fromHtml(mVersion.getRemark()));
            tv_title.setText(mVersion.getRemark());
            mTextUpdateInfo.setText(mVersion.getRemark());
        }else {
            tv_title.setText("");
            mTextUpdateInfo.setText("");
        }
        tv_version_text.setText("V" + mVersion.getVersion());
        fileName = saveFileName + name + mVersion.getVersion() + ".apk";
        if (mVersion.getForce().equals(FORCE)) {
            iv_break.setVisibility(View.VISIBLE);
        } else {
            iv_break.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        position = checkFile();
        checkoutText();
    }

    public void checkoutText() {
        if (position == 0) {
            btn_update.setText(R.string.upgrade_now);
        } else {
            btn_update.setText(R.string.install_now);
        }
    }

    public int checkFile() {
        if (FileUtil.checkFileExists(fileName)) {
            return 1;
        } else {
            return 0;
        }
    }

    @OnClick({R.id.btn_update, R.id.fl_root, R.id.iv_break})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_break:
                onBackPressed();
                break;
            case R.id.btn_update:
                if (position == 0) {
                    isInstall = true;
                    version = mVersion.getVersion();
                    requestExternalStorage();
                } else {
                    if (permissionUtils.checkPermissionWrite(RC_EXTERNAL_STORAGE2)) {
                        md5VerifyInstallation(fileName);
                    }
                }
                break;
            case R.id.fl_root:
                if (mVersion.getForce().equals(FORCE)) {
                    if (position == 0) {
                        if (TDevice.isWifiOpen()) {
                            isInstall = false;
                            version = mVersion.getVersion();
                            requestExternalStorage();
                        }
                    }
                    if (type != 1) {
                        BaseApplication.set(AppConfig.NO_UPDATE_TIME_INTERVAL, System.currentTimeMillis());//关闭升级界面设置时间间隔
                    }
                    finish();
                }
                break;
        }
    }

    public void requestExternalStorage() {
        if (permissionUtils.checkPermissionWrite(RC_EXTERNAL_STORAGE)) {
            DialogUtil.showProgressDialog(this, getString(R.string.loading));
            DownloadService.startService(this, mVersion.getPackageUrl(), version, isInstall);
            if (mVersion.getForce().equals(FORCE)) {
                ToastUtil.showToast(R.string.update_application_in_the_background);
                finish();
            }
        }
    }

    public void requestExternalStorageInstall() {
        if (permissionUtils.checkPermissionWrite(RC_EXTERNAL_STORAGE3)) {
            DownloadService.startService(this, mVersion.getPackageUrl(), version, isInstall);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionUtils == null) {
            return;
        }
        if (requestCode == RC_EXTERNAL_STORAGE) {
            if (permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                requestExternalStorage();
            } else {
                CustomDialog customDialog;
                customDialog = new CustomDialog(UpdateActivity.this);
                customDialog.setTitle(getString(R.string.reminder));
                customDialog.setMessage(getString(R.string.you_need_to_open_the_secret_message));
                customDialog.setRightbtn(getString(R.string.go_open), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", UpdateActivity.this.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", UpdateActivity.this.getPackageName());
                        }
                        startActivity(localIntent);
                    }
                });
                customDialog.setLeftbtn(getString(R.string.cancel), null);
                SingleDialogUtil.getInstance().show(customDialog);
            }
        } else if (requestCode == RC_EXTERNAL_STORAGE2) {
            if (permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                md5VerifyInstallation(fileName);
            } else {
                CustomDialog customDialog;
                customDialog = new CustomDialog(UpdateActivity.this);
                customDialog.setTitle(getString(R.string.reminder));
                customDialog.setMessage(getString(R.string.you_need_to_open_the_secret_message));
                customDialog.setRightbtn(getString(R.string.go_open), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", UpdateActivity.this.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", UpdateActivity.this.getPackageName());
                        }
                        startActivity(localIntent);
                    }
                });
                customDialog.setLeftbtn(getString(R.string.cancel), null);
                SingleDialogUtil.getInstance().show(customDialog);
            }
        } else if (requestCode == RC_EXTERNAL_STORAGE3) {
            if (permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                requestExternalStorageInstall();
            } else {
                CustomDialog customDialog;
                customDialog = new CustomDialog(UpdateActivity.this);
                customDialog.setTitle(getString(R.string.reminder));
                customDialog.setMessage(getString(R.string.you_need_to_open_the_secret_message));
                customDialog.setRightbtn(getString(R.string.go_open), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", UpdateActivity.this.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", UpdateActivity.this.getPackageName());
                        }
                        startActivity(localIntent);
                    }
                });
                customDialog.setLeftbtn(getString(R.string.cancel), null);
                SingleDialogUtil.getInstance().show(customDialog);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mVersion.getForce().equals(FORCE)) {
            if (type != 1) {
                BaseApplication.set(AppConfig.NO_UPDATE_TIME_INTERVAL, System.currentTimeMillis());//关闭升级界面设置时间间隔
            }
            if (position == 0 && TDevice.isWifiOpen())//点击返回键关闭是偷偷下载
            {
                isInstall = false;
                version = mVersion.getVersion();
                File file = new File(fileName);
                if (!file.exists())
                    requestExternalStorageInstall();
            }
            super.onBackPressed();
            overridePendingTransition(R.anim.out_anim, R.anim.in_anim);
        }
    }

    private void md5VerifyInstallation(final String name) {
        DialogUtil.showProgressDialog(this);
        new Thread() {
            @Override
            public void run() {
                try {
                    final String md5 = FileUtil.getFileMd5(name);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.dismissDialog();
                            if (TextUtils.isEmpty(md5)) {
                                position = 0;
                                checkoutText();
                                ToastUtil.showToast(R.string.app_file_no_existent);
                            } else if (md5.equals(mVersion.getFilemd5())) {
                                installApk(name);
                            } else {
                                position = 0;
                                checkoutText();
                                FileUtil.deleteFile(fileName);
                                ToastUtil.showToast(R.string.app_file_damage);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        BaseActivity baseActivity = BaseApplication.getInstance().getCurrentActivity();
        if (baseActivity != null) {
            baseActivity.startActivityForResult(intent, unknown_application_installation);
        }
    }

    private void installApk(String name) {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                CustomDialog customDialog;
                customDialog = new CustomDialog(UpdateActivity.this);
                customDialog.setTitle(getString(R.string.reminder));
                customDialog.setMessage(getString(R.string.you_need_to_open_the_secret_no_message));
                customDialog.setRightbtn(getString(R.string.go_open), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startInstallPermissionSettingActivity();
                        }
                    }
                });
                customDialog.setLeftbtn(getString(R.string.cancel), null);
                SingleDialogUtil.getInstance().show(customDialog);
                return;
            }
        }
        File file = new File(name);
        if (!file.exists())
            return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.giiso.dailysunshine.fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }

    protected void initStatusBar() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }
}
