package com.giiso.submmited.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2016/8/23.
 */
public class PermissionUtils {
    private Activity activity;
    public int requestCode = 0x60;

    public PermissionUtils(Activity activity) {
        this.activity = activity;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionCamera(int requestCode) {
        this.requestCode = requestCode;
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int writeExternalPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> permissions = new ArrayList<String>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionVoice(int requestCode) {
        this.requestCode = requestCode;
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        int writeExternalPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> permissions = new ArrayList<String>();
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }

    }
    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionMap(int requestCode) {
        this.requestCode = requestCode;
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int writeExternalPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int phoneInfoPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        List<String> permissions = new ArrayList<String>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (phoneInfoPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);
        }
//        if (settingPermission != PackageManager.PERMISSION_GRANTED) {
//            permissions.add(Manifest.permission.WRITE_SETTINGS);
//        }

        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionMain(int requestCode) {
        this.requestCode = requestCode;
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int phoneInfoPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        int writeExternalPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int settingPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_SETTINGS);
        List<String> permissions = new ArrayList<String>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (phoneInfoPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);
        }
//        if (settingPermission != PackageManager.PERMISSION_GRANTED) {
//            permissions.add(Manifest.permission.WRITE_SETTINGS);
//        }

        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionWrite(int requestCode) {
        this.requestCode = requestCode;
        int writeExternalPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> permissions = new ArrayList<String>();
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionInstall(int requestCode) {
        this.requestCode = requestCode;
        int writeExternalPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int installExternalPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.REQUEST_INSTALL_PACKAGES);
        List<String> permissions = new ArrayList<String>();
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (installExternalPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.REQUEST_INSTALL_PACKAGES);
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == this.requestCode) {
            boolean isPermissions = true;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isPermissions = false;
                }
            }
            if (isPermissions) {
                return true;
            }
        }
        return false;
    }
}
