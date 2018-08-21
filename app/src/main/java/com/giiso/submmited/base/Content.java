package com.giiso.submmited.base;

/**
 * Created by Administrator on 2017/9/12.
 */

public class Content {
    public final static int ANDROID = 0;
    public final static String ANDROID_STRING = "android";
    public final static int LOCATIONCHANNELINDEX = 1;//定位频道的位置
    public final static String WIFI_IMG = "wifi_img";//wifi下是否加载图片
    public final static String SKIN = "skin";
    public final static String NIGHT = "night";  //皮肤包名称
    public final static String CHANNEL_TAB = "channel_tab";
    public final static String FIELD_TAB = "field_tab";
    public final static String STRATEGY_LIST = "strategyList";
    public final static String BUNDLE = "bundle";
    public final static String JUMP_PAGE = "jumpPage";//引导页进入需要跳转的页面
    public final static int ADDKEY = 1;//添加追踪词界面
    public final static int MAIN = 2;//main主界面
    public final static String GIISO = "giiso";
    public final static String MY_CHANNEL = "55";
    public final static String APP_NAME = "com.zhisou.wentianji";
    public final static String PACKAGE = "package";

    public final static String SYSTEM_DOMAIN = "_system_domain";//系统领域缓存名后缀
    public final static String HOT_TRACK = "_hot_track";//热词缓存名后缀
    public final static String BARRAGE = "_barrage";//热弹幕缓存名后缀
    public final static String CONFLICT_KEYWORD = "conflict_keyword";
    public final static String CONFLICT_FIELD = "conflict_field";

    //   收藏关闭侧边栏
    public final static int COLLECTION_CLOSE_DRAWERLAYOUT = 10003;
    public final static String IS_SHOW = "1";
    public final static String CODE = "code";
    public final static int CODE_NO = 0;
    public final static int CODE_YES = 1;
    public final static int CODE_XF_YES = 3;//是否启用讯飞语音
    public final static int CODE_ADD_CHANNEL = 2;
    //   登陆成功刷新数据,清楚缓存的标识
    public final static int LOGIN_YES = 10001;
    //   用户信息刷新
    public final static int USER_INFO_REST_YES = 10002;
    //   收藏状态改变表识
    public final static int COLLECTION_FLAG = 10003;

    public final static String ABOUT_US = "关于我们";//关于我们
    public final static String USER_AGREEMENT = "用户协议";//用户协议

    public static boolean getSkin() {
        return BaseApplication.get(Content.SKIN, "").equals(Content.NIGHT);
    }
}
