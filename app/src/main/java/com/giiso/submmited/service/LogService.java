package com.giiso.submmited.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Constants;
import com.giiso.submmited.base.Content;
import com.giiso.submmited.bean.log.ErrorLog;
import com.giiso.submmited.db.LogDao;
import com.giiso.submmited.http.HttpContext;
import com.giiso.submmited.http.ResultResponse;
import com.giiso.submmited.http.presenter.BaseObserver;
import com.giiso.submmited.http.retrofit.RetrofitServiceManager;
import com.giiso.submmited.ui.api.SubmmitedApi;
import com.giiso.submmited.utils.AppInfoTidUtil;
import com.giiso.submmited.utils.ChannelUtil;
import com.giiso.submmited.utils.TDevice;
import com.giiso.submmited.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/10/16.
 */

public class LogService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String ACTION_UPLOAD_LOG = " com.giiso.dailysunshine.improve.service.intentservice.action.UPLOAD_LOG";

    public LogService(String name) {
        super(name);
    }

    public LogService() {
        super("LogService");
    }

    public static void startLogService(Context context) {
        Intent intent = new Intent(context, LogService.class);
        intent.setAction(ACTION_UPLOAD_LOG);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_LOG.equals(action)) {
                //读取文件
                List<ErrorLog> logs = null;
                try {
                    logs = LogDao.getInstance(LogService.this).queryAll();
                    if (logs != null) {
                        for (int i = 0; i < logs.size(); i++) {
                            uploadLog(logs.get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将本地日志上传至服务器
     */
    public void uploadLog(final ErrorLog log) {
        String marketId = ChannelUtil.getAppMetaData(LogService.this, ChannelUtil.UMENG_CHANNEL);
        String tid = AppInfoTidUtil.getInstance().getTid();
        String imei = Utils.getImei(BaseApplication.getInstance());
        String ip = TDevice.getIp(LogService.this);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(HttpContext.OS, Content.ANDROID);
        map.put(HttpContext.OS_VERSION, Build.VERSION.RELEASE);
        map.put(HttpContext.IMEI, imei);
        if (!TextUtils.isEmpty(ip)) {
            map.put(HttpContext.IP, ip);
        }
        map.put(HttpContext.MARKETID, marketId);
        map.put(HttpContext.BRAND, Build.BRAND);
        map.put(HttpContext.TID, tid);
        map.put(HttpContext.MESSAGE, log.getMeaagse());

        SubmmitedApi api = RetrofitServiceManager.getInstance().create(SubmmitedApi.class);
        api.uploadLog(map)
            .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
            .observeOn(Schedulers.io())//请求在新的线程中执行
            .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
            .subscribe(new BaseObserver<ResultResponse>() {

                @Override
                public void onSuccess(ResultResponse o) {
                    String code = o.getCode();
                    if (code.equals(Constants.RESPONSE_SUCCESS_CODE)) {
                        LogDao.getInstance(LogService.this).delect(log.getId());
                    }
                }

                @Override
                public void onError(int code, String msg) {

                }

            });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
