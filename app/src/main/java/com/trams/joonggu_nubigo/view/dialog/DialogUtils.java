package com.trams.joonggu_nubigo.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.trams.joonggu_nubigo.R;

/**
 * Created by ADMIN on 11/5/2015.
 */
public class DialogUtils {

    public interface ConfirmDialogListener {
        public void onConfirmClick();
    }

    public static final AlertDialog showConfirmAlertDialog(final Context context,final String msg,final ConfirmDialogListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setCancelable(false);

        if (listener != null) {
            builder.setPositiveButton(context.getString(R.string.btn_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            listener.onConfirmClick();

                        }
                    });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

}
