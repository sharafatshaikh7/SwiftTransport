package ConstantClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shara on 3/9/2017.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;
    public static final String OTP_REGEX = "[0-9]{1,7}";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();


        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.

            String messageBody = smsMessage.getMessageBody();

            Pattern pattern = Pattern.compile(OTP_REGEX);
            Matcher matcher = pattern.matcher(messageBody);
            String otp = "";
            while (matcher.find()) {
                otp = matcher.group();
            }

            Log.e("otp",otp);
            Log.e("sender",sender);
            if(sender.equals("VM-CMYLAB")){
                //Pass on the text to our listener.
                mListener.messageReceived(otp);
            }

            //Pass on the text to our listener.
            //mListener.messageReceived(otp);
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    public interface SmsListener {
        public void messageReceived(String messageText);
    }
}
