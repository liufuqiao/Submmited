package com.giiso.submmited.base;

import android.content.Context;
import android.os.Environment;

import com.giiso.submmited.utils.FileUtil;
import com.giiso.submmited.utils.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 应用程序配置类
 * 用于保存用户相关信息及设置
 */
public class AppConfig {

    public static final String APP_NAME = "jingbao";
    //讯飞语音配置
    public static final String VOICE_NAME = "xiaoqi";//"viyf";//设置发音人
    public static final String SPEED = "50";  //设置语速  范围0~100
    public static final String VOLUME = "80";  //设置音量，范围0~100
    //设置httpLog日志
    public static final boolean HTTP_LOG_OPEN = false;
    public final static int REQUEST_USERINFO = 0x11;
    public final static float TEXT_SIZE = 1.0f;//全局字体大小
    public final static float TEXT_SIZE_LARGE = 1.2f;//全局字体大大小
    public final static float TEXT_SIZE_HUGE = 1.5f;//全局字体巨大大小
    private final static String APP_CONFIG = "config";
    public final static String JPUSH_TYPE = "jpush_type";//极光推送true注册成功
    public final static String JPUSH_NUM = "jpush_num";//极光推送重试次数
    public final static String TEXT_SIZE_MULTIPLE = "text_size_multiple";//app字体大小倍数
    public final static int JPUSHNUM = 5;//极光推送最大重试次数
    public final static String DANMU = "danmu";//弹幕是否显示 true显示
    public final static String IS_FIRST_COMING = "isFirstComing";//第一次进入app
    public final static String IS_HOT_SHOW = "IS_HOT_SHOW";//是否显示热词
    public final static String VERSION_NUMBER = "versionNumber";//记录版本号，用于比对是否是新安装
    public static final String KEY_LOAD_IMAGE = "KEY_LOAD_IMAGE";
    public static final String KEY_NOTIFICATION_DISABLE_WHEN_EXIT = "KEY_NOTIFICATION_DISABLE_WHEN_EXIT";
    public static final String KEY_CHECK_UPDATE = "KEY_CHECK_UPDATE";
    public static final String KEY_DOUBLE_CLICK_EXIT = "KEY_DOUBLE_CLICK_EXIT";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    //通知权限标记
    public static final String MESSAGE_TAG = "message_tag";// 最近一次关闭的时间
    //是否第一次收藏
    public static final String FIRST_COLLECTION = "first_collection";// 是否第一次收藏
    //跟新升级时被拒绝时间隔
    public static final String NO_UPDATE_TIME_INTERVAL = "no_update_time_interval";
    //跟新升级时被拒绝状态
    public static final String UPDATE_TYPE = "update_type";
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "DailySunshine"
            + File.separator + "giiso_img" + File.separator;

    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "DailySunshine"
            + File.separator + "download" + File.separator;
    // 默认存放LOG文件路径
    public final static String DEFAULT_SAVE_LOG_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "DailySunshine"
            + File.separator + "log" + File.separator;
    //升级的安装包名，下载时需加版本号
    public final static String APP_FILE_NAME_TITLE = "DailySunshine";
    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    // 默认存放apk文件下载的路径
    public static String getApkFile(Context context) {
        return FileUtil.getFile(context) + "download" + File.separator;
    }

    // 默认存放apk文件下载的路径
    public static String getVideoCacheFile(Context context) {
        return FileUtil.getFile(context) + "video_cache" + File.separator;
    }

    // 默认存放LOG文件下载的路径
    public static String getLogFile(Context context) {
        return FileUtil.getFile(context) + "log" + File.separator;
    }

    // 默认存放图片的路径的路径
    public static String getImgFile(Context context) {
        return FileUtil.getFile(context) + "img" + File.separator;
    }
    // 默认存放语音的路径
    public static String getVoiceFile(Context context) {
        return FileUtil.getFile(context) + "voice" + File.separator;
    }

    public String get(String key) {
        Properties props = get();
        return (props != null) ? props.getProperty(key) : null;
    }

    public Properties get() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            // 读取app_config目录下的config
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator
                    + APP_CONFIG);

            props.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(fis);
        }
        return props;
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try {
            // 把config建在(自定义)app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            File conf = new File(dirConf, APP_CONFIG);
            fos = new FileOutputStream(conf);

            p.store(fos, null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(fos);
        }
    }

    public void set(String key, String value) {
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    public void remove(String... key) {
        Properties props = get();
        for (String k : key)
            props.remove(k);
        setProps(props);

        //R.string.releaseUrl
        //BuildConfig.API_SERVER_URL

        //BuildConfig.API_SERVER_URL
    }

}
