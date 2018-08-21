package cn.sharesdk;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


//import com.giiso.dailysunshine.R;
import com.giiso.submmited.R;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static com.mob.tools.utils.Strings.getString;
//import cn.sharesdk.onekeyshare.OnekeyShare;
//import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
//import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by Administrator on 2017/9/8.
 */

public class ShareHelper {
    final static String SUFFIX = "&isShare=1";
    final static String SUFFIX_FIRST = "isShare=1";
    final static String WEIXIN_SHARE = "&Type=0";
    final static String WEIXIN_MOMENTS_SHARE = "&Type=1";
    final static String QQ_SHARE = "&Type=2";
    final static String QQ_ZONE_SHARE = "&Type=3";
    final static String WEIBO_SHARE = "&Type=4";
    public final static String QQ = "qq";
    public final static String WEIXIN = "wx";
    public final static String WEIBO = "wb";

    static ShareHelper shareHelper;

    public static synchronized ShareHelper getInstance() {
        if (shareHelper == null)
            shareHelper = new ShareHelper();
        return shareHelper;
    }

    public void showShare(final Context context, String platform, final String title, final String text, final String picurl, final String url, final String detailsUrl) {
        final OnekeyShare oks = new OnekeyShare(); //关闭sso授权
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
        if (!TextUtils.isEmpty(url)) {
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(url);
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(url);
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(url);
        } else {
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("");
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("");
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("");
        }
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(picurl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
//        //添加复制链接
//        oks.setCustomerLogo(BitmapFactory.decodeResource(context.getResources(), R.mipmap.search), context.getString(R.string.app_name), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                // 创建普通字符型ClipData
//                ClipData mClipData = ClipData.newPlainText("text", detailsUrl);
//                // 将ClipData内容放到系统剪贴板里。
//                cm.setPrimaryClip(mClipData);
//                SimplexToast.showShort(context, R.string.app_name);
//            }
//        });
//        //隐藏新浪微博
//        oks.addHiddenPlatform(SinaWeibo.NAME);
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
//                if (platform.getClass().getName().equals(WechatMoments.class.getName())) {
//                    paramsToShare.setTitle(title);
//                }  else if (platform.getClass().getName().equals(Wechat.class.getName())) {
//                    paramsToShare.setTitle(title);
//                }
                paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//                String str = picurl;
//                str = str.toLowerCase();
                if (TextUtils.isEmpty(picurl)) {
//                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            paramsToShare.setImageData(((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher, null)).getBitmap());
                        } else {
                            paramsToShare.setImageData(((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                else if (!(str.contains("png") || str.contains("jpg"))) {
//                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//                }
            }
        });
        oks.setImageUrl(picurl);
        // 启动分享
        oks.show(context);
    }

    public void showShare(final Context context, final String title, final String text, final String picurl, final String shareUrl) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_share, null);
        final ShareButtomDialogView shareButtomDialogView = new ShareButtomDialogView(context, view, false, false);
        shareButtomDialogView.show();
        final String url;
        if (("" + shareUrl).contains("?")) {
            url = shareUrl + SUFFIX;
        } else {
            url = shareUrl + "?" + SUFFIX_FIRST;
        }
        TextView tv_friends_cir = view.findViewById(R.id.tv_friends_cir);
        TextView tv_wechat = view.findViewById(R.id.tv_wechat);
        TextView tv_webo = view.findViewById(R.id.tv_webo);
        TextView tv_qq = view.findViewById(R.id.tv_qq);
        TextView tv_qq_zone = view.findViewById(R.id.tv_qq_zone);
        TextView tv_cannel = view.findViewById(R.id.tv_cannel);
        tv_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareButtomDialogView.dismiss();
            }
        });
        tv_friends_cir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtil.showProgressDialog(context);
                showShare(context, WechatMoments.NAME, title, text, picurl, url + WEIXIN_MOMENTS_SHARE, url + WEIXIN_MOMENTS_SHARE);
                shareButtomDialogView.dismiss();
            }
        });
        tv_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtil.showProgressDialog(context);
//                String picurl = "http://pic.616pic.com/ys_b_img/00/13/14/wC1quFW3yA.jpg";
//                String picurl = "http://data.whicdn.com/images/59615673/large.gif";
                showShare(context, Wechat.NAME, title, text, picurl, url + WEIXIN_SHARE, url + WEIXIN_SHARE);
                shareButtomDialogView.dismiss();
            }
        });
        tv_webo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtil.showProgressDialog(context);
                showShare(context, SinaWeibo.NAME, title, text, picurl, url + WEIBO_SHARE, url + WEIBO_SHARE);
                shareButtomDialogView.dismiss();
            }
        });
        tv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtil.showProgressDialog(context);
                showShare(context, cn.sharesdk.tencent.qq.QQ.NAME, title, text, picurl, url + QQ_SHARE, url + QQ_SHARE);
                shareButtomDialogView.dismiss();
            }
        });
        tv_qq_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtil.showProgressDialog(context);
                showShare(context, QZone.NAME, title, text, picurl, url + QQ_ZONE_SHARE, url + QQ_ZONE_SHARE);
                shareButtomDialogView.dismiss();
            }
        });
    }

    /**
     * 登录
     *
     * @param name 登录方式（QQ.NAME、Wechat.NAME、SinaWeibo.NAME）
     */
    public void login(String name) {
        Platform mPlatform = ShareSDK.getPlatform(name);
        mPlatform.setPlatformActionListener(mPlatformActionListener);
        mPlatform.authorize();//单独授权,OnComplete返回的hashmap是空的
        mPlatform.showUser(null);//授权并获取用户信息
    }

    /**
     * 登录
     *
     * @param name 登录方式（QQ.NAME、Wechat.NAME、SinaWeibo.NAME）
     */

    public void login(String name, PlatformActionListener platformActionListener) {
        Platform mPlatform = ShareSDK.getPlatform(name);
        if (mPlatform.isAuthValid()) {
            mPlatform.removeAccount(true);
        }
        mPlatform.setPlatformActionListener(platformActionListener);
        mPlatform.SSOSetting(false);
//        mPlatform.authorize();//单独授权,OnComplete返回的hashmap是空的
        mPlatform.showUser(null);//授权并获取用户信息
    }

    public PlatformActionListener mPlatformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Log.e("onComplete", "登录成功");
            Log.e("openid", platform.getDb().getUserId()); //拿到登录后的
            Log.e("username", platform.getDb().getUserName()); //拿到登录用户的昵称
            Log.e("Token", platform.getDb().getToken());
            Log.e("getTokenSecret", platform.getDb().getTokenSecret());
            Log.e("getUserIcon", platform.getDb().getUserIcon());
            Log.e("getPlatformNname", platform.getDb().getPlatformNname());
            Log.e("getUserGender", platform.getDb().getUserGender());
            Log.e("getExpiresIn", "" + platform.getDb().getExpiresIn());
            Log.e("getPlatformVersion", "" + platform.getDb().getPlatformVersion());
            Log.e("getExpiresTime", "" + platform.getDb().getExpiresTime());
            Log.e("exportData", "" + platform.getDb().exportData());
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Log.e("onError", throwable.toString() + "");
            Log.e("onError", "登录失败" + throwable.toString());
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Log.e("onCancel", "登录取消");
        }
    };
}
