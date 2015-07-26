package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 18-07-2015.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import demo.stackexchange.com.stackexchangedemo.R;

public class DialogHelper {

    private ProgressDialog dialog;
    Context mContext;
    int mflag;

    public DialogHelper(Context context, int flag) {
        dialog = new ProgressDialog(context);
        //Dialog was getting cancelled up
        dialog.setCanceledOnTouchOutside(false);
        mContext = context;
        mflag = flag;
    }

    public void showDialog() {


        dialog.setMessage("Loading Data Please Wait...");

        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
