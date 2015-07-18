package demo.stackexchange.com.stackexchangedemo.helper;

/**
 * Created by vinay.pratap on 18-07-2015.
 */

import android.app.ProgressDialog;
import android.content.Context;

public class DialogHelper {

    private ProgressDialog dialog;
    Context mContext;
    int mflag;

    public DialogHelper(Context context, int flag) {
        dialog = new ProgressDialog(context);
        mflag = flag;
    }

    public void showDialog() {


        dialog.setMessage("Loading Data Please Wait...");

		/*
         * else if(mflag==Const.IS_PLACE_IMAGE_ACTIVITY){
		 * dialog.setMessage("Image Loading Please wait"); }
		 */
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
