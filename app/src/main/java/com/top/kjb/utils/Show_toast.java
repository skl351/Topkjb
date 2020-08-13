package com.top.kjb.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * 快速显示toast
 * Created by Administrator on 2016/11/4.
 */
public class Show_toast {
    // 构造方法私有化 不允许new对象
    private Show_toast() {

    }

    // Toast对象
    private static Toast toast = null;

    /**
     * 显示Toast
     */
    public static void showText(Context context, String text) {
        if (null == text) {
            return;
        }
        if (!"".equals(text)) {
            try {
//                BToast.normal(context).text(text).relativeGravity(BToast.LAYOUT_GRAVITY_TOP).duration(BToast.DURATION_SHORT).show();//解决toast问题
                ToastUtil.show(context,text,Toast.LENGTH_SHORT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 显示Toast long
     */
    public static void showText2(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();

    }


}
