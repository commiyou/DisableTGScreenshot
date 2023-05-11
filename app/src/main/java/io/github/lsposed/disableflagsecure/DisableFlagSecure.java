package io.github.lsposed.disableflagsecure;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.BiPredicate;

import android.view.WindowManager;


import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class DisableFlagSecure implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("org.telegram.messenger"))
            return;

        XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "onCreate", android.os.Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object activity = param.thisObject;
                WindowManager.LayoutParams params = ((android.app.Activity) activity).getWindow().getAttributes();
                params.flags |= WindowManager.LayoutParams.FLAG_SECURE;
                ((android.app.Activity) activity).getWindow().setAttributes(params);
            }
        });
    }


}
