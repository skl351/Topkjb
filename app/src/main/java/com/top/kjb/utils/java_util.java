package com.top.kjb.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.ruiwenliu.Horizontallibrary.adapter.BaseRecyclerviewAdapter;
import com.ruiwenliu.Horizontallibrary.adapter.RecylcerViewHolder;
import com.top.kjb.R;
import com.top.kjb.bean.bean_type_item;
import com.top.kjb.originpack.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public String bmapTranQQMap(double lat, double lng) {
        Double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        Double x = lng - 0.0065;
        Double y = lat - 0.006;
        Double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        Double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        Double lngs = z * Math.cos(theta);
        Double lats = z * Math.sin(theta);

        return lats + "," + lngs;
    }

    public void hintKeyBoard(Activity context) {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && context.getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (context.getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }


    public void addliandongline(com.ruiwenliu.Horizontallibrary.HorizontalScrollview horizontalScrollview, ArrayList<bean_type_item> list, FragmentActivity activity) {

        horizontalScrollview.setCustomAdapter(new BaseRecyclerviewAdapter(R.layout.item_viewly, list) {

            @Override
            protected void convert(RecylcerViewHolder helper, Object item, int position) {

                TextView tv_title = helper.getView(R.id.tv_title);
                bean_type_item a= (bean_type_item)item;
                tv_title.setText(a.getSportsName());
                if (position == getSelectPostion()) {
                    tv_title.setTextColor(activity.getResources().getColor(R.color.color_333333));
                    tv_title.setTextSize(18f);
                    tv_title.getPaint().setFakeBoldText(true);
                } else {
                    tv_title.setTextColor(activity.getResources().getColor(R.color.color_9B9BAE));
                    tv_title.setTextSize(16f);
                    tv_title.getPaint().setFakeBoldText(false);
                }
            }
        });
    }
    public void addliandongline2(com.ruiwenliu.Horizontallibrary.HorizontalScrollview horizontalScrollview, ArrayList<bean_type_item> list, FragmentActivity activity) {

        horizontalScrollview.setCustomAdapter(new BaseRecyclerviewAdapter(R.layout.item_viewly, list) {

            @Override
            protected void convert(RecylcerViewHolder helper, Object item, int position) {

                TextView tv_title = helper.getView(R.id.tv_title);
                bean_type_item a= (bean_type_item)item;
                tv_title.setText(a.getSportsName());
                if (position == getSelectPostion()) {
                    tv_title.setTextColor(activity.getResources().getColor(R.color.color_1cbe6f));
                    tv_title.setTextSize(18f);
                    tv_title.getPaint().setFakeBoldText(true);
                } else {
                    tv_title.setTextColor(activity.getResources().getColor(R.color.color_9B9BAE));
                    tv_title.setTextSize(16f);
                    tv_title.getPaint().setFakeBoldText(false);
                }
            }
        });
    }


    /**
     * 计算周几
     */
    public static String getWeek(String data) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        long lcc = Long.valueOf(data);
//        int i = Integer.parseInt(data);
        String times = sdr.format(new Date(lcc));
        Date date = null;
        int mydate = 0;
        String week = "";
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }

}
