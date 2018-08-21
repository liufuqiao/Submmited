package com.giiso.submmited.bean.account;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import com.giiso.submmited.bean.account.bean.User;
import com.giiso.submmited.cache.SharedPreferencesHelper;
import com.giiso.submmited.utils.Log;


/**
 * 账户辅助类，
 * 用于更新用户信息和保存当前账户等操作
 */
public final class AccountHelper {
    private static final String TAG = AccountHelper.class.getSimpleName();
    public static final String WOMEN_STRING = "1";
    public static final String MEN_STRING = "0";
    public static final String WOMEN = "女";
    public static final String MEN = "男";
    private User user;
    private Application application;
    @SuppressLint("StaticFieldLeak")
    private static AccountHelper instances;

    private AccountHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        if (instances == null)
            instances = new AccountHelper(application);
        else {
            // reload from source
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, User.class);
            Log.d(TAG, "init reload:" + instances.user);
        }
    }

    public static boolean isLogin() {
        User user = getUser();
        if (user == null || TextUtils.isEmpty(user.getToken())
                || TextUtils.isEmpty(user.getUid()) || user.getUserType() == -1) {
            return false;
        }
        return true;
    }

    public static boolean isTourist() {
        User user = getUser();
        if (user == null || TextUtils.isEmpty(user.getToken())
                || TextUtils.isEmpty(user.getUid())) {
            return false;
        }
        return true;
    }

    public static String getUid() {
        String uid = getUser() == null ? "12" : getUser().getUid();
        return uid;
    }

    public static String getVid() {
        String vid = getUser() == null ? "" : getUser().getVid();
        return vid;
    }

    public static String getToken() {
        String token = getUser() == null ? "" : getUser().getToken();
        return token;
    }

    public static String getUserName() {
        String name = getUser() == null ? "" : getUser().getUserName();
        return name;
    }

    public static String getNikeName() {
        String name = getUser() == null ? "" : getUser().getNikeName();
        return name;
    }

    public static String getDescription() {
        String description = getUser() == null ? "" : getUser().getDescription();
        return description;
    }

    public static boolean getIsFingerprint() {
        if (getUser() == null) {
            return false;
        }
        return TextUtils.isEmpty(getUser().getFingerCode()) ? false : true;
    }

    public static String getSexString() {
        String sex;
        if (getUser() == null) {
            sex = MEN_STRING;
        } else if (getUser().getSex().equals(MEN)) {
            sex = MEN_STRING;
        } else {
            sex = WOMEN_STRING;
        }
        return sex;
    }

    public static String getSex() {
        String sex = getUser() == null ? "" : getUser().getSex();
        return sex;
    }

    public static boolean isDaka() {
        if (getUser() != null && getUser().getUserType() == 1)
            return true;
        return false;
    }

    public static String getBirthday() {
        String birthday = getUser() == null ? "" : getUser().getBirthday();
        return birthday;
    }

    public static String getAddr() {
        String addr = getUser() == null ? "" : getUser().getArea();
        return addr;
    }


    //第三方登录返回的id,如果是自己本方短信登陆也会传自己的uid上去用于判断是否不是匿名登陆
    public static String getOpenID() {
        String openID = getUser() == null ? "" : getUser().getQqOpenId();
        if (TextUtils.isEmpty(openID)) {
            openID = getUser() == null ? "" : getUser().getWbOpenId();
        }
        if (TextUtils.isEmpty(openID)) {
            openID = getUser() == null ? "" : getUser().getWbOpenId();
        }
        return openID;
    }


    public static int getPlatCode() {
        int openID = getUser() == null ? 0 : getUser().getPlatCode();
        return openID;
    }

    public static String getHeadimgUrl() {
        String headImgUrl = getUser() == null ? "" : getUser().getHeadimgUrl();
        return headImgUrl;
    }

    public static String getCookie() {
        String cookie = getUser() == null ? "" : getUser().getToken();
        return cookie == null ? "" : cookie;
    }

    public static String getMobile() {
        String mobile = getUser() == null ? "" : getUser().getMobile();
        return mobile == null ? "" : mobile;
    }

    public static String getWX() {
        String wx = (getUser() == null || getUser().getWeixinBind() == null) ? "1" : getUser().getWeixinBind();
        return wx;
    }

    public static String getWB() {
        String wb = (getUser() == null || getUser().getWeiboBind() == null) ? "1" : getUser().getWeiboBind();
        return wb;
    }

    public static String getQQ() {
        String qq = (getUser() == null || getUser().getQqBind() == null) ? "1" : getUser().getQqBind();
        return qq;
    }

    public synchronized static User getUser() {
        if (instances == null) {
            Log.error("AccountHelper instances is null, you need call init() method.");
            return new User();
        }
        if (instances.user == null) {
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, User.class);
        }
        if (instances.user == null) {
            instances.user = null;
        }
        return instances.user;
    }

    public static boolean updateUserCache(User user) {
        if (user == null)
            return false;
        instances.user = user;
        return SharedPreferencesHelper.save(instances.application, user);
    }

    private static void clearUserCache() {
        instances.user = null;
        SharedPreferencesHelper.remove(instances.application, User.class);
    }

//    public static boolean anonymousLogins(final User user, Header[] headers) {
//        // 更新Cookie
//        String cookie = ApiHttpClient.getCookie(headers);
//        if (TextUtils.isEmpty(cookie) || cookie.length() < 6) {
//            return false;
//        }
//
//        TLog.d(TAG, "anonymousLogins:" + user + " cookie：" + cookie);
//        int count = 10;
//        boolean saveOk;
//        // 保存缓存
//        while (!(saveOk = updateUserCache(user)) && count-- > 0) {
//            SystemClock.sleep(100);
//        }
//
//        if (saveOk) {
////            ApiHttpClient.setCookieHeader(getCookie());
//            // 登陆成功,重新启动消息服务
////            NoticeManager.init(instances.application);
//        }
//        return saveOk;
//    }

    /**
     * 退出登陆清楚用户数据
     */
    public static void logout() {
        // 清除用户缓存
        clearUserCache();
    }

    /**
     * 退出登陆操作需要传递一个View协助完成延迟检测操作
     *
     * @param view     View
     * @param runnable 当清理完成后回调方法
     */
    public static void logout(final View view, final Runnable runnable) {
        // 清除用户缓存
        clearUserCache();
        // 等待缓存清理完成
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.removeCallbacks(this);
                User user = SharedPreferencesHelper.load(instances.application, User.class);
                // 判断当前用户信息是否清理成功
                if (user == null || user.getId() <= 0) {
                    clearAndPostBroadcast(instances.application);
                    runnable.run();
                } else {
                    view.postDelayed(this, 200);
                }
            }
        }, 200);
    }

    /**
     * 当前用户信息清理完成后调用方法清理服务等信息
     *
     * @param application Application
     */
    private static void clearAndPostBroadcast(Application application) {
        // 清理网络相关
//        ApiHttpClient.destroyAndRestore(application);
    }
}
