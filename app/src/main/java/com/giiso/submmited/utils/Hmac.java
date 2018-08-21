package com.giiso.submmited.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/9/14.
 */

public class Hmac {
    static Hmac hmac = null;
    private static final String LOG_TAG = "Hmac";
    private static final String HmacSHA1 = "HmacSHA1";

    public synchronized static Hmac getInstance() {
        if (hmac == null)
            hmac = new Hmac();
        return hmac;
    }

    public String stringToSign(String data, String key) {
        try {
            Mac mac = Mac.getInstance(HmacSHA1);
//            SecretKeySpec secret = new SecretKeySpec(
//                    key.getBytes("UTF-8"), mac.getAlgorithm());
//            mac.init(secret);
            if(TextUtils.isEmpty(data))
            data="0";
            if(TextUtils.isEmpty(key))
                key="0";
            byte[] keyBytes = key.getBytes();
            mac.init(new SecretKeySpec(keyBytes, HmacSHA1));
            String signature = new String(Base64.encodeToString(mac.doFinal(data.getBytes()), Base64.NO_WRAP));
            return signature;
        } catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, "Hash algorithm SHA-1 is not supported", e);
        }  catch (InvalidKeyException e) {
            Log.e(LOG_TAG, "Invalid key", e);
        }
        return "";
    }


}
