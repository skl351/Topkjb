package com.top.kjb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        View v = activity.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    public Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    public  String  bmapTranQQMap(double lat,double lng){
        Double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        Double x = lng - 0.0065;
        Double y = lat - 0.006;
        Double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        Double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        Double lngs = z * Math.cos(theta);
        Double lats = z * Math.sin(theta);

        return lats+","+lngs;
    }

}
