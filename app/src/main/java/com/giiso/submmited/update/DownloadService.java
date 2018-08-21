package com.giiso.submmited.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;

import com.giiso.submmited.R;
import com.giiso.submmited.base.AppConfig;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.bean.Version;
import com.giiso.submmited.ui.MainActivity;
import com.giiso.submmited.ui.base.activity.BaseActivity;
import com.giiso.submmited.utils.ChannelUtil;
import com.giiso.submmited.utils.DialogUtil;
import com.giiso.submmited.utils.StreamUtil;
import com.giiso.submmited.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载服务
 * Created by haibin
 * on 2016/10/19.
 */
@SuppressWarnings("all")
public class DownloadService extends Service {
    public static boolean isDownload = false;

    private String mUrl;
    private String mTitle = "正在下载";
    //    private String saveFileName = AppConfig.DEFAULT_SAVE_FILE_PATH;
    private String saveFileName = AppConfig.getApkFile(this);

    private String name = AppConfig.APP_FILE_NAME_TITLE;//名

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    public boolean isInstall = true;
    public boolean flag = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mNotificationManager.cancel(0);
                    Version version = VersionHelper.getInstance().getVersion(DownloadService.this);
                    if (flag && version != null) {
                        boolean type = BaseApplication.get(AppConfig.UPDATE_TYPE, false);
                        BaseApplication.set(AppConfig.UPDATE_TYPE, false);
                        if (type)//根据上一次关闭升级界面时间间隔是否大于两个小时来决定弹出升级界面
                            UpdateActivity.show(DownloadService.this, version);
                    } else if (isInstall) {
                        DialogUtil.dismissDialog();
                        installApk();
                    }
                    break;
                case 1:
                    int rate = msg.arg1;
                    if (rate < 100) {
                        RemoteViews views = mNotification.contentView;
                        views.setTextViewText(R.id.tv_download_progress, mTitle + "(" + rate
                                + "%" + ")");
                        views.setProgressBar(R.id.pb_progress, 100, rate,
                                false);
                    } else {
                        // 下载完毕后变换通知形式
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        mNotification.contentView = null;
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        intent.putExtra("completed", "yes");

                        PendingIntent contentIntent = PendingIntent.getActivity(
                                getApplicationContext(), 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                    }
                    mNotificationManager.notify(0, mNotification);
                    break;
                case 2:
                    DialogUtil.dismissDialog();
                    ToastUtil.showToast(R.string.download_failure);
                    break;
            }

        }
    };

    public static void startService(Context context, String downloadUrl, String version, boolean isInstall, boolean flag) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("url", downloadUrl);
        intent.putExtra("is_install", isInstall);
        intent.putExtra("flag", flag);
        intent.putExtra("version", version);
        context.startService(intent);
    }

    public static void startService(Context context, String downloadUrl, String version, boolean isInstall) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("url", downloadUrl);
        intent.putExtra("is_install", isInstall);
        intent.putExtra("version", version);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isDownload = true;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            isInstall = intent.getBooleanExtra("is_install", true);
            mUrl = intent.getStringExtra("url");
            flag = intent.getBooleanExtra("flag", false);
            name = AppConfig.APP_FILE_NAME_TITLE + intent.getStringExtra("version") + ".apk";
            File file = new File(saveFileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            final File apkFile = new File(saveFileName + name);
            setUpNotification();
            new Thread() {
                @Override
                public void run() {
                    try {
                        downloadUpdateFile(mUrl, apkFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mHandler.sendEmptyMessage(2);
                    }
                }
            }.start();
        } else {
            mHandler.sendEmptyMessage(2);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[2048];
            int readSize = 0;
            while ((readSize = is.read(buffer)) > 0) {

                fos.write(buffer, 0, readSize);
                totalSize += readSize;
                if ((downloadCount == 0)
                        || (int) (totalSize * 100 / updateTotalSize) - 4 > downloadCount) {
                    downloadCount += 4;
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = downloadCount;
                    mHandler.sendMessage(msg);
                }
            }

            mHandler.sendEmptyMessage(0);
            isDownload = false;

        } catch (Exception e) {
            mHandler.sendEmptyMessage(2);
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            StreamUtil.close(is, fos);
            stopSelf();
        }
        return totalSize;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        BaseActivity baseActivity = BaseApplication.getInstance().getCurrentActivity();
        if (baseActivity != null) {
            baseActivity.startActivityForResult(intent, BaseActivity.unknown_application_installation);
        }
    }

    private void installApk() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startInstallPermissionSettingActivity();
                }
                return;
            }
        }
        File file = new File(saveFileName + name);
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

    private void setUpNotification() {

        int icon = R.mipmap.ic_launcher;
        CharSequence tickerText = "开始下载";
        long when = System.currentTimeMillis();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String marketId = ChannelUtil.getAppMetaData(DownloadService.this, ChannelUtil.UMENG_CHANNEL);
            NotificationChannel mChannel = new NotificationChannel(marketId, "channel_name", NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotification = new Notification.Builder(DownloadService.this, marketId).setTicker(tickerText).setWhen(when).setSmallIcon(R.mipmap.ic_launcher).build();
            mNotification.flags = Notification.FLAG_ONGOING_EVENT;
            RemoteViews contentView = new RemoteViews(getPackageName(),
                    R.layout.layout_notification_view);
            contentView.setTextViewText(R.id.tv_download_progress, mTitle);
            mNotification.contentView = contentView;
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mNotification.contentIntent = contentIntent;
            mNotificationManager.notify(0, mNotification);
        } else {
            mNotification = new Notification(icon, tickerText, when);

            mNotification.flags = Notification.FLAG_ONGOING_EVENT;
            RemoteViews contentView = new RemoteViews(getPackageName(),
                    R.layout.layout_notification_view);
            contentView.setTextViewText(R.id.tv_download_progress, mTitle);
            mNotification.contentView = contentView;

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotification.contentIntent = contentIntent;
            mNotificationManager.notify(0, mNotification);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        isDownload = false;
        super.onDestroy();
    }
}
