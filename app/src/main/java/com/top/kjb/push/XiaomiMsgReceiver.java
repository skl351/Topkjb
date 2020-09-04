package com.top.kjb.push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;


/**
 * 小米离线推送
 */
public class XiaomiMsgReceiver extends PushMessageReceiver {
    private static final String TAG = "XiaomiMsgReceiver";
    private String mRegId;

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        Log.d(TAG, "onReceiveRegisterResult is called. +++++++" + miPushCommandMessage.toString());
        String command = miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

        Log.d(TAG, "cmd: " + command + " | arg: " + cmdArg1
                + " | result: " + miPushCommandMessage.getResultCode() + " | reason: " + miPushCommandMessage.getReason());

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }

        Log.d(TAG, "regId: " + mRegId);
//        Sp.INSTANCE.setXiaomi_mRegId(mRegId);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);

//        Intent intent = new Intent(context, launActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);

    }
}