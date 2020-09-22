package com.top.kjb.tabfragment.chat;

import com.orhanobut.logger.Logger;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.top.kjb.utils.functionClass;

public class java_chat {

    public void login() {
        TUIKit.login("18267913849", "123", new IUIKitCallBack() {
            @Override
            public void onError(String module, final int code, final String desc) {

                Logger.d("imLogin errorCode = " + code + ", errorInfo = " + desc);
            }

            @Override
            public void onSuccess(Object data) {
                Logger.d("imLogin success");
            }
        });
    }
}
