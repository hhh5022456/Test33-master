package com.example.administrator.test33;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 所有activity基类
 * @author zhiyuan
 * @date 2017/7/27
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private boolean fullScreen;
    private boolean noTitleBar;
    private boolean tophasColor;
    private boolean splashFull;

    /** 是否在后台 */
    protected boolean isCurrentRunningForeground = true;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSuperCreateFinish(savedInstanceState);
        setContentView(getLayoutRes());
        unbinder = ButterKnife.bind(this);
        Log.e("currentView",getLocalClassName());
        onViewBindFinish(savedInstanceState);
        onViewBindFinish();
        setSystemBar();
    }

    protected void setSystemBar(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            // 只对5.0以上进行状态栏适配
            return;
        }



    }

    protected void noTitleBar(boolean noTitleBar){
        this.noTitleBar = noTitleBar;
    }

    protected void splashFull(boolean splashFull){
        this.splashFull = splashFull;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * 设置沉浸式
     * @param barColor 状态栏颜色
     * @param fontColor 状态了字体颜色
     */
    protected void setTopBarColorAndTextColor(int barColor,int fontColor){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            // 只对6.0以上进行状态栏适配
            return;
        }
        Log.e("MessageInfo","设置颜色");
        tophasColor = true;
    }

    /**
     * 设置是否为全屏
     */
    protected void setFullScreen(boolean fullScreen){
        this.fullScreen = fullScreen;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        public static void onPageStart(String viewName);
//        public static void onPageEnd(String viewName);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onSuperCreateFinish(@Nullable Bundle savedInstanceState) {}

    protected void onViewBindFinish(@Nullable Bundle savedInstanceState) {}

    protected void onViewBindFinish() {}



    protected abstract int getLayoutRes();


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
    }


    protected String getUmengPageTitle() {
        return this.getClass().getSimpleName();
    }


    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }

}
