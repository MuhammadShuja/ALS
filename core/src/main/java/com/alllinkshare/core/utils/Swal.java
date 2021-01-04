package com.alllinkshare.core.utils;

import android.content.Context;
import android.util.Log;

import com.alllinkshare.core.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Swal {
    private static final String TAG = "Util/Swal";

    private Context mContext;
    private SweetAlertDialog dialog;

    public Swal(Context context){
        mContext = context;
        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.gold));

        Log.d(TAG, "New instance created...");
    }

    public void progress(String title) {
        dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText("");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void success(String message) {
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText("Success");
        dialog.setConfirmText("Done");
        dialog.setContentText(message);
        dialog.setCancelable(false);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    public void success(String message, SweetAlertDialog.OnSweetClickListener callback) {
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText("Success");
        dialog.setConfirmText("Done");
        dialog.setContentText(message);
        dialog.setCancelable(false);
        dialog.setConfirmClickListener(callback);
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    public void error(String error) {
        dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("Error");
        dialog.setContentText(error);
        dialog.setCancelable(false);
        dialog.setConfirmButton("Close", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
        dialog.setConfirmButtonBackgroundColor(mContext.getResources().getColor(R.color.grey));
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    public void error(String error, SweetAlertDialog.OnSweetClickListener callback) {
        dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("Error");
        dialog.setContentText(error);
        dialog.setCancelable(false);
        dialog.setConfirmButton("Close", callback);
        dialog.setConfirmButtonBackgroundColor(mContext.getResources().getColor(R.color.grey));
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    public void dismiss(){
        dialog.dismiss();
    }
}