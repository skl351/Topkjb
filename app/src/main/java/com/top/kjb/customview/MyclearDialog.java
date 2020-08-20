package com.top.kjb.customview;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.top.kjb.R;



public class MyclearDialog extends Dialog {



    LinearLayout id_click_ok;

    public MyclearDialog(Context context) {
        super(context, R.style.MyProgressDialog);
        setContentView(R.layout.mycleardialog_view);
        Window window=getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.gravity= Gravity.CENTER;
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        id_click_ok=findViewById(R.id.id_click_ok);
        id_click_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
