package com.mm.weclubs.util;

import android.content.Context;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Heller on 2014/12/19 0019.
 */
public class ThreadUtil {

    public static void runInMainThread(Context context, Runnable runnable) {
        Handler mainHandler = new Handler(context.getMainLooper());
        mainHandler.post(runnable);
    }

    public static void runInMainThread(Context context, Runnable runnable, long delay) {
        Handler mainHandler = new Handler(context.getMainLooper());
        mainHandler.postDelayed(runnable, delay);
    }

    public static void runTimerThread(Context context, TimerTask timerTask, long delay) {
        Timer timer = new Timer("THREAD_UTIL");
        timer.schedule(timerTask, delay);
    }
}
