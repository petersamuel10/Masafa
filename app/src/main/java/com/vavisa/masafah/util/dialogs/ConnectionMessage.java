package com.vavisa.masafah.util.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.vavisa.masafah.R;

public class ConnectionMessage {

    private Dialog dialog;
    private static ConnectionMessage mInstance;

    public static synchronized ConnectionMessage getInstance() {
        if(mInstance == null)
            mInstance = new ConnectionMessage();
        return mInstance;
    }

    public void show(Context context){
        if(dialog !=null && dialog.isShowing())
            return;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.connection_alert_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void dismiss(){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
