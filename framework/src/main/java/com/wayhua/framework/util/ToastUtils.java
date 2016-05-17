package com.wayhua.framework.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.MessageFormat;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/2/18.
 **/
public class ToastUtils {


    private static void show(final Activity activity, final int resId,
                             final int duration) {
        if (activity == null)
            return;

        final Context context = activity.getApplication();
        activity.runOnUiThread(new Runnable() {

            public void run() {
                Toast.makeText(context, resId, duration).show();
            }
        });
    }

    public static void show(final Activity activity, final String message) {
        showLong(activity, message);
    }
    public static void show(final Activity activity, final int message) {
        showLong(activity, message);
    }

    private static void show(final Activity activity, final String message,
                             final int duration) {
        if (activity == null)
            return;
        if (TextUtils.isEmpty(message))
            return;

        final Context context = activity.getApplication();
        activity.runOnUiThread(new Runnable() {

            public void run() {
                Toast.makeText(context, message, duration).show();
            }
        });
    }

    public static void show(final Activity activity, final Throwable e,
                            final int defaultMessage) {
        if (activity == null)
            return;

        String message = e.getMessage();
//        if (e instanceof RequestException)
//            message = ((RequestException) e).formatErrors();
//        else
//            message = null;

        if (TextUtils.isEmpty(message))
            message = activity.getString(defaultMessage);

        show(activity, message);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     *
     * @param activity
     * @param resId
     */
    public static void showLong(final Activity activity, int resId) {
        show(activity, resId, LENGTH_LONG);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
     *
     * @param activity
     * @param resId
     */
    public static void showShort(final Activity activity, final int resId) {
        show(activity, resId, LENGTH_SHORT);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     *
     * @param activity
     * @param message
     */
    public static void showLong(final Activity activity, final String message) {
        show(activity, message, LENGTH_LONG);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
     *
     * @param activity
     * @param message
     */
    public static void showShort(final Activity activity, final String message) {
        show(activity, message, LENGTH_SHORT);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     *
     * @param activity
     * @param message
     * @param args
     */
    public static void showLong(final Activity activity, final String message,
                                final Object... args) {
        String formatted = MessageFormat.format(message, args);
        show(activity, formatted, LENGTH_LONG);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
     *
     * @param activity
     * @param message
     * @param args
     */
    public static void showShort(final Activity activity, final String message,
                                 final Object... args) {
        String formatted = MessageFormat.format(message, args);
        show(activity, formatted, LENGTH_SHORT);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_LONG} duration
     *
     * @param activity
     * @param resId
     * @param args
     */
    public static void showLong(final Activity activity, final int resId,
                                final Object... args) {
        if (activity == null)
            return;

        String message = activity.getString(resId);
        showLong(activity, message, args);
    }

    /**
     * Show message in {@link Toast} with {@link Toast#LENGTH_SHORT} duration
     *
     * @param activity
     * @param resId
     * @param args
     */
    public static void showShort(final Activity activity, final int resId,
                                 final Object... args) {
        if (activity == null)
            return;

        String message = activity.getString(resId);
        showShort(activity, message, args);
    }

}
