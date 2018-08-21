package com.giiso.submmited.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 工具类，获取手机型号，imei号
 * Created by Administrator on 2016/12/22.
 */
public class Utils {

    /**
     * 获取手机品牌型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }
    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getImei(Context context) {
        try {
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            if (!TextUtils.isEmpty(device_id)){
                return device_id;
            }
            device_id = getMac();
            if (!TextUtils.isEmpty(device_id) && !"00:00:00:00:00:00".equals(device_id)) {
                return  device_id ;
            }
            device_id = getAndroidId(context);
            if (!TextUtils.isEmpty(device_id)) {
                return  device_id ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getSecondImei();
    }
    //拼接后的计算出的MD5值
    public static String getImeid(Context context) {
        String m_szLongID = getDevice(context) + getMac()
                + getAndroidId(context) + android.os.Build.SERIAL + getSecondImei();
        // compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        }   // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }
    private static String getAndroidId(Context context) {
        String android_id = null;
        android_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        return android_id;
    }
    private static String getMac() {

        String mac = null;
        FileReader fstream = null;
        try {
            try {
                fstream = new FileReader("/sys/class/net/wlan0/Address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/Address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

    private static String getDevice(Context context) {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = null;
        if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            device_id = tm.getDeviceId();
        }
        return device_id;
    }

    /**
     * 获取imei失败时，构造一串类似于imei号的字符串
     *
     * @return
     */
    private static String getSecondImei() {
        String ANDROID_ID = "";
        try {
            ANDROID_ID = "35" + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10; //13 digits
        } catch (Exception e) {
            ANDROID_ID = java.util.UUID.randomUUID().toString();
        }
        return ANDROID_ID;
    }

    /**
     * 获取应用签名
     *
     * @return
     */
    public static String getSign(Context context) {
        Signature[] arrayOfSignature = getRawSignature(context, context.getApplicationContext().getPackageName());
        if ((arrayOfSignature == null) || (arrayOfSignature.length == 0)) {
            return "";
        }
        return (getMessageDigest(arrayOfSignature[0].toByteArray()));
    }

    private static Signature[] getRawSignature(Context paramContext, String paramString) {
        if ((paramString == null) || (paramString.length() == 0)) {
            return null;
        }
        PackageManager localPackageManager = paramContext.getPackageManager();
        PackageInfo localPackageInfo;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(paramString, PackageManager.GET_SIGNATURES);
            if (localPackageInfo == null) {
                return null;
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            return null;
        }
        return localPackageInfo.signatures;
    }

    public static final String getMessageDigest(byte[] paramArrayOfByte) {
        char[] arrayOfChar1 = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            int i = arrayOfByte.length;
            char[] arrayOfChar2 = new char[i * 2];
            int j = 0;
            int k = 0;
            while (true) {
                if (j >= i)
                    return new String(arrayOfChar2);
                int m = arrayOfByte[j];
                int n = k + 1;
                arrayOfChar2[k] = arrayOfChar1[(0xF & m >>> 4)];
                k = n + 1;
                arrayOfChar2[n] = arrayOfChar1[(m & 0xF)];
                j++;
            }
        } catch (Exception localException) {
        }
        return null;
    }

    /**
     * 获取Manifest中application结点下的meta数据
     *
     * @param context
     * @param name
     * @return
     */
    public static String getApplicationMetaData(Context context, String name) {

        Object result = "";
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);

            result = info.metaData.get(name);
            if (result == null) {
                result = "";
            }
            System.out.println("application meta: key = " + name + ", value = "
                    + result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
