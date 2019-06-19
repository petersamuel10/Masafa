package com.vavisa.masafah.util.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.vavisa.masafah.R;

public class FailedMessage {

    private Dialog dialog;
    private static FailedMessage mInstance;

    public static synchronized FailedMessage getInstance(){
        if(mInstance == null)
            mInstance = new FailedMessage();
        return mInstance;
    }

    public void show(Context context){
        if(dialog !=null && dialog.isShowing())
            return;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.failed_message_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void dismiss(){
        if(dialog !=null && dialog.isShowing())
            dialog.dismiss();
    }
}
