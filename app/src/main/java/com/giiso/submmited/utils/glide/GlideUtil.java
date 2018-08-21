package com.giiso.submmited.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.giiso.submmited.R;
import com.giiso.submmited.base.BaseApplication;
import com.giiso.submmited.base.Content;
import com.giiso.submmited.utils.TDevice;

/**
 * Glide图片加载工具类
 * Created by Administrator on 2018/6/5.
 */

public class GlideUtil {
    public static void setImageCenterCrop(Context context, String url, ImageView view) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(view);
    }
    public static void setImageSquareCircleCenterCrop(final Context context, String url, ImageView view) {
        GlideApp.with(context).asBitmap().load("" + url).centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(new BitmapImageViewTarget(view) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(TDevice.dip2px(context,5));
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
    public static void setImageCenterCrop(Context context, int placeholder, int error, String url, ImageView view) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .centerCrop()
                .error(error)
                .into(view);
    }

    public static void glideImgCenterCrop(Context context, String url, ImageView view) {
        setImageCenterCrop(context, url, view);
    }

    public static void glideImgWifiSwitchCenterCrop(Context context, String url, ImageView view) {
        if (!TDevice.isWifiOpen() && BaseApplication.get(Content.WIFI_IMG, false)) {
//        if (BaseApplication.get(Content.WIFI_IMG, false)) {
            setImageCenterCrop(context, "", view);
        } else {
            setImageCenterCrop(context, url, view);
        }
    }
    public static void glideSquareCircleImgWifiSwitchCenterCrop(Context context, String url, ImageView view) {
        if (!TDevice.isWifiOpen() && BaseApplication.get(Content.WIFI_IMG, false)) {
//        if (BaseApplication.get(Content.WIFI_IMG, false)) {
            setImageSquareCircleCenterCrop(context, "", view);
        } else {
            setImageSquareCircleCenterCrop(context, url, view);
        }
    }

    public static void setImageCircularCenterCrop(final Context context, String url, ImageView view) {
//                //圆形
        GlideApp.with(context).asBitmap().load("" + url).centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(new BitmapImageViewTarget(view) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setImageCircularCenterCrop(final Context context, int placeholder, int error, String url, ImageView view) {
//                //圆形
        GlideApp.with(context).asBitmap().load("" + url).centerCrop()
                .placeholder(placeholder)
                .error(error).into(new BitmapImageViewTarget(view) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void glideHeadCircularCenterCrop(Context context, String url, ImageView view) {
        setImageCircularCenterCrop(context, url, view);
    }
}
