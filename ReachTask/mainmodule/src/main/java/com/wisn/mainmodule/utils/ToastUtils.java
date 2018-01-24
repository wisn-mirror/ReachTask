package com.wisn.mainmodule.utils;

import android.widget.Toast;

import com.wisn.mainmodule.app.MApplication;
import com.wisn.utils.Utils;

/**
 * Created by wisn on 2017/10/5.
 */

public class ToastUtils {

    private static Toast toast;

    public static void show( String msg) {
        if (toast == null) {
            toast = Toast.makeText(MApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void show(int id) {
        show(Utils.getApp().getString(id));
    }

}
