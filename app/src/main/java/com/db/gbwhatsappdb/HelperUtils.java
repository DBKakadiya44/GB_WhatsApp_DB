package com.db.gbwhatsappdb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class HelperUtils {
    private static String EMAIL = "mailto:topnewappss@gmail.com";
    private static String KEY_ACCEPT_PP = "key_accept_";
    public static String KEY_CATEGORY = "category";
    private static String KEY_CSS_VALUES = "KEY_CSS_VALUES";
    private static String KEY_IS_RATE_APP = "KEY_IS_RATE_APP";
    public static final String KEY_PLAY_VIDEO_FILE_NAME = "key_play_video_file_name";
    private static String KEY_SAVE_SUBS_TIME = "KEY_SAVE_SUBS_TIME";
    private static String KEY_USER_PURCHASE = "KEY_USER_PURCHASE";
    public static final String KEY_VIDEO_FILE_URI = "key_download_url";
    private static String SAVED_TIME = "SAVEDTIME";
    private static String SUBJECT = "Rate WhatsWeb & Scanner";

    public static void setCSS(Context context, String str) {
        new PrefManager(context).setCssString(KEY_CSS_VALUES, str);
    }

    public static String getCssValues(Context context) {
        return new PrefManager(context).getCssString(KEY_CSS_VALUES, ".initial_startup{display:none!important}.main{display:none!important}.landing-header{display:none!important}._2WuPw{display:none!important}._3aF8K{display:none!important}._3-soo{display:none!important}.-A_bA{display:none!important}#initial_startup{display:none!important}");
    }

    public static boolean isUserPurchaseProduct(Context context) {
        return new PrefManager(context).getBoolean(KEY_USER_PURCHASE);
    }

    public static void setKeyUserPurchase(Context context, boolean z) {
        new PrefManager(context).setBoolean(KEY_USER_PURCHASE, Boolean.valueOf(z));
    }

    public static boolean isUserAcceptPrivacyPolicy(Context context) {
        return new PrefManager(context).getBoolean(KEY_ACCEPT_PP);
    }

    public static boolean isShowSubsAct(Context context) {
        return new PrefManager(context).getBoolean(KEY_SAVE_SUBS_TIME);
    }

    public static void setAcceptPP(Context context, boolean z) {
        new PrefManager(context).setBoolean(KEY_ACCEPT_PP, Boolean.valueOf(z));
    }

    public static void setUserRated(Context context, boolean z) {
        new PrefManager(context).setBoolean(KEY_IS_RATE_APP, Boolean.valueOf(z));
    }

    public static boolean isRated(Context context) {
        return new PrefManager(context).getBoolean(KEY_IS_RATE_APP);
    }

    private static long getTime(Context context) {
        return new PrefManager(context).getLong(SAVED_TIME);
    }

    public static void setTime(Context context) {
        new PrefManager(context).setLong(SAVED_TIME, System.currentTimeMillis());
    }

    public static Boolean showPurchase(Context context) {
        return Boolean.valueOf(System.currentTimeMillis() >= getTime(context) + 86400000);
    }

    public static void shareApp(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", context.getResources().getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", "\nLet me recommend you this application\n\n" + "https://play.google.com/store/apps/details?id=com.db.gbwhatsappdb" + "\n\n");
            context.startActivity(Intent.createChooser(intent, "choose one"));
        } catch (Exception unused) {
        }
    }

    @SuppressLint("WrongConstant")
    public static void goMarket(Context context) {
        String packageName = context.getPackageName();
        new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)).addFlags(1208483840);
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showSettingsDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings(context);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void openSettings(Context context) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

    public static boolean isNetworkAvailable(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
