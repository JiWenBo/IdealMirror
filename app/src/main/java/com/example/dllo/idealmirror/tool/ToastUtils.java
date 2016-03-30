package com.example.dllo.idealmirror.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by nan on 16/3/30.
 * 解决toast的时间冲突
 */
public class ToastUtils {
    // Toast的对象
    private static Toast toast = null;

    public static void showToast(Context context, String id) {
        if (toast == null) {
            toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }
}
