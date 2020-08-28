package com.top.kjb.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.top.kjb.MainActivity;
import com.top.kjb.originpack.BaseActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class java_util {

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et, Context context) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    public void hideInput(BaseActivity activity) {
        InputMethodManager imm = (InputMethodManager)activity. getSystemService(INPUT_METHOD_SERVICE);
        View v = activity.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
